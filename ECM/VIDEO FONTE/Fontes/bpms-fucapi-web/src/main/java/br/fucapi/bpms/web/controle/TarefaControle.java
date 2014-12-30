package br.fucapi.bpms.web.controle;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.fucapi.bpms.activiti.dominio.TarefaInstancia;
import br.fucapi.bpms.activiti.servico.ActivitiServico;
import br.fucapi.bpms.alfresco.dominio.GrupoAlfresco;
import br.fucapi.bpms.alfresco.dominio.Usuario;
import br.fucapi.bpms.alfresco.servico.AlfrescoServico;
import br.fucapi.bpms.web.dominio.Desenho;
import br.fucapi.bpms.web.dominio.Pendencia;
import br.fucapi.bpms.web.dominio.VariaveisProcesso;
import br.fucapi.bpms.web.repositorio.PendenciaRepositorio;

@ManagedBean(name = "tarefaControle")
@SessionScoped
public class TarefaControle implements Serializable {

	private static final long serialVersionUID = -8945105181334276134L;

	@ManagedProperty(value = "#{activitiServicoImpl}")
	private ActivitiServico activitiServico;

	@ManagedProperty(value = "#{alfrescoServicoImpl}")
	private AlfrescoServico alfrescoServico;

	@ManagedProperty(value = "#{pendenciaRepositorioImpl}")
	private PendenciaRepositorio pendenciaRepositorio;

	private String parecer;

	private boolean status;

	private boolean proprietario = false;

	private GrupoAlfresco grupo = null;

	private StreamedContent file;

	private Usuario usuario;

	private Usuario usuarioLogado;

	private List<Usuario> usuarios = new ArrayList<Usuario>();

	private TarefaInstancia entity;

	private Desenho desenho = new Desenho();

	private List<Desenho> desenhos = new ArrayList<Desenho>();

	private List<TarefaInstancia> resultList = new ArrayList<TarefaInstancia>();

	public String init() {

		this.usuarioLogado = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuarioLogado");

		this.usuario = this.usuarioLogado;

		pesquisar();

		List<GrupoAlfresco> grupos;
		try {
			grupos = alfrescoServico.listarGruposPorUsuario(usuario
					.getUserName());

			// verificar se o usuario é adminstrador
			for (GrupoAlfresco grupoAlfresco : grupos) {
				if (grupoAlfresco.getDisplayName().equals("REVISOR_APROVADOR")) {
					grupo = grupoAlfresco;
					break;
				}
			}

		} catch (AdministrationFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		this.usuarios = alfrescoServico.getUsuarios();
		this.usuario = new Usuario();

		return "/pages/tarefa/search.xhtml";
	}

	public void pesquisar() {
		
		if(this.usuario.getUserName().equals("TODOS"))
			this.resultList = activitiServico.getTodasTarefas();

		else
			this.resultList = activitiServico.getTarefasUsuario(this.usuario
					.getUserName());
			

		VariaveisProcesso variaveisProcesso = null;

		for (TarefaInstancia tarefaInstancia : this.resultList) {
			variaveisProcesso = new VariaveisProcesso();
			variaveisProcesso
					.converterListaVariaveisParaVariaveisProcesso(tarefaInstancia
							.getVariables());
			tarefaInstancia.setVariaveisProcesso(variaveisProcesso);
		}

		validarProprietario();
	}

	public String editar(TarefaInstancia entity) {
		this.parecer = "";
		this.entity = entity;
		this.desenhos = ((VariaveisProcesso) this.entity.getVariaveisProcesso())
				.getDesenhos();

		return "/pages/tarefa/parecer.xhtml";
	}

	public void downloadArquivo() {
		String nomeArquivo = ((VariaveisProcesso) this.entity
				.getVariaveisProcesso()).getArquivo().getNomeArquivo();

		String uuidArquivo = ((VariaveisProcesso) this.entity
				.getVariaveisProcesso()).getArquivo().getUuid();

		InputStream temp = alfrescoServico.baixarArquivo(nomeArquivo,
				uuidArquivo);
		file = new DefaultStreamedContent(temp, null, nomeArquivo);
	}

	public void downloadDesenho() {
		String nomeArquivo = this.desenho.getNomeArquivo();
		System.out.println(desenho.getUuid());

		InputStream temp = alfrescoServico.baixarArquivo(nomeArquivo,
				desenho.getUuid());

		file = new DefaultStreamedContent(temp, null, nomeArquivo);
	}

	public String telaParecer() {
		return "/pages/tarefa/parecer.xhtml";
	}

	public String consultarDesenho(Desenho desenho) {
		this.desenho = desenho;
		return "/pages/tarefa/desenho.xhtml";

	}

	public String telaSearch() {
		return "/pages/tarefa/search.xhtml";
	}

	public String aprovar() {

		String json = "{\"name\":\"status\", \"value\":true},"
				+ "{\"name\":\"parecer\", \"value\":\"" + this.parecer + "\"}";
		System.out.println(this.entity.getId());
		System.out.println(json);
		String result = activitiServico.completarTarefa(this.entity.getId(),
				json);
		System.out.println(result);

		this.usuario.setUserName(this.entity.getAssignee());
		// atualiza a lista de tarefas pendentes
		pesquisar();

		if (result.equals("200")
				&& entity.getTaskDefinitionKey().equals("aprovarDocumento")) {
			Pendencia pendencia;
			VariaveisProcesso variaveisProcesso = (VariaveisProcesso) this.entity
					.getVariaveisProcesso();

			try {

				Set<String> listLogin = new HashSet<String>();
				for (String grupo : variaveisProcesso.getGruposNotificar()) {

					List<Usuario> usuarios = alfrescoServico
							.getUsuariosPorGrupo(grupo);

					for (Usuario usuario : usuarios) {
						listLogin.add(usuario.getUserName());
					}

				}

				for (String login : listLogin) {
					pendencia = new Pendencia();
					pendencia.setBusinessKey(entity.getDescription());
					pendencia.setLogin(login);
					pendencia.setStatus(false);
					pendencia.setProcessInstanceId(this.entity
							.getProcessInstanceId());
					pendencia.setData(new Date());

					pendenciaRepositorio.persist(pendencia);
				}

			} catch (AdministrationFault e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}

		}
		return "/pages/tarefa/search.xhtml";
	}

	public String reprovar() {
		String json = "{\"name\":\"status\", \"value\":false},"
				+ "{\"name\":\"parecer\", \"value\":\"" + this.parecer + "\"}";
		String result = activitiServico.completarTarefa(this.entity.getId(),
				json);
		System.out.println(result);
		pesquisar();
		return "/pages/tarefa/search.xhtml";
	}

	public String completarTarefaNative() {
		// String result = activitiServico.completarTarefaNative();
		//
		// System.out.println(result);
		pesquisar();
		return "/pages/tarefa/search.xhtml";
	}

	public void imprimir() {
		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();  
	        ServletContext context = (ServletContext) externalContext.getContext();  
	        FacesContext facesContext = FacesContext.getCurrentInstance();
	        String arquivo = context.getRealPath("/WEB-INF/reports/tarefa.jasper");
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

	public StreamedContent getFile() {
		return this.file;
	}

	public ActivitiServico getActivitiServico() {
		return activitiServico;
	}

	public void setActivitiServico(ActivitiServico activitiServico) {
		this.activitiServico = activitiServico;
	}

	public AlfrescoServico getAlfrescoServico() {
		return alfrescoServico;
	}

	public void setAlfrescoServico(AlfrescoServico alfrescoServico) {
		this.alfrescoServico = alfrescoServico;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<TarefaInstancia> getResultList() {
		return resultList;
	}

	public void setResultList(List<TarefaInstancia> resultList) {
		this.resultList = resultList;
	}

	public TarefaInstancia getEntity() {
		return entity;
	}

	public void setEntity(TarefaInstancia entity) {
		this.entity = entity;
	}

	public GrupoAlfresco getGrupo() {
		return grupo;
	}

	public void setGrupo(GrupoAlfresco grupo) {
		this.grupo = grupo;
	}

	public String getParecer() {
		return parecer;
	}

	public void setParecer(String parecer) {
		this.parecer = parecer;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<Desenho> getDesenhos() {
		return desenhos;
	}

	public void setDesenhos(List<Desenho> desenhos) {
		this.desenhos = desenhos;
	}

	public Desenho getDesenho() {
		return desenho;
	}

	public void setDesenho(Desenho desenho) {
		this.desenho = desenho;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public PendenciaRepositorio getPendenciaRepositorio() {
		return pendenciaRepositorio;
	}

	public void setPendenciaRepositorio(
			PendenciaRepositorio pendenciaRepositorio) {
		this.pendenciaRepositorio = pendenciaRepositorio;
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

}