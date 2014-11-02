package br.com.ss.academico.controlador;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import br.com.ss.academico.dominio.Aluno;
import br.com.ss.academico.dominio.Configuracao;
import br.com.ss.academico.dominio.Matricula;
import br.com.ss.academico.dominio.Mensalidade;
import br.com.ss.academico.dominio.Observacao;
import br.com.ss.academico.dominio.Turma;
import br.com.ss.academico.enumerated.StatusMatricula;
import br.com.ss.academico.enumerated.StatusPagamento;
import br.com.ss.academico.servico.AlunoServico;
import br.com.ss.academico.servico.BoletimServico;
import br.com.ss.academico.servico.MatriculaServico;
import br.com.ss.academico.servico.MensalidadeServico;
import br.com.ss.academico.servico.TurmaServico;
import br.com.ss.core.seguranca.servico.IService;
import br.com.ss.core.web.controlador.ControladorGenerico;
import br.com.ss.core.web.enumerated.Constants;
import br.com.ss.core.web.enumerated.Meses;
import br.com.ss.core.web.enumerated.NaoSim;
import br.com.ss.core.web.ireport.RelatorioUtil;
import br.com.ss.core.web.utils.DateUtil;
import br.com.ss.core.web.utils.FacesUtils;

@ManagedBean
@SessionScoped
public class AlunoMatriculaControlador extends ControladorGenerico<Matricula> {

	private static final long serialVersionUID = 2272887087315382335L;

	@ManagedProperty(value = "#{mensalidadeServicoImpl}")
	private MensalidadeServico servicoMensalidade;

	@ManagedProperty(value = "#{matriculaServicoImpl}")
	private MatriculaServico servicoMatricula;

	@ManagedProperty(value = "#{alunoServicoImpl}")
	private AlunoServico servicoAluno;
	
	@ManagedProperty(value = "#{turmaServicoImpl}")
	private TurmaServico servicoTurma;
	
	@ManagedProperty(value = "#{boletimServicoImpl}")
	private BoletimServico boletimServico;
	
	@ManagedProperty(value = "#{relatorioUtil}")
	protected RelatorioUtil relatorioUtil;

	private Aluno alunoMatricula;

	private boolean modalCadastro;

	private List<Turma> turmas;

	private List<SelectItem> naoSimList;
	
	private List<SelectItem> statusMatriculaList;

	private List<SelectItem> mesesList;

	private Meses mesSelecionado;

	private Observacao observacaoMatricula;
	
	private boolean novo;
	/** Backup para retornar do modal de cadastro de matricula. */
	private Aluno alunoTmp;
	
	/* --------- Overrides ------------------ */

	public void init() {
		alunoMatricula = new Aluno();
		naoSimList = createNaoSimList();
		statusMatriculaList = createStatusMatriculaList();
		mesesList = createMesesList();
	}


	@Override
	protected String getNomeRelatorioJasper() {
		// nao tem relatorio
		return null;
	}

	@Override
	public String getTituloRelatorio() {
		return null;
	}
	
	@Override
	protected IService<Matricula, Long> getService() {
		return servicoMatricula;
	}
	
	@Override
	public void pesquisar() {
		// não faz pesquisa neste bean.. apenas no MatriculaControlador.
	}

	@Override
	public String novo() {
		novo = true;
		String page = super.novo();
		alunoMatricula = null;
		carregarMesSelecionado();
		entidade.setStatus(StatusMatricula.ATIVA);
		entidade.setData(new Date());
		carregarTurmas();
		return page;
	}
	
	
	@Override
	public String detalhe(Matricula entidade) {
		novo = false;
		alunoMatricula = entidade.getAluno();
		// carrega as regras da tela de cadastro
		String page = super.detalhe(entidade);
		showModalCadastroMatricula(entidade);
		return page;
	}
	
	public void imprimirContrato(Matricula matricula) {
		List<Matricula> lista = new ArrayList<Matricula>();
		lista.add(matricula);
		Map<String, Object> parametros = new HashMap<String, Object>();

		relatorioUtil.gerarRelatorioComDownload(lista, parametros, "contrato.jasper");
	}
	
	public void renderObservacao() {
		if ( observacaoMatricula == null) {
			observacaoMatricula = new Observacao();
			observacaoMatricula.setMatricula(entidade);
			loadObservacoes();
		}
	}

	/**
	 * Carrega as observacoes da Matricula, para evitar o LazyInitializationException.
	 */
	private void loadObservacoes() {
		List<Observacao> observacoes = servicoMatricula.loadObservacoes(entidade);
		entidade.setObservacoes( observacoes );
	}

	
	public void showModalMatricula(Aluno aluno) {
		alunoMatricula = aluno;
		alunoTmp = aluno;
		showModalPesquisaMatricula();
	}

	private List<SelectItem> createNaoSimList() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		for (NaoSim c : NaoSim.values()) {
			list.add(new SelectItem(c.getValue(), c.getDescricao()));
		}
		return list;
	}

	private List<SelectItem> createStatusMatriculaList() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		for (StatusMatricula c : StatusMatricula.values()) {
			list.add(new SelectItem(c, c.getDescricao()));
		}
		return list;
	}

	private List<SelectItem> createMesesList() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		for (Meses mes : Meses.values()) {
			list.add(new SelectItem(mes, mes.getDescricao()));
		}
		return list;
	}
	
	public void retornarModalPesquisaMatricula() {
		alunoMatricula = alunoTmp;
		showModalPesquisaMatricula();
	}
	
	public void showModalPesquisaMatricula() {
		modalCadastro = false;
		alunoMatricula = servicoAluno.findByPrimaryKey(alunoMatricula.getId());
		List<Matricula> matriculas = servicoMatricula.findByAluno(alunoMatricula);
		alunoMatricula.setMatriculas(matriculas);
		entidade = null;
		mesSelecionado = null;
		observacaoMatricula = null;
	}


	public void showModalCadastroMatricula(Matricula matricula) {
		// faz o load das mensalidades (fetch)
		this.entidade = servicoMatricula.loadMatriculaMensalidades(matricula);
		modalCadastro = true;
		carregarMesSelecionado();
		carregarTurmas();
		selectTurma(!matricula.isPersistent());
		validarObservacao();
	}

	/**
	 * Carrega a ultima observacao para exibir no modal.
	 */
	private void validarObservacao() {
		if ( entidade.getStatus() != StatusMatricula.ATIVA ) {
			
			loadObservacoes();
			
			// apenas situacao: cancelada
			statusMatriculaList = new ArrayList<SelectItem>();
			statusMatriculaList.add(new SelectItem(StatusMatricula.CANCELADA, StatusMatricula.CANCELADA.getDescricao()));
			
			if ( !entidade.getObservacoes().isEmpty() ) {
				observacaoMatricula = entidade.getObservacoes().get(0);
				for (Observacao ob : entidade.getObservacoes() ) {
					if ( observacaoMatricula.getId() < ob.getId() ) {
						observacaoMatricula = ob;
					}
				}
			}
		} else {
			// carrega todas as opcoes para situacao
			statusMatriculaList = createStatusMatriculaList();
		}
	}

	/**
	 * Nova matricula.
	 */
	public void showModalCadastroMatricula() {
		modalCadastro = true;
		entidade = createMatricula();
		carregarTurmas();
		carregarMesSelecionado();
		statusMatriculaList = createStatusMatriculaList();
	}

	/**
	 * Lista as turmas 'vigentes'.
	 */
	private void carregarTurmas() {
		turmas = servicoTurma.listarTurmasVigentes();
	}

	private void carregarMesSelecionado() {
		int mes;
		if ( entidade.isPersistent() ) {
			mes = servicoMensalidade.getMenorMensalidadeMatricula(entidade);
		} else {
			mes = DateUtil.getMes(new Date());
		}
		mesSelecionado = Meses.getEnum(++mes);
	}

	private Matricula createMatricula() {
		Matricula matricula = new Matricula();
		matricula.setData(new Date());
		matricula.setStatus(StatusMatricula.ATIVA);
		return matricula;
	}

	/**
	 * ChangeListener do combo de turma.
	 * @param event
	 */
	public void turmaChanged(ValueChangeEvent e) {
		Turma turma = (Turma) e.getNewValue();
		entidade.setTurma(turma);
		selectTurma(true);
	}

	public void selectTurma(boolean atualizarValor) {
		
		if (atualizarValor) {
			// atualiza o valor da matricula pelo valor do curso selecionado
			entidade.setValor(entidade.getTurma().getCurso().getValor());
			
			// reseta o combo qdo mudar a turma
			entidade.setIntegral(false);
		}

		Long vagasDisponiveis = servicoMatricula.countVagasDisponiveis(entidade.getTurma());
		entidade.getTurma().setVagasDisponiveis(vagasDisponiveis.intValue());

		if (!(vagasDisponiveis > 0)) {
			showMessage("Não há vaga disponível para a turma selecionada.", FacesMessage.SEVERITY_WARN);
		}
	}

	
	public void salvarMatricula() {
		salvar();
	}
	
	public String salvar() {

		try {
			// cria ou atualiza as mensalidades
			boolean persistent = entidade.isPersistent();
			
			if (entidade.getStatus() != StatusMatricula.ATIVA
					&& observacaoMatricula != null) {
				// se houver observacao é pq a matricula nao está ativa
				// salva a observacao da matricula
				cancelarMatricula();
				
			} else if (entidade.getStatus() == StatusMatricula.ATIVA ) {
				if (observacaoMatricula != null) {
					entidade.getObservacoes().remove(observacaoMatricula);
				}
				gerarMensalidadesMatricula(persistent);
			}

			entidade.setAluno(alunoMatricula);
			servicoMatricula.salvar(entidade);
			
			if (!persistent ) {
				// No cadastro, gera o boletim para o aluno matriculado
				this.boletimServico.gerarBoletim(entidade);
			}

			showModalPesquisaMatricula();
			Aluno alunoBkp = alunoMatricula;
			
			showMessage(Constants.MSG_SUCESSO, FacesMessage.SEVERITY_INFO);
			observacaoMatricula = null;

			setup();
			// recupera o aluno pois faz o reset do aluno no init
			alunoMatricula = alunoBkp;
			return PESQUISA;
		} catch (Exception e) {
			e.printStackTrace();
			showMessage(Constants.MSG_ERRO, FacesMessage.SEVERITY_ERROR);
			return null;
		}
	}

	private void cancelarMatricula() {
		observacaoMatricula.setUsuario(getUsuarioLogado());
		if ( !entidade.getObservacoes().contains(observacaoMatricula) ) {
			entidade.getObservacoes().add(observacaoMatricula);
		}
		observacaoMatricula.setMatricula(entidade);
		atualizarStatusMensalidade(StatusPagamento.CANCELADO);
	}

	
	/**
	 * Atualiza as mensalidades com o status informado.
	 * @param statusPagamento
	 */
	private void atualizarStatusMensalidade( StatusPagamento statusPagamento) {
		for (Mensalidade mens : entidade.getMensalidades()) {
			if ( !StatusPagamento.PAGO.equals(mens.getStatusPagamento()) ) {
				// Se a mensalidade ainda não foi paga, então atualiza o status do pagto.
				mens.setStatusPagamento(statusPagamento);
			}
		}
	}

	private void gerarMensalidadesMatricula( boolean atualizarMatricula ) {
		int mesInicial = mesSelecionado.getId();
		int mesFinal = Constants.DOZE;
		Configuracao configuracao = (Configuracao) FacesUtils.getApplicationParam("configuracao");
		int diaVencimento = configuracao.getDiaVencimento();
		
		int qtMensalidades = mesFinal - (mesInicial -1);
		
		// calcula o valor da mensalidade dividindo o valor do curso pela quantidade de meses
		double valorMens = entidade.getValor() / qtMensalidades;
		
		/*
		 * Regra:
		 * Mensalidade = Valor_Curso / Qtde_Meses */
		List<Integer> mensalidadesPagas = new ArrayList<Integer>(); 
		if (atualizarMatricula) {
			// se for atualizar exclui as mensalidades não pagas e recria novas.
			List<Mensalidade> mensalidades = new ArrayList<Mensalidade>(entidade.getMensalidades());
			for (Mensalidade mens : mensalidades ) {
				if (StatusPagamento.PAGO.equals( mens.getStatusPagamento() ) ) {
					mensalidadesPagas.add(mens.getSequencial());
					continue;
				}
				entidade.getMensalidades().remove(mens);
			}
		}
		
		for (int i = mesInicial; i <= mesFinal; i++) {
			if (!mensalidadesPagas.contains(i)) {
				entidade.getMensalidades().add(createMensalidade(diaVencimento, i, entidade.getTurma().getAno(), valorMens));
			}
		}
	}

	private Mensalidade createMensalidade(int dia, int mes, int ano, double valorVencimento) {
		Mensalidade mens = new Mensalidade();
		mens.setDataVencimento(criarDataVencimento(mes, ano, dia));
		mens.setMatricula(entidade);
		mens.setSequencial(mes);
		mens.setStatusPagamento(StatusPagamento.PENDENTE);
		mens.setValorVencimento(valorVencimento);
		mens.setUsuario(getUsuarioLogado());
		return mens;
	}

	private Date criarDataVencimento(int mes, int ano, int dia) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		cal.set(ano, mes - 1, dia);
		return cal.getTime();
	}

	
	/* -------- Gets/Sets ------------------- */
	
	public List<Matricula> getAlunoMatriculas() {
		if (alunoMatricula == null ) {
			return null;
		}
		return alunoMatricula.getMatriculas();
	}
	
	
	public RelatorioUtil getRelatorioUtil() {
		return relatorioUtil;
	}

	public void setRelatorioUtil(RelatorioUtil relatorioUtil) {
		this.relatorioUtil = relatorioUtil;
	}

	public Aluno getAlunoMatricula() {
		return alunoMatricula;
	}

	public boolean isModalCadastro() {
		return modalCadastro;
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public MatriculaServico getServicoMatricula() {
		return servicoMatricula;
	}

	public void setServicoMatricula(MatriculaServico servicoMatricula) {
		this.servicoMatricula = servicoMatricula;
	}

	public TurmaServico getServicoTurma() {
		return servicoTurma;
	}

	public void setServicoTurma(TurmaServico servicoTurma) {
		this.servicoTurma = servicoTurma;
	}

	public List<SelectItem> getNaoSimList() {
		return naoSimList;
	}

	public List<SelectItem> getStatusMatriculaList() {
		return statusMatriculaList;
	}

	public Observacao getObservacaoMatricula() {
		return observacaoMatricula;
	}

	public void setObservacaoMatricula(Observacao observacaoMatricula) {
		this.observacaoMatricula = observacaoMatricula;
	}

	public List<SelectItem> getMesesList() {
		return mesesList;
	}

	public Meses getMesSelecionado() {
		return mesSelecionado;
	}

	public void setMesSelecionado(Meses mesSelecionado) {
		this.mesSelecionado = mesSelecionado;
	}

	public BoletimServico getBoletimServico() {
		return boletimServico;
	}

	public void setBoletimServico(BoletimServico boletimServico) {
		this.boletimServico = boletimServico;
	}

	public MensalidadeServico getServicoMensalidade() {
		return servicoMensalidade;
	}

	public void setServicoMensalidade(MensalidadeServico servicoMensalidade) {
		this.servicoMensalidade = servicoMensalidade;
	}

	public boolean isNovo() {
		return novo;
	}

	public void setNovo(boolean novo) {
		this.novo = novo;
	}

	public AlunoServico getServicoAluno() {
		return servicoAluno;
	}

	public void setServicoAluno(AlunoServico servicoAluno) {
		this.servicoAluno = servicoAluno;
	}

	public void setAlunoMatricula(Aluno alunoMatricula) {
		this.alunoMatricula = alunoMatricula;
	}

}