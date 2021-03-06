package br.fucapi.ads.modelo.controlador;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.alfresco.repo.webservice.administration.AdministrationFault;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.jm.conversor.pdf.ConversaoParaPDF;
import br.com.jm.conversor.pdf.office.ConversaoAPartirDeTextoOffice;
import br.fucapi.ads.modelo.dominio.Alerta;
import br.fucapi.ads.modelo.dominio.Arquivo;
import br.fucapi.ads.modelo.dominio.Categoria;
import br.fucapi.ads.modelo.dominio.NomenclaturaDocumento;
import br.fucapi.ads.modelo.dominio.PostoCopia;
import br.fucapi.ads.modelo.dominio.Protocolo;
import br.fucapi.ads.modelo.dominio.Setor;
import br.fucapi.ads.modelo.dominio.Unidade;
import br.fucapi.ads.modelo.dominio.VariaveisTarefa;
import br.fucapi.ads.modelo.dominio.VariaveisTreinamento;
import br.fucapi.ads.modelo.dominio.Variavel;
import br.fucapi.ads.modelo.dominio.VariavelPublicarDocumento;
import br.fucapi.ads.modelo.enumerated.StatusProcesso;
import br.fucapi.ads.modelo.regranegocio.TreinamentoRN;
import br.fucapi.ads.modelo.servico.AlertaServico;
import br.fucapi.ads.modelo.servico.CategoriaServico;
import br.fucapi.ads.modelo.servico.NomenclaturaDocumentoServico;
import br.fucapi.ads.modelo.servico.PostoCopiaServico;
import br.fucapi.ads.modelo.servico.ProtocoloServico;
import br.fucapi.ads.modelo.servico.SetorServico;
import br.fucapi.ads.modelo.servico.UnidadeServico;
import br.fucapi.ads.modelo.servico.VariaveisTarefaServico;
import br.fucapi.ads.modelo.utils.GeralUtils;
import br.fucapi.ads.modelo.utils.Watermark;
import br.fucapi.bpms.activiti.dominio.ProcessoDefinicao;
import br.fucapi.bpms.activiti.dominio.ProcessoInstancia;
import br.fucapi.bpms.activiti.dominio.TarefaInstancia;
import br.fucapi.bpms.activiti.enumerated.NomeVariavel;
import br.fucapi.bpms.activiti.servico.ActivitiServico;
import br.fucapi.bpms.activiti.util.JsonUtil;
import br.fucapi.bpms.alfresco.dominio.Usuario;
import br.fucapi.bpms.alfresco.dominio.UsuarioGrupo;
import br.fucapi.bpms.alfresco.servico.AlfrescoServico;

import com.lowagie.text.DocumentException;
import com.sun.xml.ws.api.PropertySet.Property;

import flexjson.JSONDeserializer;

@ManagedBean
@SessionScoped
public class PublicarDocumentoControlador implements Serializable {

	private static final long serialVersionUID = 13244234324234332L;

	private VariavelPublicarDocumento variaveis;

	private VariavelPublicarDocumento variaveisPesquisa;

	private ProcessoInstancia processoInstancia;
	private ProcessoDefinicao processoDefinicao;

	private List<ProcessoInstancia> lista;

	private List<ProcessoInstancia> listaHistorico;

	private List<ProcessoDefinicao> listaProcessosDefinicao;
	private List<TarefaInstancia> tarefaInstancias;

	private List<TarefaInstancia> tarefas;

	private TarefaInstancia tarefa;

	private Alerta alerta;

	private VariaveisTarefa variaveisTarefa;

	private String descricao;
	private Protocolo protocolo;

	private Usuario usuarioLogado;

	private List<UsuarioGrupo> gruposAlfresco;
	private List<Usuario> usuariosGrupoRevisores;

	private DualListModel<UsuarioGrupo> gruposDualListModel;

	private List<Usuario> usuarios;

	private String imagem;

	private VariaveisTreinamento variaveisTreinamento;

	private Unidade unidade;

	private Categoria categoria;

	private Setor setor;

	private PostoCopia postoCopia;

	private ProcessoInstancia processoStart;

	private boolean habilitar = false;

	@ManagedProperty(value = "#{activitiServicoImpl}")
	private ActivitiServico activitiServico;

	@ManagedProperty(value = "#{alfrescoServicoImpl}")
	private AlfrescoServico alfrescoServico;

	@Property(value = "#{bpmswebproperties}")
	private Properties bpmswebproperties;

	@ManagedProperty(value = "#{treinamentoRN}")
	private TreinamentoRN treinamentoRN;

	@ManagedProperty(value = "#{protocoloServicoImpl}")
	private ProtocoloServico protocoloServico;

	@ManagedProperty(value = "#{variaveisTarefaServicoImpl}")
	private VariaveisTarefaServico variaveisTarefaServico;

	@ManagedProperty(value = "#{unidadeServicoImpl}")
	private UnidadeServico unidadeServico;

	@ManagedProperty(value = "#{postoCopiaServicoImpl}")
	private PostoCopiaServico postoCopiaServico;

	@ManagedProperty(value = "#{setorServicoImpl}")
	private SetorServico setorServico;

	@ManagedProperty(value = "#{nomenclaturaDocumentoServicoImpl}")
	private NomenclaturaDocumentoServico nomenclaturaDocumentoServico;

	@ManagedProperty(value = "#{categoriaServicoImpl}")
	private CategoriaServico categoriaServico;

	@ManagedProperty(value = "#{alertaServicoImpl}")
	private AlertaServico alertaServico;

	private String TELA_PESQUISA = "/paginas/solicitacao/publicardocumento/pesquisa.xhtml?faces-redirect=true";

	private String TELA_CADASTRO = "/paginas/solicitacao/publicardocumento/cadastro.xhtml?faces-redirect=true";

	private String TELA_DETALHE = "/paginas/solicitacao/publicardocumento/detalhe.xhtml?faces-redirect=true";

	private String TELA_REVISAO = "/paginas/solicitacao/publicardocumento/revisar.xhtml?faces-redirect=true";

	private String TELA_DETALHE_TAREFA = "/paginas/solicitacao/publicardocumento/detalhetarefa.xhtml?faces-redirect=true";

	private final String PESQUISATAREFAPENDENTE = "/paginas/tarefa/pesquisatarefapendente.xhtml?faces-redirect=true";

	// PickList

	private DualListModel<Usuario> aprovadores;
	private List<Usuario> aprovadoresTarget;
	private List<Usuario> aprovadoresSource;

	private DualListModel<Usuario> concessoes;
	private List<Usuario> concessoesTarget;
	private List<Usuario> concessoesSource;

	private DualListModel<Usuario> elaboradores;
	private List<Usuario> elaboradoresTarget;
	private List<Usuario> elaboradoresSource;

	private DualListModel<PostoCopia> postosCopia;
	private List<PostoCopia> postosCopiaTarget;
	private List<PostoCopia> postosCopiaSource;

	private StatusProcesso status;

	private StreamedContent file;

	private UploadedFile uploadFile;

	private boolean renderCancelar;

	private boolean renderDownload;

	public UploadedFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(UploadedFile uploadFile) {
		this.uploadFile = uploadFile;
	}

	// Att Wizard
	private boolean skip;

	public boolean isSkip() {
		return skip;
	}

	public void downloadArquivoDoc() {

		this.variaveis = (VariavelPublicarDocumento) this.processoInstancia
				.getVariaveis();

		String nomeArquivo = null;
		String uuidArquivo = null;

		nomeArquivo = this.variaveis.getArquivoDoc().getNomeArquivo();
		uuidArquivo = this.variaveis.getArquivoDoc().getUuid();

		InputStream temp = alfrescoServico.baixarArquivo(nomeArquivo,
				uuidArquivo);

		this.file = new DefaultStreamedContent(temp, null, nomeArquivo);

	}

	public void downloadArquivoControlado() {

		this.variaveis = (VariavelPublicarDocumento) this.processoInstancia
				.getVariaveis();

		String nomeArquivo = null;
		String uuidArquivo = null;

		nomeArquivo = this.variaveis.getArquivoControlado().getNomeArquivo();
		uuidArquivo = this.variaveis.getArquivoControlado().getUuid();

		InputStream temp = alfrescoServico.baixarArquivo(nomeArquivo,
				uuidArquivo);

		this.file = new DefaultStreamedContent(temp, null, nomeArquivo);

	}

	public void downloadArquivoNaoControlado() {

		this.variaveis = (VariavelPublicarDocumento) this.processoInstancia
				.getVariaveis();

		String nomeArquivo = null;
		String uuidArquivo = null;

		nomeArquivo = this.variaveis.getArquivoNaoControlado().getNomeArquivo();
		uuidArquivo = this.variaveis.getArquivoNaoControlado().getUuid();

		InputStream temp = alfrescoServico.baixarArquivo(nomeArquivo,
				uuidArquivo);

		this.file = new DefaultStreamedContent(temp, null, nomeArquivo);

	}

	public void handleFileUpload(FileUploadEvent event) {
		FacesMessage message = new FacesMessage("Arquivo ["
				+ event.getFile().getFileName() + "] inserido com Sucesso!");

		this.uploadFile = event.getFile();
		this.habilitar = true;
		FacesContext.getCurrentInstance().addMessage("messages", message);
	}

	public String onFlowProcess(FlowEvent event) {
		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		} else {
			return event.getNewStep();
		}
	}

	public void converterDocToPDF() throws DocumentException {
		List<Arquivo> listaArquivos = new ArrayList<Arquivo>();
		String pathMarcaDagua = FacesContext.getCurrentInstance()
				.getExternalContext().getRealPath("/")
				+ "\\";

		try {

			File fileTempOriginal = GeralUtils
					.converterFileUploadToFile(this.uploadFile);

			this.variaveis.setArquivoDoc(new Arquivo());
			this.variaveis.getArquivoDoc().setNomeArquivo(
					fileTempOriginal.getName());
			this.variaveis.getArquivoDoc().setFile(fileTempOriginal);

			listaArquivos.add(this.variaveis.getArquivoDoc());

			String extensao = FilenameUtils.getExtension(fileTempOriginal
					.getName());
			if ("doc".equals(extensao) || "docx".equals(extensao)) {
				try {
					// Bloco para conversao de arquivo DOC em PDF
					ConversaoParaPDF algoritmo = new ConversaoAPartirDeTextoOffice();

					Path path = Paths.get(fileTempOriginal.getAbsolutePath());

					byte[] data = Files.readAllBytes(path);
					byte[] pdf = algoritmo.converterDocumento(data);

					this.variaveis.getArquivoControlado().setNomeArquivo(
							"copiacontrolado.pdf");
					this.variaveis.getArquivoControlado().setFile(
							Watermark.inserirTarja(pdf, "copiacontrolado",
									pathMarcaDagua,
									this.variaveis.pegarRodape()));
					listaArquivos.add(this.variaveis.getArquivoControlado());

					this.variaveis.getArquivoNaoControlado().setNomeArquivo(
							"copianaocontrolado.pdf");
					this.variaveis.getArquivoNaoControlado().setFile(
							Watermark.inserirTarja(pdf, "copianaocontrolado",
									pathMarcaDagua,
									this.variaveis.pegarRodape()));
					listaArquivos.add(this.variaveis.getArquivoNaoControlado());

					this.variaveis.getArquivoObsoleto().setNomeArquivo(
							"arquivoobsoleto.pdf");
					this.variaveis.getArquivoObsoleto().setFile(
							Watermark.inserirTarja(pdf,
									"copia-arquivo-obsoleto", pathMarcaDagua,
									this.variaveis.pegarRodape()));
					listaArquivos.add(this.variaveis.getArquivoObsoleto());

					this.variaveis.getArquivoCancelado().setNomeArquivo(
							"copia-arquivo-cancelado.pdf");
					this.variaveis.getArquivoCancelado().setFile(
							Watermark.inserirTarja(pdf,
									"copia-arquivo-cancelado", pathMarcaDagua,
									this.variaveis.pegarRodape()));
					listaArquivos.add(this.variaveis.getArquivoCancelado());

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// Invoca o metodo para salvar os documentos no Alfresco
		if (null != this.variaveis.getArquivoDoc().getFile()) {
			this.saveArquivo();
		}
	}

	// Metodo responsavel por salvar no repositorio Alfresco o Documento
	public void saveArquivo() {

		// Solucao temporaria
		if (this.bpmswebproperties == null) {
			this.bpmswebproperties = new Properties();
			try {
				this.bpmswebproperties.load(getClass().getClassLoader()
						.getResourceAsStream("web.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String nomePasta = "" + protocolo.getAno() + protocolo.getSequencial();

		// for (Arquivo arquivo : listaArquivos) {

		if (this.variaveis.getArquivoDoc().getFile() != null) {
			try {
				String json;

				// inseri o documento original
				json = alfrescoServico
						.anexarArquivo(bpmswebproperties
								.getProperty("uuid.parent.publicacao"),
								nomePasta, "", this.descricao,
								this.usuarioLogado.getTicket(), this.variaveis
										.getArquivoDoc().getFile());

				this.deserializacaoReferenciaUUID(json,
						this.variaveis.getArquivoDoc());

				// inseri o documento controlado
				json = alfrescoServico
						.anexarArquivo(bpmswebproperties
								.getProperty("uuid.parent.publicacao"),
								nomePasta, "", this.descricao,
								this.usuarioLogado.getTicket(), this.variaveis
										.getArquivoControlado().getFile());

				this.deserializacaoReferenciaUUID(json,
						this.variaveis.getArquivoControlado());

				// inseri arquivo nao controlado
				json = alfrescoServico
						.anexarArquivo(bpmswebproperties
								.getProperty("uuid.parent.publicacao"),
								nomePasta, "", this.descricao,
								this.usuarioLogado.getTicket(), this.variaveis
										.getArquivoNaoControlado().getFile());

				this.deserializacaoReferenciaUUID(json,
						this.variaveis.getArquivoNaoControlado());

				// inseri arquivo obsoleto
				json = alfrescoServico
						.anexarArquivo(bpmswebproperties
								.getProperty("uuid.parent.publicacao"),
								nomePasta, "", this.descricao,
								this.usuarioLogado.getTicket(), this.variaveis
										.getArquivoObsoleto().getFile());

				this.deserializacaoReferenciaUUID(json,
						this.variaveis.getArquivoObsoleto());

				// inseri arquivo cancelado
				json = alfrescoServico
						.anexarArquivo(bpmswebproperties
								.getProperty("uuid.parent.publicacao"),
								nomePasta, "", this.descricao,
								this.usuarioLogado.getTicket(), this.variaveis
										.getArquivoCancelado().getFile());

				this.deserializacaoReferenciaUUID(json,
						this.variaveis.getArquivoCancelado());

			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			FacesMessage message = new FacesMessage("warn",
					" Ocorreu um erro ao inserir o documento");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		// }
	}

	private void deserializacaoReferenciaUUID(String json, Arquivo arquivo) {
		Map<String, Map<String, Object>> deserialized = new JSONDeserializer<Map<String, Map<String, Object>>>()
				.deserialize(json);

		arquivo.setUuid(deserialized.get("nodeRef") + "");
		this.variaveis.setUuidPasta(deserialized.get("uuidPasta") + "");
		arquivo.setFile(null);
	}

	public void atualizarComboSetores() {
		if (this.variaveis.getUnidade() != null) {
			Setor setorTemp = new Setor();
			setorTemp.setUnidade(this.variaveis.getUnidade());
			this.variaveis.setSetores(this.setorServico.pesquisar(setorTemp));
		} else {
			this.variaveis.setSetores(new ArrayList<Setor>());
		}
	}

	public String inicioNovaSolicitacao() {

		this.variaveis = new VariavelPublicarDocumento();

		this.processoDefinicao = new ProcessoDefinicao();
		this.processoDefinicao.setKey(variaveis.getTipoSolicitacao());

		this.gruposDualListModel = new DualListModel<UsuarioGrupo>();
		this.usuarios = alfrescoServico.getUsuarios();

		this.variaveis.setProprietarios(usuarios);

		this.variaveis.setUnidades(unidadeServico.listAll());
		this.variaveis.setCategorias(categoriaServico.listAll());

		// PickList Aprovadores
		this.aprovadoresSource = new ArrayList<Usuario>();
		this.aprovadoresSource.addAll(this.usuarios);
		this.aprovadoresTarget = new ArrayList<Usuario>();
		this.aprovadores = new DualListModel<Usuario>(this.aprovadoresSource,
				this.aprovadoresTarget);

		// PickList Concessoes
		this.concessoesSource = new ArrayList<Usuario>();
		this.concessoesSource.addAll(this.usuarios);
		this.concessoesTarget = new ArrayList<Usuario>();
		this.concessoes = new DualListModel<Usuario>(this.concessoesSource,
				this.concessoesTarget);

		// PickList Elaboradores
		this.elaboradoresSource = new ArrayList<Usuario>();
		this.elaboradoresSource.addAll(this.usuarios);
		this.elaboradoresTarget = new ArrayList<Usuario>();
		this.elaboradores = new DualListModel<Usuario>(this.elaboradoresSource,
				this.elaboradoresTarget);

		// PickList PostosCopia
		this.postosCopiaSource = new ArrayList<PostoCopia>();
		this.postosCopiaSource.addAll(postoCopiaServico.listAll());
		this.postosCopiaTarget = new ArrayList<PostoCopia>();
		this.postosCopia = new DualListModel<PostoCopia>(
				this.postosCopiaSource, this.postosCopiaTarget);

		// TODO foi incluido o redirect porque as paginas não estavam
		// carregando os componentes da paginas, devido a um bug do primefaces

		// paginaCentralControladorBean.setPaginaCentral(this.TELA_CADASTRO);

		return TELA_CADASTRO;
	}

	@PostConstruct
	public void initUsuario() {
		this.usuarioLogado = (Usuario) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
	}

	public String init() {
		initUsuario();

		this.variaveisPesquisa = new VariavelPublicarDocumento();
		this.variaveisPesquisa
				.setPostosCopiaObject(postoCopiaServico.listAll());
		this.variaveisPesquisa.setUnidades(unidadeServico.listAll());
		this.variaveisPesquisa.setCategorias(categoriaServico.listAll());
		this.variaveisPesquisa.setSetores(setorServico.listAll());

		return TELA_PESQUISA = "/paginas/solicitacao/publicardocumento/pesquisa.xhtml?faces-redirect=true";
	}

	public String validarFormulario() throws DocumentException {
		if (!"".equals(this.uploadFile.getFileName())) {
			return this.salvarNovaSolicitacao();
		} else {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"O arquivo deve ser selecionado!",
					"O arquivo deve ser selecionado!");
			FacesContext.getCurrentInstance().addMessage("messages", message);
			return "";
		}
	}

	/**
	 * Metodo responsavel por salvar uma nova solicitacao de treinamento no
	 * activiti
	 * 
	 * @throws DocumentException
	 */
	public String salvarNovaSolicitacao() throws DocumentException {

		this.protocolo = protocoloServico.gerarProtocolo();
		System.out.println(this.protocolo.toString());

		/*
		 * Trata a lista de aprovadores (login e email) e concessoes (login e
		 * email) que devem ser enviadas ao Activiti
		 */
		this.variaveis.tratarAtributos(this.aprovadores.getTarget(),
				this.concessoes.getTarget(), this.elaboradores.getTarget(),
				this.postosCopia.getTarget());

		// Bloco para setar Nomenclatura
		NomenclaturaDocumento nomenclatura = new NomenclaturaDocumento();
		nomenclatura.setUnidade(this.variaveis.getUnidade());
		nomenclatura.setCategoria(this.variaveis.getCategoria());
		nomenclatura.setSetor(this.variaveis.getSetor());
		nomenclatura = this.nomenclaturaDocumentoServico
				.pegarSequencial(nomenclatura);

		this.variaveis.setCodigo(nomenclatura.toString());

		// Chamada para converter o arquivo .doc e salvar os arquivos no
		// Alfresco
		this.converterDocToPDF();

		this.variaveis.setSequencial(this.protocolo.getSequencial());
		this.variaveis.setAno(this.protocolo.getAno());
		this.variaveis.setSolicitante(this.usuarioLogado.getUserName());

		this.activitiServico.iniciarInstanciaProcessoPorParametrosByKey(
				variaveis.getPUBLICAR_DOCUMENTO(), this.protocolo.toString(),
				variaveis.converterVariaveis());

		RequestContext request = RequestContext.getCurrentInstance();
		request.execute("sucessoDialog.show()");

		this.variaveis = new VariavelPublicarDocumento();

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
				"Documento incluído no fluxo de processo!");
		FacesContext.getCurrentInstance().addMessage("msg", message);

		// Atualiza a lista de processos.
		this.variaveisPesquisa.setStatusProcesso("PENDENTE");
		this.pesquisar();

		return this.telaPesquisa();
	}

	public String cancelar() {

		this.activitiServico.atualizarVariavelProcesso(
				this.processoInstancia.getId(), NomeVariavel.STATUS_PROCESSO,
				StatusProcesso.CANCELADO.getStatus());

		Map<String, Object> variaveis = new HashMap<String, Object>();
		variaveis.put("justificativaStatus", ((Variavel) this.processoInstancia
				.getVariaveis()).getJustificativaStatus());
		variaveis.put("statusProcesso", StatusProcesso.CANCELADO);

		try {
			String json = JsonUtil.converterVariaveisToJson(variaveis);
			this.activitiServico.atualizarVariaveis(
					this.processoInstancia.getId(), json);

		} catch (Exception e) {
			// ocorreu um erro ao cancelar o processo

		}

		/*
		 * this.activitiServico.cancelarProcessoInstaciado(processoInstancia
		 * .getId());
		 */

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
				"Solicitação cancelada com sucesso!");
		FacesContext.getCurrentInstance().addMessage("msg", message);

		// Atualiza a lista de processos
		this.pesquisar();

		return this.telaPesquisa();
	}

	public String carregarTarefas() throws ParseException {

		this.tarefaInstancias = activitiServico
				.getHistoricoTarefasPorVariaveis(null, null, null, null,
						this.processoInstancia.getId());

		if (!"PENDENTE"
				.equals(((VariavelPublicarDocumento) this.processoInstancia
						.getVariaveis()).getStatusProcesso()))
			this.alerta = alertaServico
					.pesquisarProcessInstanceId(this.processoInstancia.getId());
		else
			this.alerta = null;

		return this.TELA_DETALHE;

	}

	public String detalhe(ProcessoInstancia entity) throws ParseException {
		this.processoInstancia = entity;
		return carregarTarefas();

	}

	public String detalhe(TarefaInstancia tarefaInstancia)
			throws ParseException {
		this.TELA_PESQUISA = this.PESQUISATAREFAPENDENTE;
		pesquisarProcessoInstancia(tarefaInstancia.getProcessInstanceId());

		return detalhe(this.processoInstancia);
	}

	private void pesquisarProcessoInstancia(String processInstanceId) {
		this.processoInstancia = activitiServico
				.getProcessosInstanciaId(processInstanceId);

		this.variaveis = new VariavelPublicarDocumento();
		this.variaveis.converterListaVariaveis(this.processoInstancia
				.getVariables());

		this.processoInstancia.setVariaveis(variaveis);
	}

	public void preRenderView() {

		initUsuario();

		if (this.usuarioLogado.getCapabilities().isAdmin()) {
			this.renderDownload = true;
			if ((!StatusProcesso.CANCELADO
					.equals(((Variavel) this.processoInstancia.getVariaveis())
							.getStatusProcesso()))) {
				this.renderCancelar = true;
			} else {
				this.renderCancelar = false;
			}
		} else {
			this.renderCancelar = false;
			this.renderDownload = false;
		}
	}

	public boolean isHabilitarBotaoCancelar() {
		if (this.usuarioLogado.getCapabilities().isAdmin()) {
			return true;
		}

		return false;
	}

	public String revisar(ProcessoInstancia entity) {

		this.variaveis = (VariavelPublicarDocumento) entity.getVariaveis();
		// seta o protocolo anterior para realizar futuras pesquisas
		this.variaveis.setVersaoRevisao(this.incrementarVersao(this.variaveis
				.getProtocoloOrigem()));
		// seta o id do alerta da solicitação anterios, para mudar o status do
		// alerta para obsoleto.
		this.variaveis.setIdAlerta(this.alerta.getId());

		return this.TELA_REVISAO;
	}

	public String revisar(Alerta entity) {

		this.alerta = entity;
		List<ProcessoInstancia> listaResultado = null;
		this.lista = new ArrayList<ProcessoInstancia>();

		Map<String, Object> var = new HashMap<String, Object>();
		var.put("protocolo", entity.getProtocolo());

		listaResultado = activitiServico
				.getHistoricoProcessosFiltroVariaveis(var);

		for (ProcessoInstancia pInstancia : listaResultado) {
			this.variaveis = new VariavelPublicarDocumento();
			this.variaveis.converterListaVariaveis(pInstancia.getVariables());

			pInstancia.setVariaveis(variaveis);
			this.lista.add(pInstancia);
		}

		return revisar(this.lista.get(0));

	}

	public String telaPesquisa() {
		this.processoInstancia = null;

		this.tarefaInstancias = null;
		return this.TELA_PESQUISA;
	}

	public void handleTransfer(TransferEvent event) {
		StringBuilder builder = new StringBuilder();
		for (Object item : event.getItems()) {
			builder.append(((Usuario) item).getFirstName()).append("<br />");
			this.aprovadores.getTarget().add((Usuario) item);

		}

		System.out.println(builder.toString());
		FacesMessage msg = new FacesMessage();
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		msg.setSummary("Tache Transferred");
		msg.setDetail(builder.toString());

		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public void getUsuariosPorGrupo() {
		try {
			this.usuariosGrupoRevisores = alfrescoServico
					.getUsuariosPorGrupo(/*
										 * pmswebproperties
										 * .getProperty("bpms.fucapi.grupo")
										 */"");
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
				if (!grupo.getDisplayName().contains("site_swsdp"))
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

	public String pesquisar() {

		List<ProcessoInstancia> listaResultado = null;
		this.lista = new ArrayList<ProcessoInstancia>();

		Map<String, Object> var = this.filtroVariaveis();
		listaResultado = activitiServico
				.getHistoricoProcessosFiltroVariaveis(var);

		for (ProcessoInstancia pInstancia : listaResultado) {
			this.variaveis = new VariavelPublicarDocumento();
			this.variaveis.converterListaVariaveis(pInstancia.getVariables());

			pInstancia.setVariaveis(variaveis);
			if (filtrarProcessoInstancia())
				this.lista.add(pInstancia);
		}

		return this.TELA_PESQUISA = "/paginas/solicitacao/publicardocumento/pesquisa.xhtml?faces-redirect=true";

	}

	private boolean filtrarProcessoInstancia() {
		boolean retorno = true;

		if (this.postoCopia != null) {
			for (PostoCopia posto : this.variaveis.getPostosCopiaObjeto()) {
				if (posto.getId().equals(this.postoCopia.getId())) {
					retorno = true;
					break;
				} else
					retorno = false;
			}
			if (!retorno)
				return retorno;
		}

		if (this.unidade != null) {
			if (!this.unidade.getId().equals(
					this.variaveis.getUnidade().getId()))
				return false;
		}

		if (this.setor != null) {
			if (!this.setor.getId().equals(this.variaveis.getSetor().getId()))
				return false;
		}

		if (this.categoria != null) {
			if (!this.categoria.getId().equals(
					this.variaveis.getCategoria().getId()))
				return false;

		}

		return retorno;
	}

	public void pesquisarHistoricoDocumento(String protocoloOrigem) {
		List<ProcessoInstancia> listaResultado = null;
		this.listaHistorico = new ArrayList<ProcessoInstancia>();

		Map<String, Object> var = new HashMap<String, Object>();
		var.put("tipoSolicitacao", this.variaveisPesquisa.getTipoSolicitacao());
		var.put("protocoloOrigem", protocoloOrigem);

		listaResultado = activitiServico
				.getHistoricoProcessosFiltroVariaveis(var);

		for (ProcessoInstancia pInstancia : listaResultado) {
			this.variaveis = new VariavelPublicarDocumento();
			this.variaveis.converterListaVariaveis(pInstancia.getVariables());

			pInstancia.setVariaveis(variaveis);
			this.listaHistorico.add(pInstancia);
		}

	}

	public int incrementarVersao(String protocoloOrigem) {
		Map<String, Object> var = new HashMap<String, Object>();
		var.put("tipoSolicitacao", this.variaveisPesquisa.getTipoSolicitacao());
		var.put("protocoloOrigem", protocoloOrigem);

		return activitiServico.incrementarVersaoDocumento(var);

	}

	private Map<String, Object> filtroVariaveis() {

		Map<String, Object> var = new HashMap<String, Object>();

		if (null != this.variaveisPesquisa.getStatusProcesso()
				&& this.variaveisPesquisa.getStatusProcesso() != "")
			var.put("statusProcesso",
					this.variaveisPesquisa.getStatusProcesso());

		if (null != this.variaveisPesquisa.getProtocoloOrigem()
				&& this.variaveisPesquisa.getProtocoloOrigem() != "")
			var.put("protocoloOrigem",
					this.variaveisPesquisa.getProtocoloOrigem());

		if (null != this.variaveisPesquisa.getProtocolo()
				&& this.variaveisPesquisa.getProtocolo().length() > 4)
			var.put("protocolo", this.variaveisPesquisa.getProtocolo());

		if (null != this.variaveisPesquisa.getSolicitante()
				&& this.variaveisPesquisa.getSolicitante() != "")
			var.put("solicitante", this.variaveisPesquisa.getSolicitante());

		var.put("tipoSolicitacao", this.variaveisPesquisa.getTipoSolicitacao());

		return var;
	}

	public String detalheTarefa(TarefaInstancia tarefa) {
		this.tarefa = tarefa;
		// TODO
		// this.variaveisTarefa =
		// variaveisTarefaServico.findById(Long.valueOf(tarefa.getId()));
		this.variaveisTarefa = null;

		return this.TELA_DETALHE_TAREFA;
	}

	public void abrirImagemProcesso(ProcessoInstancia entity) {
		this.variaveis = (VariavelPublicarDocumento) entity.getVariaveis();
		processoInstancia = entity;
		setImagem(null);
		setImagem(activitiServico.getProcessoDiagrama(entity.getId()));
	}

	public String telaDetalhe() {
		return this.TELA_DETALHE;

	}

	public List<ProcessoInstancia> getLista() {
		return lista;
	}

	public void setLista(List<ProcessoInstancia> lista) {
		this.lista = lista;
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

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
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

	public boolean isHabilitar() {
		return habilitar;
	}

	public AlfrescoServico getAlfrescoServico() {
		return alfrescoServico;
	}

	public void setAlfrescoServico(AlfrescoServico alfrescoServico) {
		this.alfrescoServico = alfrescoServico;
	}

	public Properties getBpmswebproperties() {
		return bpmswebproperties;
	}

	public void setBpmswebproperties(Properties bpmswebproperties) {
		this.bpmswebproperties = bpmswebproperties;
	}

	public VariaveisTreinamento getVariaveisTreinamento() {
		return variaveisTreinamento;
	}

	public void setVariaveisTreinamento(
			VariaveisTreinamento variaveisTreinamento) {
		this.variaveisTreinamento = variaveisTreinamento;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public TreinamentoRN getTreinamentoRN() {
		return treinamentoRN;
	}

	public void setTreinamentoRN(TreinamentoRN treinamentoRN) {
		this.treinamentoRN = treinamentoRN;
	}

	public ProtocoloServico getProtocoloServico() {
		return protocoloServico;
	}

	public void setProtocoloServico(ProtocoloServico protocoloServico) {
		this.protocoloServico = protocoloServico;
	}

	public List<TarefaInstancia> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<TarefaInstancia> tarefas) {
		this.tarefas = tarefas;
	}

	public void onTransfer(TransferEvent event) {
		StringBuilder builder = new StringBuilder();
		for (Object item : event.getItems()) {
			this.aprovadores.getTarget().add((Usuario) item);
		}

		FacesMessage msg = new FacesMessage();
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		msg.setSummary("Items Transferred");
		msg.setDetail(builder.toString());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public TarefaInstancia getTarefa() {
		return tarefa;
	}

	public void setTarefa(TarefaInstancia tarefa) {
		this.tarefa = tarefa;
	}

	public VariaveisTarefa getVariaveisTarefa() {
		return variaveisTarefa;
	}

	public void setVariaveisTarefa(VariaveisTarefa variaveisTarefa) {
		this.variaveisTarefa = variaveisTarefa;
	}

	public VariaveisTarefaServico getVariaveisTarefaServico() {
		return variaveisTarefaServico;
	}

	public void setVariaveisTarefaServico(
			VariaveisTarefaServico variaveisTarefaServico) {
		this.variaveisTarefaServico = variaveisTarefaServico;
	}

	public VariavelPublicarDocumento getVariaveis() {
		return variaveis;
	}

	public void setVariaveis(VariavelPublicarDocumento variaveis) {
		this.variaveis = variaveis;
	}

	public UnidadeServico getUnidadeServico() {
		return unidadeServico;
	}

	public void setUnidadeServico(UnidadeServico unidadeServico) {
		this.unidadeServico = unidadeServico;
	}

	public PostoCopiaServico getPostoCopiaServico() {
		return postoCopiaServico;
	}

	public void setPostoCopiaServico(PostoCopiaServico postoCopiaServico) {
		this.postoCopiaServico = postoCopiaServico;
	}

	public SetorServico getSetorServico() {
		return setorServico;
	}

	public void setSetorServico(SetorServico setorServico) {
		this.setorServico = setorServico;
	}

	public CategoriaServico getCategoriaServico() {
		return categoriaServico;
	}

	public void setCategoriaServico(CategoriaServico categoriaServico) {
		this.categoriaServico = categoriaServico;
	}

	public DualListModel<Usuario> getAprovadores() {
		return aprovadores;
	}

	public void setAprovadores(DualListModel<Usuario> aprovadores) {
		this.aprovadores = aprovadores;
	}

	public List<Usuario> getAprovadoresTarget() {
		return aprovadoresTarget;
	}

	public void setAprovadoresTarget(List<Usuario> aprovadoresTarget) {
		this.aprovadoresTarget = aprovadoresTarget;
	}

	public List<Usuario> getAprovadoresSource() {
		return aprovadoresSource;
	}

	public void setAprovadoresSource(List<Usuario> aprovadoresSource) {
		this.aprovadoresSource = aprovadoresSource;
	}

	public DualListModel<Usuario> getConcessoes() {
		return concessoes;
	}

	public void setConcessoes(DualListModel<Usuario> concessoes) {
		this.concessoes = concessoes;
	}

	public List<Usuario> getConcessoesTarget() {
		return concessoesTarget;
	}

	public void setConcessoesTarget(List<Usuario> concessoesTarget) {
		this.concessoesTarget = concessoesTarget;
	}

	public List<Usuario> getConcessoesSource() {
		return concessoesSource;
	}

	public void setConcessoesSource(List<Usuario> concessoesSource) {
		this.concessoesSource = concessoesSource;
	}

	public DualListModel<Usuario> getElaboradores() {
		return elaboradores;
	}

	public void setElaboradores(DualListModel<Usuario> elaboradores) {
		this.elaboradores = elaboradores;
	}

	public List<Usuario> getElaboradoresTarget() {
		return elaboradoresTarget;
	}

	public void setElaboradoresTarget(List<Usuario> elaboradoresTarget) {
		this.elaboradoresTarget = elaboradoresTarget;
	}

	public List<Usuario> getElaboradoresSource() {
		return elaboradoresSource;
	}

	public void setElaboradoresSource(List<Usuario> elaboradoresSource) {
		this.elaboradoresSource = elaboradoresSource;
	}

	public DualListModel<PostoCopia> getPostosCopia() {
		return postosCopia;
	}

	public void setPostosCopia(DualListModel<PostoCopia> postosCopia) {
		this.postosCopia = postosCopia;
	}

	public List<PostoCopia> getPostosCopiaTarget() {
		return postosCopiaTarget;
	}

	public void setPostosCopiaTarget(List<PostoCopia> postosCopiaTarget) {
		this.postosCopiaTarget = postosCopiaTarget;
	}

	public List<PostoCopia> getPostosCopiaSource() {
		return postosCopiaSource;
	}

	public void setPostosCopiaSource(List<PostoCopia> postosCopiaSource) {
		this.postosCopiaSource = postosCopiaSource;
	}

	public StreamedContent getFile() {
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public VariavelPublicarDocumento getVariaveisPesquisa() {
		return variaveisPesquisa;
	}

	public void setVariaveisPesquisa(VariavelPublicarDocumento variaveisPesquisa) {
		this.variaveisPesquisa = variaveisPesquisa;
	}

	public List<ProcessoInstancia> getListaHistorico() {
		return listaHistorico;
	}

	public void setListaHistorico(List<ProcessoInstancia> listaHistorico) {
		this.listaHistorico = listaHistorico;
	}

	public NomenclaturaDocumentoServico getNomenclaturaDocumentoServico() {
		return nomenclaturaDocumentoServico;
	}

	public void setNomenclaturaDocumentoServico(
			NomenclaturaDocumentoServico nomenclaturaDocumentoServico) {
		this.nomenclaturaDocumentoServico = nomenclaturaDocumentoServico;
	}

	public boolean isRenderCancelar() {
		return renderCancelar;
	}

	public void setRenderCancelar(boolean renderCancelar) {
		this.renderCancelar = renderCancelar;
	}

	public boolean isRenderDownload() {
		return renderDownload;
	}

	public void setRenderDownload(boolean renderDownload) {
		this.renderDownload = renderDownload;
	}

	public AlertaServico getAlertaServico() {
		return alertaServico;
	}

	public void setAlertaServico(AlertaServico alertaServico) {
		this.alertaServico = alertaServico;
	}

	public Alerta getAlerta() {
		return alerta;
	}

	public void setAlerta(Alerta alerta) {
		this.alerta = alerta;
	}

	public StatusProcesso[] getStatusProcesso() {
		return StatusProcesso.values();
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public PostoCopia getPostoCopia() {
		return postoCopia;
	}

	public void setPostoCopia(PostoCopia postoCopia) {
		this.postoCopia = postoCopia;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

}