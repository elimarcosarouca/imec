package br.fucapi.ads.modelo.controlador;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.alfresco.repo.webservice.administration.AdministrationFault;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;

import br.fucapi.ads.modelo.dominio.GrupoLog;
import br.fucapi.ads.modelo.dominio.UsuarioGrupoLog;
import br.fucapi.ads.modelo.dominio.UsuarioTarefa;
import br.fucapi.ads.modelo.servico.GrupoLogServico;
import br.fucapi.ads.modelo.servico.UsuarioGrupoLogServico;
import br.fucapi.ads.modelo.servico.UsuarioTarefaServico;
import br.fucapi.bpms.alfresco.dominio.Usuario;
import br.fucapi.bpms.alfresco.dominio.UsuarioGrupo;
import br.fucapi.bpms.alfresco.servico.AlfrescoServico;

@ManagedBean
@ViewScoped
public class GrupoControladorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{messengerProperties}")
	private Properties messengerProperties;

	@ManagedProperty(value = "#{usuarioTarefaServicoImpl}")
	private UsuarioTarefaServico usuarioTarefaServico;

	@ManagedProperty(value = "#{alfrescoServicoImpl}")
	private AlfrescoServico alfrescoServico;

	@ManagedProperty(value = "#{usuarioGrupoLogServicoImpl}")
	private UsuarioGrupoLogServico usuarioGrupoLogServico;

	@ManagedProperty(value = "#{grupoLogServicoImpl}")
	private GrupoLogServico grupoLogServico;

	private DualListModel<UsuarioGrupo> usuarioGrupoDualListModel;

	private UsuarioGrupo usuarioGrupo = new UsuarioGrupo();
	private Usuario usuario = new Usuario();

	private List<UsuarioTarefa> lista = new ArrayList<UsuarioTarefa>();
	private List<UsuarioGrupo> grupos = new ArrayList<UsuarioGrupo>();
	private List<Usuario> usuarios = new ArrayList<Usuario>();

	private DualListModel<UsuarioGrupo> gruposDualListModel;
	private DualListModel<Usuario> usuarios_pick;

	private List<Usuario> source = new ArrayList<Usuario>();
	private List<Usuario> target = new ArrayList<Usuario>();

	private String shortName;

	@ManagedProperty(value = "#{paginaCentralControladorBean}")
	private PaginaCentralControladorBean paginaCentralControladorBean;

	@PostConstruct
	public void init() {

		this.shortName = "";
		this.grupos = new ArrayList<UsuarioGrupo>();
		this.lista = new ArrayList<UsuarioTarefa>();
		this.usuarios = new ArrayList<Usuario>();

	}

	public void listarGrupoAlfresco() {
		try {

			grupos = alfrescoServico.listarGrupos();
			this.init();

		} catch (AdministrationFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void listarGrupoNome() {

		try {

			this.grupos = alfrescoServico.listarGruposLike(this.shortName);

		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public void cadastro() {

		paginaCentralControladorBean
				.setPaginaCentral("paginas/grupo/cadastro.xhtml");
	}

	public void cancelar() {

		paginaCentralControladorBean
				.setPaginaCentral("paginas/grupo/pesquisa.xhtml");
	}

	public void incluir() {

		try {
			alfrescoServico.incluirGrupo(this.shortName);
			this.init();
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							messengerProperties
									.getProperty("alert.group.create.sucess"),
							""));

		} catch (HttpClientErrorException e) {

			System.out.println(e.getStatusCode().toString());

			if ("400".equals(e.getStatusCode().toString())) {

				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										messengerProperties
												.getProperty("alert.group.create.nameconflict"),
										""));

			} else {

				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										FacesMessage.SEVERITY_INFO,
										messengerProperties
												.getProperty("alert.group.create.failed"),
										""));

			}

		} catch (Exception e) {

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							messengerProperties
									.getProperty("alert.group.create.failed"),
							""));

		}

	}

	public void deletar() throws RemoteException {

		GrupoLog grupoLog = new GrupoLog(this.usuarioGrupo.getShortName(),
				((Usuario) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal()).getUserName(),
				new Date(), "E");

		try {

			alfrescoServico.deletarGrupoNome(this.usuarioGrupo.getShortName());

			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(
					null,
					new FacesMessage(messengerProperties
							.getProperty("alert.group.create.delete"), ""));

		//	grupoLog.setOperacao("E");
			grupoLogServico.save(grupoLog);

			listarGrupoAlfresco();

		} catch (HttpClientErrorException e) {

			System.out.println("Cod. Erro- 404.");
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Atenção. Não foi possível deletar corretamente",
							""));
		}

	}

	public String telaIncluirUsuarioGrupo(UsuarioGrupo usuarioGrupo) {

		this.usuarioGrupo = usuarioGrupo;
		this.setUsuarios(alfrescoServico.getUsuarios());

		// TODO - Refatorar método de consulta de grupo
		try {

			this.target = new ArrayList<Usuario>();
			this.target = alfrescoServico.getUsuariosPorGrupo(usuarioGrupo
					.getShortName());
			List<Usuario> sourceTemp = alfrescoServico.getUsuarios();
			this.source = new ArrayList<Usuario>();

			boolean existe = false;

			for (Usuario usuario : sourceTemp) {
				for (Usuario usuarioTemp : target) {
					if (usuario.getUserName().equals(usuarioTemp.getUserName())) {
						this.source.remove(usuarioTemp);
						existe = true;
						System.out.println(usuarioTemp.getUserName());
						break;
					}
				}

				if (!existe)
					this.source.add(usuario);

				existe = false;
			}

			this.usuarios_pick = new DualListModel<Usuario>(this.source,
					this.target);

		} catch (AdministrationFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		paginaCentralControladorBean
				.setPaginaCentral("paginas/grupo/manterusuariogrupo.xhtml");
		return "";
	}

	public String paginaincluir() {

		paginaCentralControladorBean
				.setPaginaCentral("paginas/grupo/pesquisa.xhtml");
		return "";

	}

	public String paginaManterUsuario(Usuario usuario) {

		paginaCentralControladorBean
				.setPaginaCentral("paginas/usuario/usuario_no_grupo.xhtml");
		return "";

	}

	public void onTransfer(TransferEvent event) {

		for (int i = 0; event.getItems().size() > i; i++) {

			Usuario usuario = (Usuario) event.getItems().get(i);

			UsuarioGrupoLog usuarioGrupoLog = new UsuarioGrupoLog(
					this.usuarioGrupo.getShortName(), usuario.getUserName(),
					((Usuario) SecurityContextHolder.getContext()
							.getAuthentication().getPrincipal()).getUserName(),
					new Date(), "I");

			if (event.isAdd()) {

				this.alfrescoServico.incluirUsuarioGrupo(usuario.getUserName(),
						this.usuarioGrupo.getShortName());

				usuarioGrupoLogServico.save(usuarioGrupoLog);

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Usuário(a)" + usuario.getUserName()
										+ " associado(a) ao grupo", ""));

			} else {

				this.alfrescoServico.excluirUsuarioGrupo(usuario.getUserName(),
						this.usuarioGrupo.getShortName());

				usuarioGrupoLog.setOperacao("E");
				usuarioGrupoLogServico.save(usuarioGrupoLog);

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Usuário(a)" + usuario.getUserName()
										+ " desassociado(a) do grupo.", ""));

			}
		}
	}

	public void telaVoltar() {
		this.paginaCentralControladorBean
				.setPaginaCentral("paginas/grupo/pesquisa.xhtml");
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public DualListModel<UsuarioGrupo> getGruposDualListModel() {
		return gruposDualListModel;
	}

	public void setGruposDualListModel(
			DualListModel<UsuarioGrupo> gruposDualListModel) {
		this.gruposDualListModel = gruposDualListModel;
	}

	public DualListModel<Usuario> getUsuarios_pick() {
		return usuarios_pick;
	}

	public void setUsuarios_pick(DualListModel<Usuario> usuarios_pick) {
		this.usuarios_pick = usuarios_pick;
	}

	public GrupoLogServico getGrupoLogServico() {
		return grupoLogServico;
	}

	public void setGrupoLogServico(GrupoLogServico grupoLogServico) {
		this.grupoLogServico = grupoLogServico;
	}

	public List<Usuario> getSource() {
		return source;
	}

	public void setSource(List<Usuario> source) {
		this.source = source;
	}

	public List<Usuario> getTarget() {
		return target;
	}

	public void setTarget(List<Usuario> target) {
		this.target = target;
	}

	public UsuarioTarefaServico getUsuarioTarefaServico() {
		return usuarioTarefaServico;
	}

	public void setUsuarioTarefaServico(
			UsuarioTarefaServico usuarioTarefaServico) {
		this.usuarioTarefaServico = usuarioTarefaServico;
	}

	public List<UsuarioTarefa> getLista() {
		return lista;
	}

	public void setLista(List<UsuarioTarefa> lista) {
		this.lista = lista;
	}

	public AlfrescoServico getAlfrescoServico() {
		return alfrescoServico;
	}

	public void setAlfrescoServico(AlfrescoServico alfrescoServico) {
		this.alfrescoServico = alfrescoServico;
	}

	public List<UsuarioGrupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<UsuarioGrupo> grupos) {
		this.grupos = grupos;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public UsuarioGrupo getUsuarioGrupo() {
		return usuarioGrupo;
	}

	public void setUsuarioGrupo(UsuarioGrupo usuarioGrupo) {
		this.usuarioGrupo = usuarioGrupo;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public DualListModel<UsuarioGrupo> getUsuarioGrupoDualListModel() {
		return usuarioGrupoDualListModel;
	}

	public void setUsuarioGrupoDualListModel(
			DualListModel<UsuarioGrupo> usuarioGrupoDualListModel) {
		this.usuarioGrupoDualListModel = usuarioGrupoDualListModel;
	}

	public PaginaCentralControladorBean getPaginaCentralControladorBean() {
		return paginaCentralControladorBean;
	}

	public void setPaginaCentralControladorBean(
			PaginaCentralControladorBean paginaCentralControladorBean) {
		this.paginaCentralControladorBean = paginaCentralControladorBean;
	}

	public UsuarioGrupoLogServico getUsuarioGrupoLogServico() {
		return usuarioGrupoLogServico;
	}

	public void setUsuarioGrupoLogServico(
			UsuarioGrupoLogServico usuarioGrupoLogServico) {
		this.usuarioGrupoLogServico = usuarioGrupoLogServico;
	}

	public Properties getMessengerProperties() {
		return messengerProperties;
	}

	public void setMessengerProperties(Properties messengerProperties) {
		this.messengerProperties = messengerProperties;
	}

	

}