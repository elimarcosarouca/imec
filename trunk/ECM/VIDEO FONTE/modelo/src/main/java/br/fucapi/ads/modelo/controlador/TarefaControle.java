package br.fucapi.ads.modelo.controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;

import br.fucapi.ads.modelo.dominio.Documento;
import br.fucapi.ads.modelo.dominio.UUIDNodeRef;
import br.fucapi.ads.modelo.dominio.VariaveisTreinamento;
import br.fucapi.ads.modelo.dominio.VariavelPublicarDocumento;
import br.fucapi.ads.modelo.regranegocio.TreinamentoRN;
import br.fucapi.bpms.activiti.dominio.ProcessoDefinicao;
import br.fucapi.bpms.activiti.dominio.ProcessoInstancia;
import br.fucapi.bpms.activiti.dominio.TarefaInstancia;
import br.fucapi.bpms.activiti.servico.ActivitiServico;
import br.fucapi.bpms.alfresco.dominio.Usuario;
import br.fucapi.bpms.alfresco.servico.AlfrescoServico;

@ManagedBean
@SessionScoped
public class TarefaControle implements Serializable {

	private static final long serialVersionUID = -8945105181334276134L;

	@ManagedProperty(value = "#{activitiServicoImpl}")
	private ActivitiServico activitiServico;

	@ManagedProperty(value = "#{alfrescoServicoImpl}")
	private AlfrescoServico alfrescoServico;

	@ManagedProperty(value = "#{treinamentoRN}")
	private TreinamentoRN treinamentoRN;

	@Value("${uuid.parent.requerimentos.treinamentos}")
	private String parentTreinamento;

	private final String PESQUISATAREFAPENDENTE = "paginas/tarefa/pesquisatarefapendente.xhtml";

	private final String DETALHETAREFAPENDENTE = "paginas/tarefa/detalhe.xhtml";

	private String parecer;

	private boolean status;

	private Usuario usuario;

	private ProcessoDefinicao processoDefinicao;

	private TarefaInstancia entity;

	private List<TarefaInstancia> listaTarefasPendentes;

	private Date dataInicial;

	private Date dataFinal;

	private DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy HH_mm_ss");

	private Calendar cal = Calendar.getInstance();

	@ManagedProperty(value = "#{paginaCentralControladorBean}")
	private PaginaCentralControladorBean paginaCentralControladorBean;

	// private VariaveisTreinamento variaveisTreinamento = new
	// VariaveisTreinamento();

	private VariavelPublicarDocumento variaveis;

	private List<ProcessoDefinicao> listaProcessosDefinicao;

	private int totalTarefas;

	private String cookiesResult;

	private List<FileItem> itens;

	private List<Documento> documentos;

	/**
	 * Metodo responsavel por verificar a quantidade de tarefas pendentes de um
	 * usuario
	 */
	public void initTotalTarefasUsuario() {
		this.usuario = ((Usuario) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal());

		this.listaTarefasPendentes = this.activitiServico
				.getTarefasUsuario(this.usuario.getUserName());

		this.totalTarefas = (this.listaTarefasPendentes != null) ? this.listaTarefasPendentes
				.size() : 0;

	}

	@PostConstruct
	public void init() throws ParseException {

		this.initTotalTarefasUsuario();

		this.processoDefinicao = new ProcessoDefinicao();
		this.variaveis = new VariavelPublicarDocumento();
		this.usuario = (Usuario) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		this.listaProcessosDefinicao = activitiServico
				.getProcessosDefinicaoPorQueryLastVersion();
		this.listaTarefasPendentes = new ArrayList<TarefaInstancia>();

		this.pesquisar();
		this.lerCookie();
	}

	public byte[] criarPDF() throws IOException {

		InputStream is = new FileInputStream("c:\\teste.pdf");

		System.out.println("CRIANDO PDF");

		return IOUtils.toByteArray(is);

	}

	public String pesquisar() throws ParseException {

		Map<String, Object> filtro = this.filtroVariaveis();

		// Soh deverah trazer as tarefas que estao com o status pendente
		this.listaTarefasPendentes = activitiServico
				.getHistoricoTarefasPorVariaveis(filtro,
						this.usuario.getUserName(),
						this.variaveis.getTipoSolicitacao(), true, null);

		VariavelPublicarDocumento varTemp = null;
		for (TarefaInstancia tarefaInstancia : this.listaTarefasPendentes) {

			// Solucao adotada para exibir no formulario o nome do processo sem
			// o ID.
			if (tarefaInstancia.getProcessDefinitionId() != null) {
				String args[] = tarefaInstancia.getProcessDefinitionId().split(
						":");
				tarefaInstancia.setProcessDefinitionId(args[0]);
			}

			varTemp = new VariavelPublicarDocumento();
			varTemp.converterListaVariaveisParaVariaveisProcesso(tarefaInstancia
					.getVariables());
			tarefaInstancia.setVariaveis(varTemp);
		}

		this.variaveis = new VariavelPublicarDocumento();

		// verificar se as tarefas é do usuário para atualizar o contador
		if (this.usuario.getUserName().equals(
				((Usuario) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal()).getUserName()))
			this.totalTarefas = this.listaTarefasPendentes.size();

		return "";

	}

	public void aprovar(TarefaInstancia tarefa) throws ParseException {

		this.anexarDocumentosAlfresco();

		String jsonDocumentos = Documento.listToJson(this.documentos);

		String json = "{\"name\":\"aprovacaoOK\", \"value\":true},"
				+ "{\"name\":\"parecer\", \"value\":\"" + "teste" + "\"},"
				+ "{\"name\":\"documentos\", \"value\":" + jsonDocumentos + "}";

		this.activitiServico.completarTarefa(tarefa.getId(), json);

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Tarefa aprovada com sucesso", "Tarefa aprovada com sucesso!");

		FacesContext.getCurrentInstance().addMessage(null, message);

		this.pesquisar();
		this.telaPesquisaTarefaPendente();
	}

	public void reprovar(TarefaInstancia tarefa) throws ParseException {
		String json = "{\"name\":\"aprovacaoOK\", \"value\":false},"
				+ "{\"name\":\"parecer\", \"value\":\"" + this.parecer + "\"}";

		String processInstanceId = tarefa.getProcessInstanceId();

		activitiServico.completarTarefa(tarefa.getId(), json);

		reprovarOutrasTarefas(processInstanceId);

		String MSG = "Tarefa reprovada com sucesso!";

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				MSG, MSG);
		FacesContext.getCurrentInstance().addMessage(null, message);

		this.pesquisar();
		this.telaPesquisaTarefaPendente();
	}

	public void reprovarOutrasTarefas(String processInstanceId)
			throws ParseException {
		String json = "{\"name\":\"aprovacaoOK\", \"value\":false},"
				+ "{\"name\":\"parecer\", \"value\":\"" + this.parecer + "\"}";

		List<TarefaInstancia> tasks = activitiServico
				.getTarefasProcessoInstancia(processInstanceId);

		for (TarefaInstancia tarefaInstancia : tasks) {
			activitiServico.completarTarefa(tarefaInstancia.getId(), json);
		}

	}

	public void detalhe(TarefaInstancia tarefa) {
		this.parecer = "";
		this.entity = tarefa;
		this.telaDetalheTarefaPendente();
	}

	public void listarTarefas(ProcessoInstancia entity) {
		this.listaTarefasPendentes = activitiServico
				.getHistoricoTarefasProcessoInstancia(entity.getId());
	}

	@SuppressWarnings("unused")
	public void lerCookie() {

		this.cookiesResult = "";

		try {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) facesContext
					.getExternalContext().getRequest();

			// obtem a lista de cookies
			Cookie[] cookies = request.getCookies();

			String sessionId = "JSESSIONID=" + request.getSession().getId();
			System.out.println("RETORNANDO SESSION ID: " + sessionId);
			cookiesResult = sessionId;
			if (true)
				return;

			// foreach
			for (Cookie cookie : cookies) {
				if (cookie.getName().trim().equalsIgnoreCase("JSESSIONID")) {
					this.cookiesResult += cookie.getName() + "="
							+ cookie.getValue();
				}
			}
			// this.cookiesResult +=";";

		} catch (Exception x) {
			x.printStackTrace();
		}

		System.out.println(this.cookiesResult);
	}

	public void imprimir() {
		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance()
					.getExternalContext();
			ServletContext context = (ServletContext) externalContext
					.getContext();
			FacesContext facesContext = FacesContext.getCurrentInstance();
			String arquivo = context
					.getRealPath("/WEB-INF/reports/tarefa.jasper");
			System.out.println(arquivo);

			FacesContext fc = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) fc
					.getExternalContext().getResponse();

			response.setContentType("application/pdf");
			response.addHeader("Content-disposition",
					"attachment; filename=\"pendencia.pdf\"");

			InputStream inputStream = new FileInputStream(new File(arquivo));

			JasperPrint impressao = JasperFillManager.fillReport(inputStream,
					null, new JRBeanCollectionDataSource(
							this.listaTarefasPendentes, false));

			JasperExportManager.exportReportToPdfStream(impressao,
					response.getOutputStream());

			facesContext.responseComplete();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro: " + e.getMessage());
		}
	}

	public void listarTodasTarefas() {

		try {
			this.listaTarefasPendentes = activitiServico.getTodasTarefas();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro: " + e.getMessage());
		}
	}

	private Map<String, Object> filtroVariaveis() {

		Map<String, Object> var = new HashMap<String, Object>();

		if (this.variaveis.getProtocolo() != null
				&& !this.variaveis.getProtocolo().equals("")) {
			var.put("protocolo", this.variaveis.getProtocolo());

		} else if (this.variaveis.getTipoSolicitacao() != null
				&& !this.variaveis.getTipoSolicitacao().equals("")) {

			var.put("tipoSolicitacao", this.variaveis.getTipoSolicitacao());

		}
		return var;
	}

	public void anexarDocumentosAlfresco() {

		this.documentos = new ArrayList<Documento>();
		Documento doc = null;

		try {
			String nomePasta = +((VariaveisTreinamento) this.entity
					.getVariaveis()).getAno()
					+ ""
					+ ((VariaveisTreinamento) this.entity.getVariaveis())
							.getSequencial();

			File file = null;

			for (FileItem item : this.itens) {

				String nomeArquivo = item.getName();
				doc = new Documento();

				file = new File(nomeArquivo);

				item.write(file);

				String json = alfrescoServico.anexarArquivo(parentTreinamento,
						nomePasta, null, nomeArquivo, this.usuario.getTicket(),
						file);

				UUIDNodeRef nodeRef = UUIDNodeRef.fromJsonToUUIDNodeRef(json);

				doc.setUuid(nodeRef.getNodeRef());
				doc.setFile(null);
				doc.setNomeArquivo(nomeArquivo);

				this.documentos.add(doc);
			}

		} catch (Exception e) {

		}
	}

	public void SalvarPDF(final byte[] pdfAssinado, final String nomeArquivo)
			throws IOException {

		File file = new File("C:/assinatura" + getDateTime() + ".pdf");
		FileOutputStream in = new FileOutputStream(file);
		in.write(pdfAssinado);
		in.close();

	}

	public StreamedContent getFile(InputStream fPdf) {
		return new DefaultStreamedContent(fPdf, "application/pdf", "DOCUMENTO_"
				+ dateFormat.format(cal.getTime()) + ".pdf");
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

	public ProcessoDefinicao getProcessoDefinicao() {
		return processoDefinicao;
	}

	public void setProcessoDefinicao(ProcessoDefinicao processoDefinicao) {
		this.processoDefinicao = processoDefinicao;
	}

	public List<TarefaInstancia> getListaTarefasPendentes() {
		return listaTarefasPendentes;
	}

	public void setListaTarefasPendentes(
			List<TarefaInstancia> listaTarefasPendentes) {
		this.listaTarefasPendentes = listaTarefasPendentes;
	}

	public TarefaInstancia getEntity() {
		return entity;
	}

	public void setEntity(TarefaInstancia entity) {
		this.entity = entity;
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

	public int getTotalTarefas() {
		return totalTarefas;
	}

	public void setTotalTarefas(int totalTarefas) {
		this.totalTarefas = totalTarefas;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public PaginaCentralControladorBean getPaginaCentralControladorBean() {
		return paginaCentralControladorBean;
	}

	public void setPaginaCentralControladorBean(
			PaginaCentralControladorBean paginaCentralControladorBean) {
		this.paginaCentralControladorBean = paginaCentralControladorBean;
	}

	public VariavelPublicarDocumento getVariaveis() {
		return variaveis;
	}

	public void setVariaveis(VariavelPublicarDocumento variaveis) {
		this.variaveis = variaveis;
	}

	public List<ProcessoDefinicao> getListaProcessosDefinicao() {
		return listaProcessosDefinicao;
	}

	public void setListaProcessosDefinicao(
			List<ProcessoDefinicao> listaProcessosDefinicao) {
		this.listaProcessosDefinicao = listaProcessosDefinicao;
	}

	public String getCookiesResult() {
		return cookiesResult;
	}

	public void setCookiesResult(String cookiesResult) {
		this.cookiesResult = cookiesResult;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public List<FileItem> getItens() {
		return itens;
	}

	public void setItens(List<FileItem> itens) {
		this.itens = itens;
	}

	public void telaPesquisaTarefaPendente() {
		paginaCentralControladorBean
				.setPaginaCentral(this.PESQUISATAREFAPENDENTE);
	}

	public void telaDetalheTarefaPendente() {
		paginaCentralControladorBean
				.setPaginaCentral(this.DETALHETAREFAPENDENTE);
	}

	public TreinamentoRN getTreinamentoRN() {
		return treinamentoRN;
	}

	public void setTreinamentoRN(TreinamentoRN treinamentoRN) {
		this.treinamentoRN = treinamentoRN;
	}

	public boolean getButtonReporvar() {
		if (this.parecer.length() > 0)
			return true;
		else
			return false;
	}

	public void update(String parecer) {
		setParecer(parecer);
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy HHmmss");
		Date date = new Date();
		return dateFormat.format(date);
	}

}