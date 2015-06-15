package br.fucapi.ads.modelo.controlador;

import java.io.File;
import java.io.FileInputStream;
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
import javax.validation.constraints.NotNull;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.security.core.context.SecurityContextHolder;

import br.fucapi.ads.modelo.dominio.Alerta;
import br.fucapi.ads.modelo.dominio.VariaveisTarefa;
import br.fucapi.ads.modelo.dominio.VariavelPublicarDocumento;
import br.fucapi.ads.modelo.enumerated.StatusProcesso;
import br.fucapi.ads.modelo.regranegocio.TreinamentoRN;
import br.fucapi.ads.modelo.servico.AlertaServico;
import br.fucapi.ads.modelo.servico.VariaveisTarefaServico;
import br.fucapi.bpms.activiti.dominio.ProcessoDefinicao;
import br.fucapi.bpms.activiti.dominio.ProcessoInstancia;
import br.fucapi.bpms.activiti.dominio.TarefaInstancia;
import br.fucapi.bpms.activiti.servico.ActivitiServico;
import br.fucapi.bpms.activiti.util.JsonUtil;
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

	@ManagedProperty(value = "#{alertaServicoImpl}")
	private AlertaServico alertaServico;

	@ManagedProperty(value = "#{emailControlador}")
	private EmailControlador emailControlador;

	@ManagedProperty(value = "#{variaveisTarefaServicoImpl}")
	private VariaveisTarefaServico variaveisTarefaServico;

	@ManagedProperty(value = "#{sessaoControladorBean}")
	private SessaoControladorBean sessaoControladorBean;

	private final String PESQUISATAREFAPENDENTE = "/paginas/tarefa/pesquisatarefapendente.xhtml?faces-redirect=true";

	private final String DETALHETAREFAPENDENTE = "/paginas/tarefa/publicardocumento/detalhe.xhtml?faces-redirect=true";

	private StreamedContent file;

	@NotNull
	private String parecer;

	private boolean status;

	private Usuario usuarioSelecionado;

	private String login;

	private List<Usuario> listaUsuarios;

	private TarefaInstancia entity;

	private List<TarefaInstancia> listaTarefasPendentes;

	private Date dataInicial;

	private Date dataFinal;

	private DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy HH_mm_ss");

	private Calendar cal = Calendar.getInstance();

	private VariavelPublicarDocumento variaveis;

	private List<ProcessoDefinicao> listaProcessosDefinicao;

	private int totalTarefas;

	private String cookiesResult;

	private List<FileItem> itens;

	/**
	 * Metodo responsavel por verificar a quantidade de tarefas pendentes de um
	 * usuario
	 */
	public void initTotalTarefasUsuario(String login) {

		this.listaTarefasPendentes = this.activitiServico
				.getTarefasUsuario(login);

		this.totalTarefas = (this.listaTarefasPendentes != null) ? this.listaTarefasPendentes
				.size() : 0;

	}

	public void destroyWorld() {
		addMessage("System Error", "Please try again later.");
	}

	public void addMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	@PostConstruct
	public String init() throws ParseException {

		this.usuarioSelecionado = (Usuario) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		this.login = this.usuarioSelecionado.getUserName();

		this.initTotalTarefasUsuario(this.login);

		if (this.usuarioSelecionado.getCapabilities().isAdmin()) {
			this.listaUsuarios = alfrescoServico.getUsuarios();
		} else {
			this.listaUsuarios = new ArrayList<Usuario>();
		}

		this.lerCookie();

		this.variaveis = new VariavelPublicarDocumento();

		this.pesquisar();

		return this.PESQUISATAREFAPENDENTE;

	}

	public byte[] criarPDF() throws IOException {

		InputStream is = new FileInputStream("c:\\teste.pdf");

		System.out.println("CRIANDO PDF");

		return IOUtils.toByteArray(is);

	}

	public String pesquisar() throws ParseException {
		return pesquisar(this.login);
	}

	public String pesquisar(String login) throws ParseException {

		Map<String, Object> filtro = this.filtroVariaveis();

		// Soh deverah trazer as tarefas que estao com o status pendente
		this.listaTarefasPendentes = activitiServico
				.getHistoricoTarefasPorVariaveis(filtro, login,
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
			varTemp.converterListaVariaveis(tarefaInstancia.getVariables());
			tarefaInstancia.setVariaveis(varTemp);
		}

		this.variaveis = new VariavelPublicarDocumento();

		Usuario usuarioLogado = sessaoControladorBean.getUsuario();

		// verificar se as tarefas Ã© do usuÃ¡rio para atualizar o contador
		if (this.usuarioSelecionado.getUserName().equals(
				usuarioLogado.getUserName()))
			this.totalTarefas = this.listaTarefasPendentes.size();

		return this.PESQUISATAREFAPENDENTE;

	}

	public void aprovar() throws Exception {

		String json = "{\"name\":\"aprovacaoOK\", \"value\":true},"
				+ "{\"name\":\"parecer\", \"value\":\"" + this.parecer + "\"}";

		this.activitiServico.completarTarefa(this.entity.getId(), json);

		this.parecer = "";

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Tarefa aprovada com sucesso", "Tarefa aprovada com sucesso!");

		FacesContext.getCurrentInstance().addMessage(null, message);

		this.pesquisar();
	}

	public void reprovar() throws Exception {
		String json = "{\"name\":\"aprovacaoOK\", \"value\":false},"
				+ "{\"name\":\"parecer\", \"value\":\"" + this.parecer + "\"}";

		String processInstanceId = this.entity.getProcessInstanceId();

		activitiServico.completarTarefa(this.entity.getId(), json);
		this.parecer = "";

		salvarVariaveisTarefa(this.entity, false);

		reprovarOutrasTarefas(processInstanceId);

		String MSG = "Tarefa reprovada com sucesso!";

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				MSG, MSG);
		FacesContext.getCurrentInstance().addMessage(null, message);

		this.pesquisar();
	}

	public void notificarPublicacao(TarefaInstancia tarefa) throws Exception {
		this.entity = tarefa;

		obsoletarProcessos(tarefa);

		Alerta alerta = new Alerta();
		alerta.setStatus(StatusProcesso.ATIVO);
		alerta.converterTarefaInstanciaToAlerta(tarefa);
		// inserir o registro de alerta de vencimento
		alertaServico.saveOrUpdate(alerta);

		aprovar();
	}

	/**
	 * metodo utilizado para colocar o alerta da solicitação anterios no status
	 * OBSOLETO
	 * @param tarefa
	 */
	public void buscarAlerta(TarefaInstancia tarefa) {

		if (((VariavelPublicarDocumento) tarefa.getVariaveis()).getIdAlerta() != null
				&& ((VariavelPublicarDocumento) tarefa.getVariaveis())
						.getIdAlerta() >= 1l) {

			Alerta alerta = (Alerta) alertaServico
					.getByPrimaryKey(((VariavelPublicarDocumento) tarefa
							.getVariaveis()).getIdAlerta());
			alerta.setStatus(StatusProcesso.OBSOLETO);
			alertaServico.saveOrUpdate(alerta);
		}

	}

	public void obsoletarProcessos(TarefaInstancia tarefa) {

		Map<String, Object> variaveis = new HashMap<String, Object>();
		variaveis.put("statusProcesso", StatusProcesso.OBSOLETO);
		String json = JsonUtil.converterVariaveisToJson(variaveis);

		List<ProcessoInstancia> listaResultado = new ArrayList<ProcessoInstancia>();

		Map<String, Object> var = new HashMap<String, Object>();

		var.put("protocoloOrigem", ((VariavelPublicarDocumento) tarefa
				.getVariaveis()).getProtocoloOrigem());

		listaResultado = activitiServico
				.getHistoricoProcessosFiltroVariaveis(var);

		for (ProcessoInstancia processoInstancia : listaResultado) {
			System.out.println(processoInstancia.getId());
			if (!tarefa.getProcessInstanceId()
					.equals(processoInstancia.getId())) {
				this.activitiServico.atualizarVariaveis(
						processoInstancia.getId(), json);
			}
		}
	}

	public void reprovarOutrasTarefas(String processInstanceId)
			throws ParseException {
		String json = "{\"name\":\"aprovacaoOK\", \"value\":false},"
				+ "{\"name\":\"parecer\", \"value\":\"" + this.parecer + "\"}";

		List<TarefaInstancia> tasks = activitiServico
				.getTarefasProcessoInstancia(processInstanceId);

		for (TarefaInstancia tarefaInstancia : tasks) {
			if (null != tarefaInstancia.getId())
				activitiServico.completarTarefa(tarefaInstancia.getId(), json);
		}

	}

	public void salvarVariaveisTarefa(TarefaInstancia tarefaInstancia,
			boolean status) {
		VariaveisTarefa variaveisTarefa = new VariaveisTarefa();
		variaveisTarefa.setIdProcesso(Long.valueOf(tarefaInstancia
				.getProcessInstanceId()));

		variaveisTarefa.setIdTarefa(Long.valueOf(tarefaInstancia.getId()));
		variaveisTarefa.setLogin(tarefaInstancia.getAssignee());
		variaveisTarefa.setParecer(this.parecer);
		variaveisTarefa.setStatus(status);

		variaveisTarefaServico.saveOrUpdate(variaveisTarefa);
	}

	public void prepararParecer(TarefaInstancia tarefaInstancia) {
		this.entity = tarefaInstancia;
		this.parecer = "";
	}

	public void concluirWorkFlow(List<String> email) {
		for (String string : email) {
			System.out.println(string);
		}
	}

	public String detalhe(TarefaInstancia tarefa) {
		this.parecer = "";
		this.entity = tarefa;
		return this.telaDetalheTarefaPendente();
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

		if (null != this.variaveis.getProtocolo()
				&& !this.variaveis.getProtocolo().equals("")) {
			var.put("protocolo", this.variaveis.getProtocolo());

		} else if (this.variaveis.getTipoSolicitacao() != null
				&& !this.variaveis.getTipoSolicitacao().equals("")) {

			var.put("tipoSolicitacao", this.variaveis.getTipoSolicitacao());

		}
		return var;
	}

	public void downloadArquivo(TarefaInstancia tarefa) {
		if (this.variaveis.getArquivoDoc() == null) {
			FacesMessage msg = new FacesMessage(
					"A solicitaÃ§Ã£o nÃ£o possui modelo anexado ", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {

			String nomeArquivo = ((VariavelPublicarDocumento) tarefa
					.getVariaveis()).getArquivoControlado().getNomeArquivo();
			String uuidArquivo = ((VariavelPublicarDocumento) tarefa
					.getVariaveis()).getArquivoControlado().getUuid();

			InputStream temp = alfrescoServico.baixarArquivo(nomeArquivo,
					uuidArquivo);
			file = new DefaultStreamedContent(temp, null, nomeArquivo);

		}
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

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
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

	public String telaPesquisaTarefaPendente() {
		return this.PESQUISATAREFAPENDENTE;
	}

	public String telaDetalheTarefaPendente() {
		return this.DETALHETAREFAPENDENTE;
	}

	public TreinamentoRN getTreinamentoRN() {
		return treinamentoRN;
	}

	public void setTreinamentoRN(TreinamentoRN treinamentoRN) {
		this.treinamentoRN = treinamentoRN;
	}

	public StreamedContent getFile() {
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
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

	public AlertaServico getAlertaServico() {
		return alertaServico;
	}

	public void setAlertaServico(AlertaServico alertaServico) {
		this.alertaServico = alertaServico;
	}

	public EmailControlador getEmailControlador() {
		return emailControlador;
	}

	public void setEmailControlador(EmailControlador emailControlador) {
		this.emailControlador = emailControlador;
	}

	public VariaveisTarefaServico getVariaveisTarefaServico() {
		return variaveisTarefaServico;
	}

	public void setVariaveisTarefaServico(
			VariaveisTarefaServico variaveisTarefaServico) {
		this.variaveisTarefaServico = variaveisTarefaServico;
	}

	public SessaoControladorBean getSessaoControladorBean() {
		return sessaoControladorBean;
	}

	public void setSessaoControladorBean(
			SessaoControladorBean sessaoControladorBean) {
		this.sessaoControladorBean = sessaoControladorBean;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}