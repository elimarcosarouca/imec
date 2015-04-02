package br.fucapi.ads.modelo.controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.alfresco.repo.webservice.administration.AdministrationFault;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;

import br.fucapi.ads.modelo.dominio.Protocolo;
import br.fucapi.bpms.activiti.dominio.ProcessoDefinicao;
import br.fucapi.bpms.activiti.dominio.ProcessoInstancia;
import br.fucapi.bpms.activiti.dominio.TarefaInstancia;
import br.fucapi.bpms.activiti.servico.ActivitiServico;
import br.fucapi.bpms.alfresco.dominio.Usuario;
import br.fucapi.bpms.alfresco.dominio.UsuarioGrupo;
import br.fucapi.bpms.alfresco.servico.AlfrescoServico;

@ManagedBean(name = "processoControle")
@SessionScoped
public class ProcessoControle implements Serializable {

	private static final long serialVersionUID = 13244234324234332L;

	private SimpleDateFormat dateFormat = new SimpleDateFormat(
			"dd/MM/yyyy - hh:mm:ss");

	private ProcessoInstancia processoInstancia;
	private ProcessoDefinicao processoDefinicao;
	private boolean habilitar = false;
	private boolean habilitarDesenho = false;

	private List<ProcessoInstancia> processos;
	private List<ProcessoDefinicao> listaProcessosDefinicao;
	private List<TarefaInstancia> tarefaInstancias;

	private String descricao;
	private Protocolo protocolo;

	private Usuario aprovador;
	private Usuario revisor1;
	private Usuario revisor2;
	private Usuario usuario;
	private List<UsuarioGrupo> gruposAlfresco;
	private List<Usuario> usuariosGrupoRevisores;

	private String status;
	private List<SelectItem> listaStatus;

	private DualListModel<UsuarioGrupo> gruposDualListModel;

	private String sequencial;
	private String ano;

	private StreamedContent file;

	private ProcessoInstancia processoStart;

	@ManagedProperty(value = "#{activitiServicoImpl}")
	private ActivitiServico activitiServico;

	@ManagedProperty(value = "#{alfrescoServicoImpl}")
	private AlfrescoServico alfrescoServico;

	@ManagedProperty(value = "#{bpmswebproperties}")
	private Properties bpmswebproperties;

	public String init() {

		this.usuario = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuarioLogado");

		this.processoInstancia = new ProcessoInstancia();
		this.processoDefinicao = new ProcessoDefinicao();
		this.revisor1 = new Usuario();
		this.revisor2 = new Usuario();
		this.aprovador = new Usuario();

		this.gruposAlfresco = new ArrayList<UsuarioGrupo>();
		this.usuariosGrupoRevisores = new ArrayList<Usuario>();
		this.gruposDualListModel = new DualListModel<UsuarioGrupo>();

		this.pesquisar();

		return "/pages/processo/search.xhtml";
	}

	public Usuario getRevisor1() {
		return revisor1;
	}

	public void setRevisor1(Usuario revisor1) {
		this.revisor1 = revisor1;
	}

	public Usuario getRevisor2() {
		return revisor2;
	}

	public void setRevisor2(Usuario revisor2) {
		this.revisor2 = revisor2;
	}

	public String selecionar() {
		this.listaProcessosDefinicao = activitiServico.getProcessosDefinicao();
		return "/pages/processo/selecionar.xhtml";
	}

	public String criar() throws AdministrationFault, RemoteException {

		listarGrupoAlfresco();
		getUsuariosPorGrupo();

		return "/pages/processo/start.xhtml";
	}

	public void getUsuariosPorGrupo() {
		try {
			this.usuariosGrupoRevisores = alfrescoServico
					.getUsuariosPorGrupo(bpmswebproperties
							.getProperty("bpms.fucapi.grupo"));
		} catch (AdministrationFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void listarGrupoAlfresco() {
		try {

			List<UsuarioGrupo> list = alfrescoServico.listarGrupos();

			this.gruposAlfresco = new ArrayList<UsuarioGrupo>();

			for (Iterator<UsuarioGrupo> iterator = list.iterator(); iterator
					.hasNext();) {
				UsuarioGrupo grupo = (UsuarioGrupo) iterator.next();
				// filtrar apenas os grupos e exlui os site
				if (!grupo.getDisplayName().substring(0, 10)
						.equals("site_swsdp"))
					this.gruposAlfresco.add(grupo);
			}

			this.gruposDualListModel.setSource(this.gruposAlfresco);
			this.gruposDualListModel.setTarget(new ArrayList<UsuarioGrupo>());
		} catch (AdministrationFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void pesquisar() {
		List<ProcessoInstancia> listaResultado = null;
		// VariaveisProcesso variaveisProcesso = null;
		this.processos = new ArrayList<ProcessoInstancia>();

		// if (this.origem.getId() == null) {
		listaResultado = activitiServico.getHistoricoProcessosFiltroVariaveis(
				new HashMap<String, Object>());
		// } else {
		// Map<String, Object> var = this.filtroVariaveis();
		// listaResultado = activitiServico
		// .getHistoricoProcessosFiltroVariaveis(var, this.status);
		// }

		// for (ProcessoInstancia pInstancia : listaResultado) {
		// variaveisProcesso = new VariaveisProcesso();
		// variaveisProcesso
		// .converterListaVariaveisParaVariaveisProcesso(pInstancia
		// .getVariables());
		//
		// pInstancia.setVariaveisProcesso(variaveisProcesso);
		// this.processos.add(pInstancia);
		// }
	}

	public String iniciar() {

		this.protocolo = null; // protocoloRepositorio.gerarProtocolo();

		this.processoStart = new ProcessoInstancia();
		this.processoStart.setProcessDefinitionId(processoDefinicao.getId());
		this.processoStart.setBusinessKey(protocolo.toString());
		this.processoStart.setProcessDefinitionId(this.processoDefinicao
				.getId());

		return "/pages/processo/lista_desenho.xhtml";
	}

	public List<ProcessoInstancia> getProcessos() {
		return this.processos;
	}

	public void setProcessos(List<ProcessoInstancia> processos) {
		this.processos = processos;
	}

	public List<ProcessoDefinicao> getListaProcessosDefinicao() {
		return this.listaProcessosDefinicao;
	}

	public void setListaProcessosDefinicao(
			List<ProcessoDefinicao> listaProcessosDefinicao) {
		this.listaProcessosDefinicao = listaProcessosDefinicao;
	}

	public ProcessoInstancia getProcessoInstancia() {
		return processoInstancia;
	}

	public void setProcessoInstancia(ProcessoInstancia processoInstancia) {
		this.processoInstancia = processoInstancia;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public ProcessoDefinicao getProcessoDefinicao() {
		return processoDefinicao;
	}

	public void setProcessoDefinicao(ProcessoDefinicao processoDefinicao) {
		this.processoDefinicao = processoDefinicao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Protocolo getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(Protocolo protocolo) {
		this.protocolo = protocolo;
	}

	public List<TarefaInstancia> getTarefaInstancias() {
		return tarefaInstancias;
	}

	public void setTarefaInstancias(List<TarefaInstancia> tarefaInstancias) {
		this.tarefaInstancias = tarefaInstancias;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getAprovador() {
		return aprovador;
	}

	public void setAprovador(Usuario aprovador) {
		this.aprovador = aprovador;
	}

	public List<UsuarioGrupo> getGruposAlfresco() {
		return gruposAlfresco;
	}

	public List<Usuario> getUsuariosGrupoRevisores() {
		return usuariosGrupoRevisores;
	}

	public void setUsuariosGrupoRevisores(List<Usuario> usuariosGrupoRevisores) {
		this.usuariosGrupoRevisores = usuariosGrupoRevisores;
	}

	public DualListModel<UsuarioGrupo> getGruposDualListModel() {
		return gruposDualListModel;
	}

	public void setGruposDualListModel(
			DualListModel<UsuarioGrupo> gruposDualListModel) {
		this.gruposDualListModel = gruposDualListModel;
	}

	public Properties getBpmswebproperties() {
		return bpmswebproperties;
	}

	public void setBpmswebproperties(Properties bpmswebproperties) {
		this.bpmswebproperties = bpmswebproperties;
	}

	public void setGruposAlfresco(List<UsuarioGrupo> gruposAlfresco) {
		this.gruposAlfresco = gruposAlfresco;
	}

	public ActivitiServico getActivitiServico() {
		return activitiServico;
	}

	public void setActivitiServico(ActivitiServico activitiServico) {
		this.activitiServico = activitiServico;
	}

	public ProcessoInstancia getProcessoStart() {
		return processoStart;
	}

	public void setProcessoStart(ProcessoInstancia processoStart) {
		this.processoStart = processoStart;
	}

	public AlfrescoServico getAlfrescoServico() {
		return alfrescoServico;
	}

	public void setAlfrescoServico(AlfrescoServico alfrescoServico) {
		this.alfrescoServico = alfrescoServico;
	}

	public boolean isHabilitar() {
		return habilitar;
	}

	public boolean isHabilitarDesenho() {
		return habilitarDesenho;
	}

	public void setHabilitarDesenho(boolean habilitarDesenho) {
		this.habilitarDesenho = habilitarDesenho;
	}

	public void imprimir() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();

			HttpServletResponse response = (HttpServletResponse) context
					.getExternalContext().getResponse();

			response.setContentType("application/pdf");
			response.addHeader("Content-disposition",
					"attachment; filename=\"pendencia.pdf\"");

			File file = new File("C:\\relatoriosiemens\\pendencia.jasper");

			InputStream inputStream = new FileInputStream(file);

			JasperPrint impressao = JasperFillManager.fillReport(inputStream,
					null, new JRBeanCollectionDataSource(null, false));

			JasperExportManager.exportReportToPdfStream(impressao,
					response.getOutputStream());

			context.responseComplete();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro: " + e.getMessage());
		}
	}
}