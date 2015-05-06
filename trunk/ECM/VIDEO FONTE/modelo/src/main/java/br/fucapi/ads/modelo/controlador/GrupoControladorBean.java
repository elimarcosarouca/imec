package br.fucapi.ads.modelo.controlador;

import java.io.IOException;
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
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.alfresco.repo.webservice.administration.AdministrationFault;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;

import br.fucapi.ads.modelo.dominio.GrupoLog;
import br.fucapi.ads.modelo.dominio.UsuarioGrupoLog;
import br.fucapi.ads.modelo.dominio.UsuarioTarefa;
import br.fucapi.ads.modelo.enumerated.Constants;
import br.fucapi.ads.modelo.servico.GrupoLogServico;
import br.fucapi.ads.modelo.servico.UsuarioGrupoLogServico;
import br.fucapi.ads.modelo.servico.UsuarioTarefaServico;
import br.fucapi.bpms.alfresco.dominio.Usuario;
import br.fucapi.bpms.alfresco.dominio.UsuarioGrupo;
import br.fucapi.bpms.alfresco.servico.AlfrescoServico;

@ManagedBean
//@ViewScoped
@SessionScoped
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
	private DualListModel<Usuario> usuariosPickList;

	private List<Usuario> source = new ArrayList<Usuario>();
	private List<Usuario> target = new ArrayList<Usuario>();

	/**
	 * Alias para redirecionar para a tela de cadastro.
	 */
	public static final String CADASTRO = "cadastro";

	/**
	 * Alias para redirecionar para a tela de pesquisa.
	 */
	public static final String PESQUISA = "pesquisa";

	/**
	 * Alias para redirecionar para a tela de pesquisa.
	 */
	public static final String USUARIOGRUPO = "manterusuariogrupo";

	private String shortName;

	public String getPaginaCadastro() {
		return redirect(CADASTRO);
	}

	protected String redirect(String page) {
		try {

			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			HttpServletRequest request = (HttpServletRequest) context
					.getRequest();

			String fullUrl = request.getRequestURL().toString();
			String path = fullUrl.substring(0,
					fullUrl.lastIndexOf(Constants.BARRA));
			String url = path + Constants.BARRA + page + Constants.EXTENSION
					+ Constants.REDIRECT;

			context.redirect(url);
			FacesContext.getCurrentInstance().responseComplete();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * Força o recarregamento da pagina de pesquisa.
	 */
	public void reload() {
		// init();
	}

	@PostConstruct
	public void init() {

		this.shortName = "";
		this.grupos = new ArrayList<UsuarioGrupo>();
		this.lista = new ArrayList<UsuarioTarefa>();
		this.usuarios = new ArrayList<Usuario>();

	}

	public void listarGrupoAlfresco() {
		try {

			this.grupos = new ArrayList<UsuarioGrupo>();
			List<UsuarioGrupo> gruposTemp = alfrescoServico.listarGrupos();

			for (UsuarioGrupo usuarioGrupo : gruposTemp) {
				if (!usuarioGrupo.getShortName().contains("site")) {
					this.grupos.add(usuarioGrupo);
				}
			}

		} catch (AdministrationFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void listarGrupoNome() {

		try {

			List<UsuarioGrupo> gruposTemp = new ArrayList<UsuarioGrupo>();
			this.grupos = new ArrayList<UsuarioGrupo>();

			gruposTemp = alfrescoServico.listarGruposLike(this.shortName);

			for (UsuarioGrupo usuarioGrupo : gruposTemp) {
				if (!usuarioGrupo.getShortName().contains("site")) {
					this.grupos.add(usuarioGrupo);
				}

			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public String cadastro() {
		return redirect(CADASTRO);
	}

	public String cancelar() {
		return redirect(PESQUISA);
	}

	public String incluir() {

		try {
			alfrescoServico.incluirGrupo(this.shortName);
			listarGrupoAlfresco();
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
				return getPaginaCadastro();

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
				return redirect(CADASTRO);

			}

		} catch (Exception e) {

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							messengerProperties
									.getProperty("alert.group.create.failed"),
							""));
			return redirect(CADASTRO);

		}

		return redirect(PESQUISA);

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

			// grupoLog.setOperacao("E");
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

			this.usuariosPickList = new DualListModel<Usuario>(this.source,
					this.target);

		} catch (AdministrationFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return redirect(USUARIOGRUPO);
	}

	public String paginaincluir() {
		return redirect(CADASTRO);

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

	public DualListModel<Usuario> getUsuariosPickList() {
		return usuariosPickList;
	}

	public void setUsuariosPickList(DualListModel<Usuario> usuariosPickList) {
		this.usuariosPickList = usuariosPickList;
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