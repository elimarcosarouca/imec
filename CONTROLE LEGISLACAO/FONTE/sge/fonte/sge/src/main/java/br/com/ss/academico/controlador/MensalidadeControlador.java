package br.com.ss.academico.controlador;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import br.com.ss.academico.dominio.Aluno;
import br.com.ss.academico.dominio.Matricula;
import br.com.ss.academico.dominio.Mensalidade;
import br.com.ss.academico.enumerated.StatusPagamento;
import br.com.ss.academico.enumerated.TipoPesquisaData;
import br.com.ss.academico.servico.AlunoServico;
import br.com.ss.academico.servico.MensalidadeServico;
import br.com.ss.core.seguranca.servico.EmpresaServico;
import br.com.ss.core.seguranca.servico.IService;
import br.com.ss.core.web.controlador.ControladorGenerico;
import br.com.ss.core.web.enumerated.Constants;
import br.com.ss.core.web.utils.PageUtils;

@ManagedBean
@SessionScoped
public class MensalidadeControlador extends ControladorGenerico<Mensalidade> {

	private static final long serialVersionUID = -6832271293709421841L;

	private List<Aluno> alunos;

	@ManagedProperty(value = "#{mensalidadeServicoImpl}")
	private MensalidadeServico servico;

	@ManagedProperty(value = "#{alunoServicoImpl}")
	private AlunoServico alunoServico;

	@ManagedProperty(value = "#{empresaServicoImpl}")
	private EmpresaServico empresaServico;

	private List<SelectItem> statusList;

	private Date dataInicio;

	private Date dataFim;

	private TipoPesquisaData tipoPesquisaData;

	private List<SelectItem> tipoPesquisaDataList;
	
	/** Usado para validacao de renderizacao na tela de cadastro. */
	private StatusPagamento statusInicial;

	/** Indica se está vindo do Home ou não. */
	private boolean fromHome;

	private static final String PATH_REPORT = "resources" + File.separator + "jasper" + File.separator;

	@Override
	public void init() {
		pesquisa.setMatricula(new Matricula());
		this.alunos = alunoServico.listarTodos();
		statusList = new ArrayList<SelectItem>();
		for (StatusPagamento status : StatusPagamento.values()) {
			statusList.add(new SelectItem(status, status.getDescricao()));
		}
		tipoPesquisaDataList = new ArrayList<SelectItem>();
		for (TipoPesquisaData tipo : TipoPesquisaData.values()) {
			tipoPesquisaDataList.add(new SelectItem(tipo, tipo.getDescricao()));
		}
		tipoPesquisaData = TipoPesquisaData.VECIMENTO;
		carregarDatas();
	}

	public void carregarDatas() {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		int dia = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int mes = (cal.get(Calendar.MONDAY) + 1);
		int ano = cal.get(Calendar.YEAR);
		try {
			this.dataInicio = (new SimpleDateFormat("dd/MM/yyyy")).parse("01/"
					+ mes + "/" + ano);
			this.dataFim = (new SimpleDateFormat("dd/MM/yyyy")).parse(dia + "/"
					+ mes + "/" + ano);
		} catch (ParseException e) {
		}
	}

	@Override
	public String getNomeRelatorioJasper() {
		return "mensalidade.jasper";
	}

	@Override
	public String getTituloRelatorio() {
		return "RELATÓRIO DE MENSALIDADE";
	}
	

	@Override
	protected IService<Mensalidade, Long> getService() {
		return servico;
	}


	public void pesquisar() {
		this.listaPesquisa = servico.pesquisar(pesquisa, dataInicio, dataFim, tipoPesquisaData);
	}

	@Override
	public String detalhe() {
		String page = super.detalhe();
		validarPreencherDataPagamento();
		setStatusInicial(entidade.getStatusPagamento());
		return page;
	}

	private void validarPreencherDataPagamento() {
		if (StatusPagamento.PENDENTE.equals(entidade.getStatusPagamento())) {
			entidade.setDataPagamento(new Date());
		}
	}
	
	public String detalheHome(Mensalidade mensalidade) {
		this.fromHome = true;
		entidade = mensalidade;
		setStatusInicial(entidade.getStatusPagamento());
		validarPreencherDataPagamento();
		return "/paginas/mensalidade/cadastro.xhtml" + Constants.REDIRECT;
	}

	/**
	 * Lista os Alunos - para a lista do auto-complete da tela de pesquisa.
	 */
	public List<Aluno> listarAlunos(String nome) {
		Aluno aluno = new Aluno();
		aluno.setNome(nome);
		return alunoServico.pesquisar(aluno);
	}

	public String cancelarMensalidade() {
		this.entidade.setStatusPagamento(StatusPagamento.CANCELADO);
		return this.salvar();
	}

	public String salvar() {
		this.entidade.setUsuario(getUsuarioLogado());
		
		if (!StatusPagamento.PENDENTE.equals(entidade.getStatusPagamento())) {
		    // grava o usuario que está atualizando a mensalidade
		    this.entidade.setUsuarioAtualizacao(getUsuarioLogado());
		}
		
		String page = null;
		if (fromHome) {
			page = "home";
			fromHome = false;
		}
		
		return super.salvar(page);
	}

	

	@Override
	public String cancelar() {
		
		entidade = getService().findByPrimaryKey(entidade.getId());
		pesquisar();
		
		if (!fromHome) {
			return super.cancelar();
		}
		fromHome = false;
		PageUtils.redirectForUrl( Constants.INDEX_REDIRECT );
		return null;
	}
	
	/* ------------- Gets/Sets ----------------------- */

	public MensalidadeServico getServico() {
		return servico;
	}

	public void setServico(MensalidadeServico servico) {
		this.servico = servico;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public AlunoServico getAlunoServico() {
		return alunoServico;
	}

	public void setAlunoServico(AlunoServico alunoServico) {
		this.alunoServico = alunoServico;
	}

	public EmpresaServico getEmpresaServico() {
		return empresaServico;
	}

	public void setEmpresaServico(EmpresaServico empresaServico) {
		this.empresaServico = empresaServico;
	}

	public List<SelectItem> getStatusList() {
		return statusList;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public TipoPesquisaData getTipoPesquisaData() {
		return tipoPesquisaData;
	}

	public void setTipoPesquisaData(TipoPesquisaData tipoPesquisaData) {
		this.tipoPesquisaData = tipoPesquisaData;
	}

	public List<SelectItem> getTipoPesquisaDataList() {
		return tipoPesquisaDataList;
	}

	public void setTipoPesquisaDataList(List<SelectItem> tipoPesquisaDataList) {
		this.tipoPesquisaDataList = tipoPesquisaDataList;
	}

//	public void imprimir() {
//		Map<String, Object> parametros = new HashMap<String, Object>();
//		super.imprimir(this.listaPesquisa, parametros, "mensalidade.jasper");
//		
//	}
	
	public void imprimirRecibo(Mensalidade mensalidade)
			throws FileNotFoundException {

		ExternalContext econtext = FacesContext.getCurrentInstance()
				.getExternalContext();
		FacesContext context = FacesContext.getCurrentInstance();

		String webPath = context.getExternalContext().getRealPath("/");
		String reportPath1 = webPath + PATH_REPORT + "recibo.jasper";

		InputStream stream1 = new FileInputStream(reportPath1);

		List<JasperPrint> lista = new ArrayList<JasperPrint>();

		List<Mensalidade> list = new ArrayList<Mensalidade>();
		list.add(mensalidade);

		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("empresa", empresaServico.findOne(1l));
			lista.add(JasperFillManager.fillReport(stream1, params,
					new JRBeanCollectionDataSource(list)));

			JRPdfExporter exporter = new JRPdfExporter();
			HttpServletResponse response = (HttpServletResponse) econtext
					.getResponse();
			FacesContext fcontext = FacesContext.getCurrentInstance();
			ByteArrayOutputStream retorno = new ByteArrayOutputStream();

			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, lista);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, retorno);
			exporter.setParameter(
					JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS,
					Boolean.TRUE);
			exporter.exportReport();

			byte[] retornoArray = retorno.toByteArray();

			response.setContentType("application/pdf");
			response.setContentLength(retornoArray.length);

			OutputStream output = response.getOutputStream();
			output.write(retornoArray);
			output.flush();
			output.close();
			fcontext.responseComplete();

		} catch (RuntimeException e) {
			// logar e tratar exception
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				stream1.close();
			} catch (IOException e) {
				// logar e tratar exception
			}
		}

	}

	public StatusPagamento getStatusInicial() {
		return statusInicial;
	}

	public void setStatusInicial(StatusPagamento statusInicial) {
		this.statusInicial = statusInicial;
	}

}