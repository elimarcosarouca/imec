package br.fucapi.bpms.web.controle;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.alfresco.repo.webservice.administration.AdministrationFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.fucapi.bpms.alfresco.dominio.GrupoAlfresco;
import br.fucapi.bpms.alfresco.dominio.Usuario;
import br.fucapi.bpms.alfresco.servico.AlfrescoServico;
import br.fucapi.bpms.web.dominio.Pendencia;
import br.fucapi.bpms.web.repositorio.PendenciaRepositorio;

@ManagedBean(name = "pendenciaControle")
@SessionScoped
public class PendenciaControle implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory
			.getLogger(PendenciaControle.class);

	private Pendencia entity;
	private Pendencia search;
	private Usuario usuario;
	private Usuario usuarioLogado;
	private GrupoAlfresco grupo = null;

	private boolean proprietario = false;

	private List<Pendencia> resultList = new ArrayList<Pendencia>();
	private List<Usuario> usuarios = new ArrayList<Usuario>();

	@ManagedProperty(value = "#{pendenciaRepositorioImpl}")
	private PendenciaRepositorio pendenciaRepositorio;

	@ManagedProperty(value = "#{alfrescoServicoImpl}")
	private AlfrescoServico alfrescoServico;

	public List<Pendencia> getResultList() {
		return this.resultList;
	}

	public void setResultList(List<Pendencia> resultList) {
		this.resultList = resultList;
	}

	// @PostConstruct
	public String init() {
		try {

			this.usuarioLogado = (Usuario) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("usuarioLogado");

			this.usuario = this.usuarioLogado;

			initEntity();

			pesquisar();

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		try {
			List<GrupoAlfresco> grupos;
			this.usuarios = new ArrayList<Usuario>();
			grupos = alfrescoServico.listarGruposPorUsuario(usuario
					.getUserName());

			// verificar se o usuario é adminstrador
			for (GrupoAlfresco grupoAlfresco : grupos) {
				if (grupoAlfresco.getDisplayName().equals("REVISOR_APROVADOR")) {
					grupo = grupoAlfresco;
					this.usuarios = alfrescoServico.getUsuarios();
					break;
				}
			}

			if (this.usuarios.size() == 0)
				this.usuarios.add(this.usuario);

			this.usuario = new Usuario();

		} catch (AdministrationFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return "/pages/pendencia/search.xhtml";

	}

	@PreDestroy
	public void cleanUp() {
		this.resultList = null;
		this.usuarios = null;
	}

//	public void init() {
//		this.search();
//	}

	protected void initEntity() throws InstantiationException,
			IllegalAccessException {
		this.entity = new Pendencia();
		this.search = new Pendencia();

	}

//	public void search() {
//		this.resultList = pendenciaRepositorio.list();
//	}

	public void confirmar(Pendencia entity) {

		this.entity = entity;
		this.entity.setStatus(true);
		this.entity.setDataConfirmacao(new Date());
		pendenciaRepositorio.saveOrUpdate(entity);

		this.usuario.setUserName(this.entity.getLogin());
		pesquisar();

	}

	public Pendencia getEntity() {
		return entity;
	}

	public void setEntity(Pendencia entity) {
		this.entity = entity;
	}

	public Pendencia getSearch() {
		return search;
	}

	public void setSearch(Pendencia search) {
		this.search = search;
	}

	public void pesquisar() {
		if (this.usuario.getUserName() != null
				&& this.usuario.getUserName().length() > 0)
			this.resultList = pendenciaRepositorio
					.listarPendenciaPorLogin(this.usuario.getUserName());
		else
			this.resultList = pendenciaRepositorio.listarTodasPendentes();
		// verifica se o usuário logado é o proprietario da tarefa
		validarProprietario();
	}

	public String listaPendencia(Pendencia p) {

		if (p.getBusinessKey() != null
				&& this.search.getBusinessKey().length() > 0)
			this.resultList = pendenciaRepositorio
					.listarPendenciaPorBusinessKey(search.getBusinessKey());

		return "/pages/pendencia/search.jsf";
	}

	public PendenciaRepositorio getPendenciaRepositorio() {
		return pendenciaRepositorio;
	}

	public void setPendenciaRepositorio(
			PendenciaRepositorio pendenciaRepositorio) {
		this.pendenciaRepositorio = pendenciaRepositorio;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public GrupoAlfresco getGrupo() {
		return grupo;
	}

	public void setGrupo(GrupoAlfresco grupo) {
		this.grupo = grupo;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public AlfrescoServico getAlfrescoServico() {
		return alfrescoServico;
	}

	public void setAlfrescoServico(AlfrescoServico alfrescoServico) {
		this.alfrescoServico = alfrescoServico;
	}

	public void validarProprietario() {
		if (usuarioLogado.getUserName().equals(this.usuario.getUserName()))
			this.proprietario = true;
		else
			this.proprietario = false;
	}

	public boolean isProprietario() {
		return proprietario;
	}

	public void setProprietario(boolean proprietario) {
		this.proprietario = proprietario;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String teste() {
		System.out.println("teste");

		return "/pages/pendencia/search.xhtml";
	}

	public void imprimir() {
		try {
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();  
	        ServletContext context = (ServletContext) externalContext.getContext();  
	        FacesContext facesContext = FacesContext.getCurrentInstance();
	        String arquivo = context.getRealPath("/WEB-INF/reports/pendencia.jasper");
			System.out.println(arquivo);
			
			FacesContext fc = FacesContext.getCurrentInstance();  
	        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();  

			response.setContentType("application/pdf");
			response.addHeader("Content-disposition",
					"attachment; filename=\"pendencia.pdf\"");

			InputStream inputStream = new FileInputStream(new File(arquivo));

			JasperPrint impressao = JasperFillManager.fillReport(inputStream,
					null,
					new JRBeanCollectionDataSource(this.resultList, false));

			JasperExportManager.exportReportToPdfStream(impressao,
					response.getOutputStream());

			facesContext.responseComplete();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro: " + e.getMessage());
		}
	}

}