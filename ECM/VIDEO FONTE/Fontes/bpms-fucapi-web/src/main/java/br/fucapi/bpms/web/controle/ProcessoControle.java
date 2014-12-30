package br.fucapi.bpms.web.controle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.faces.application.FacesMessage;
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
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;

import br.fucapi.bpms.activiti.dominio.ProcessoDefinicao;
import br.fucapi.bpms.activiti.dominio.ProcessoInstancia;
import br.fucapi.bpms.activiti.dominio.TarefaInstancia;
import br.fucapi.bpms.activiti.servico.ActivitiServico;
import br.fucapi.bpms.alfresco.dominio.Usuario;
import br.fucapi.bpms.alfresco.dominio.UsuarioGrupo;
import br.fucapi.bpms.alfresco.servico.AlfrescoServico;
import br.fucapi.bpms.web.dominio.Arquivo;
import br.fucapi.bpms.web.dominio.Desenho;
import br.fucapi.bpms.web.dominio.FamiliaProduto;
import br.fucapi.bpms.web.dominio.MetadadoDesenho;
import br.fucapi.bpms.web.dominio.Origem;
import br.fucapi.bpms.web.dominio.Pendencia;
import br.fucapi.bpms.web.dominio.Protocolo;
import br.fucapi.bpms.web.dominio.Situacao;
import br.fucapi.bpms.web.dominio.Status;
import br.fucapi.bpms.web.dominio.TipoDesenho;
import br.fucapi.bpms.web.dominio.TipoDocumento;
import br.fucapi.bpms.web.dominio.VariaveisProcesso;
import br.fucapi.bpms.web.repositorio.FamiliaProdutoRepositorio;
import br.fucapi.bpms.web.repositorio.MetadadoDesenhoRepositorio;
import br.fucapi.bpms.web.repositorio.OrigemRepositorio;
import br.fucapi.bpms.web.repositorio.PendenciaRepositorio;
import br.fucapi.bpms.web.repositorio.ProtocoloRepositorio;
import br.fucapi.bpms.web.repositorio.TipoDesenhoRepositorio;
import br.fucapi.bpms.web.repositorio.TipoDocumentoRepositorio;

@ManagedBean(name = "processoControle")
@SessionScoped
public class ProcessoControle implements Serializable {

	private static final long serialVersionUID = 13244234324234332L;

	private SimpleDateFormat dateFormat = new SimpleDateFormat(
			"dd/MM/yyyy - hh:mm:ss");

	private FamiliaProduto familiaProduto;
	private Origem origem;
	private TipoDocumento tipoDocumento;
	private TipoDesenho tipoDesenho;
	private ProcessoInstancia processoInstancia;
	private ProcessoDefinicao processoDefinicao;
	private Desenho desenho;
	private Arquivo arquivo;
	private boolean habilitar = false;
	private boolean habilitarDesenho = false;

	private List<FamiliaProduto> familiaProdutos;
	private List<Origem> origems;
	private List<TipoDocumento> tipoDocumentos;
	private List<TipoDesenho> tipoDesenhos;
	private List<ProcessoInstancia> processos;
	private List<ProcessoDefinicao> listaProcessosDefinicao;
	private List<Desenho> desenhos;
	private List<TarefaInstancia> tarefaInstancias;

	private Situacao situacaos;
	private Situacao situacaoDesenhoSubstituidos;

	private String descricao;
	private Protocolo protocolo;

	private Usuario aprovador;
	private Usuario revisor1;
	private Usuario revisor2;
	private Usuario usuario;
	private List<UsuarioGrupo> gruposAlfresco;
	private List<Usuario> usuariosGrupoRevisores;
	private List<Pendencia> pendencias;

	private String status;
	private List<SelectItem> listaStatus;

	private DualListModel<UsuarioGrupo> gruposDualListModel;

	private String sequencial;
	private String ano;

	private List<MetadadoDesenho> metadadoDesenhos;

	private MetadadoDesenho metadadoDesenho;

	private StreamedContent file;

	private ProcessoInstancia processoStart;

	private VariaveisProcesso variaveisProcesso = null;

	@ManagedProperty(value = "#{bpmswebproperties}")
	private Properties bpmswebproperties;

	@ManagedProperty(value = "#{familiaProdutoRepositorioImpl}")
	private FamiliaProdutoRepositorio familiaProdutoRepositorio;

	@ManagedProperty(value = "#{origemRepositorioImpl}")
	private OrigemRepositorio origemRepositorio;

	@ManagedProperty(value = "#{tipoDocumentoRepositorioImpl}")
	private TipoDocumentoRepositorio tipoDocumentoRepositorio;

	@ManagedProperty(value = "#{tipoDesenhoRepositorioImpl}")
	private TipoDesenhoRepositorio tipoDesenhoRepositorio;

	@ManagedProperty(value = "#{protocoloRepositorioImpl}")
	private ProtocoloRepositorio protocoloRepositorio;

	@ManagedProperty(value = "#{metadadoDesenhoRepositorioImpl}")
	private MetadadoDesenhoRepositorio metadadoDesenhoRepositorio;

	@ManagedProperty(value = "#{activitiServicoImpl}")
	private ActivitiServico activitiServico;

	@ManagedProperty(value = "#{alfrescoServicoImpl}")
	private AlfrescoServico alfrescoServico;

	@ManagedProperty(value = "#{pendenciaRepositorioImpl}")
	private PendenciaRepositorio pendenciaRepositorio;

	public String init() {

		this.usuario = (Usuario) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuarioLogado");

		this.familiaProduto = new FamiliaProduto();
		this.tipoDesenho = new TipoDesenho();
		this.origem = new Origem();
		this.desenho = new Desenho();
		this.arquivo = new Arquivo();
		this.tipoDocumento = new TipoDocumento();
		this.processoInstancia = new ProcessoInstancia();
		this.processoDefinicao = new ProcessoDefinicao();
		this.revisor1 = new Usuario();
		this.revisor2 = new Usuario();
		this.aprovador = new Usuario();

		this.gruposAlfresco = new ArrayList<UsuarioGrupo>();
		this.usuariosGrupoRevisores = new ArrayList<Usuario>();
		this.gruposDualListModel = new DualListModel<UsuarioGrupo>();
		this.familiaProdutos = familiaProdutoRepositorio.list();
		this.origems = origemRepositorio.list();
		this.tipoDocumentos = tipoDocumentoRepositorio.list();
		this.tipoDesenhos = tipoDesenhoRepositorio.list();
		this.desenhos = new ArrayList<Desenho>();
		this.pendencias = new ArrayList<Pendencia>();

		// TODO RETIRAR A CONSULTA
		// this.metadadoDesenhos = metadadoDesenhoRepositorio.list();
		this.metadadoDesenhos = new ArrayList<MetadadoDesenho>();
		this.metadadoDesenho = new MetadadoDesenho();

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

		this.desenhos = new ArrayList<Desenho>();

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
		VariaveisProcesso variaveisProcesso = null;
		this.processos = new ArrayList<ProcessoInstancia>();

		if (this.origem.getId() == null) {
			listaResultado = activitiServico
					.getHistoricoProcessosFiltroVariaveis(
							new HashMap<String, Object>(), "TODOS");
		} else {
			Map<String, Object> var = this.filtroVariaveis();
			listaResultado = activitiServico
					.getHistoricoProcessosFiltroVariaveis(var, this.status);
		}

		for (ProcessoInstancia pInstancia : listaResultado) {
			variaveisProcesso = new VariaveisProcesso();
			variaveisProcesso
					.converterListaVariaveisParaVariaveisProcesso(pInstancia
							.getVariables());

			pInstancia.setVariaveisProcesso(variaveisProcesso);
			this.processos.add(pInstancia);
		}
	}

	public String iniciar() {

		this.protocolo = protocoloRepositorio.gerarProtocolo();

		this.processoStart = new ProcessoInstancia();
		this.processoStart.setProcessDefinitionId(processoDefinicao.getId());
		this.processoStart.setBusinessKey(protocolo.toString());
		this.processoStart.setProcessDefinitionId(this.processoDefinicao
				.getId());

		return "/pages/processo/lista_desenho.xhtml";
	}

	public static void main(String[] args) {
		Origem o = new Origem();
		o.setId(1l);
		o.setNome("Teste");
		o.setSigla("TESTE");
		List<Origem> origemLista = new ArrayList<Origem>();
		origemLista.add(o);

		o = new Origem();
		o.setId(2l);
		o.setNome("Teste2");
		o.setSigla("TESTE2");
		origemLista.add(o);

		Origem oTeste = new Origem();
		oTeste.setId(2l);

		if (origemLista.contains(oTeste)) {
			System.out.println("SIM");
			Origem oResult = origemLista.get(origemLista.indexOf(oTeste));
			System.out.println(oResult.getNome());
		} else {
			System.out.println("NAO");
		}
	}

	public String finalizar() {

		if (this.arquivo == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"favor anexar um arquivo.",
							"favor anexar um arquivo."));

			if (this.desenhos.size() == 0)
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"favor anexar um desenho.",
								"favor anexar um desenho."));

			return "/pages/processo/arquivo.xhtml";
		}

		List<Desenho> desenhoTempList = new ArrayList<Desenho>();
		Desenho desenhoTemp = null;
		Arquivo arquivoTemp = null;
		try {
			String uuid = null;
			
			String nomePasta = this.tipoDocumento.getSigla()+"_"+protocolo.getSequencial() + "_" + protocolo.getAno();

			// Bloco responsavel por salvar no repositorio Alfresco os Desenhos
			// de processo
			for (int i = 0; i < this.desenhos.size(); i++) {

				File file = new File(this.desenhos.get(i).getFile()
						.getFileName());
				FileUtils.copyInputStreamToFile(this.desenhos.get(i).getFile()
						.getInputstream(), file);

				uuid = alfrescoServico.anexarArquivo(bpmswebproperties
						.getProperty("bpms.fucapi.parent.uuid"),
						nomePasta,
						null, this.descricao, this.usuario.getTicket(), file);

				this.desenhos.get(i).setUuid(uuid);
				desenhoTemp = this.desenhos.get(i);
				desenhoTemp.setFile(null);
				desenhoTemp.setNomeArquivo(file.getName());
				desenhoTempList.add(desenhoTemp);

				/*
				 * Bloco responsavel por salvar os metadados de desenhos TODO -
				 * E necessario alterar para que o esses metadados sejam salvos
				 * no Alfresco
				 */

				MetadadoDesenho metadadoDesenho = new MetadadoDesenho(
						desenhoTemp, this.protocolo.toString(), this.origem,
						this.tipoDocumento, this.desenhos.get(i)
								.getTipoDesenho());
				metadadoDesenho.setDataCadastro(new Date());
				metadadoDesenhoRepositorio.persist(metadadoDesenho);
			}

			// Bloco responsavel por salvar no repositorio Alfresco o Documento
			// do processo
			File file = new File(this.arquivo.getFile().getFileName());
			FileUtils.copyInputStreamToFile(this.arquivo.getFile()
					.getInputstream(), file);

			uuid = alfrescoServico.anexarArquivo(
					bpmswebproperties.getProperty("bpms.fucapi.parent.uuid"),
					nomePasta, "",
					this.descricao, this.usuario.getTicket(), file);

			this.arquivo.setUuid(uuid);
			arquivoTemp = this.arquivo;
			arquivoTemp.setFile(null);
			arquivoTemp.setNomeArquivo(file.getName());

		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		VariaveisProcesso variaveisProcesso = new VariaveisProcesso();

		variaveisProcesso.setTipoDocumento(this.tipoDocumento);
		variaveisProcesso.setOrigem(this.origem);
		variaveisProcesso.setDescricao(this.descricao);
		variaveisProcesso.setDataInicial(dateFormat.format(new Date()));
		variaveisProcesso.setSequencial(this.protocolo.getSequencial());
		variaveisProcesso.setAno(this.protocolo.getAno());
		variaveisProcesso.setRevisor1(this.revisor1.getUserName());
		variaveisProcesso.setEmailRevisor1(this.revisor1.getEmail());
		variaveisProcesso.setRevisor2(this.revisor2.getUserName());
		variaveisProcesso.setEmailRevisor2(this.revisor2.getEmail());
		variaveisProcesso.setAprovador(this.aprovador.getUserName());
		variaveisProcesso.setEmailAprovador(this.aprovador.getEmail());
		variaveisProcesso.setEmailCriador(this.usuario.getEmail());
		variaveisProcesso.setCriador(this.usuario.getUserName());
		variaveisProcesso.setEmailCriador(this.usuario.getEmail());

		variaveisProcesso.setDesenhos(desenhoTempList);
		variaveisProcesso.setArquivo(arquivoTemp);

		List<String> gruposSelecionado = new ArrayList<String>();

		for (UsuarioGrupo usuarioGrupo : this.gruposDualListModel.getTarget()) {
			gruposSelecionado.add(usuarioGrupo.getShortName());
		}

		variaveisProcesso.setGruposNotificar(gruposSelecionado);

		Map<String, Object> paramsVariaveisProcesso = variaveisProcesso
				.converterVariaveisProcessoParaMapaVariaveis();
		paramsVariaveisProcesso.put("businessKey", this.protocolo.toString());

		String responseJson = activitiServico
				.iniciarInstanciaProcessoPorParametros(
						this.processoDefinicao.getId(),
						this.protocolo.toString(), paramsVariaveisProcesso);

		if (responseJson == null) {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Ocorreu um erro, favor entrar em contato com o suporte.",
									"Ocorreu um erro, favor entrar em contato com o suporte."));

			return "/pages/processo/lista_desenho.xhtml";// SUCESSO
		}

		this.origem = new Origem();
		return "/pages/processo/ok.xhtml";

		// pesquisar();
		// return telaSearch();
	}

	public String telaArquivo() {

		if (this.desenhos.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"favor anexar um arquivo.",
							"favor anexar um arquivo."));
			return "/pages/processo/lista_desenho.xhtml";
		}

		return "/pages/processo/arquivo.xhtml";
	}

	public String telaSearch() {
		return "/pages/processo/search.xhtml";
	}

	public String telaStart() {
		return "/pages/processo/start.xhtml";
	}

	public String telaSelecionar() {

		return "/pages/processo/selecionar.xhtml";
	}

	public String telaParecer() {
		return "/pages/processo/selecionar.xhtml";
	}

	public String telaListaDesenho() {
		return "/pages/processo/lista_desenho.xhtml";
	}

	public String telaListaDetalhe() {
		return "/pages/processo/detalhe.xhtml";
	}

	public String detalhe(ProcessoInstancia entity) {

		this.processoInstancia = entity;
		this.tarefaInstancias = activitiServico
				.getTarefasProcessoInstancia(this.processoInstancia.getId());

		for (TarefaInstancia tarefaInstancia : this.tarefaInstancias) {
			variaveisProcesso = new VariaveisProcesso();
			variaveisProcesso
					.converterListaVariaveisParaVariaveisProcesso(tarefaInstancia
							.getVariables());
			tarefaInstancia.setVariaveisProcesso(variaveisProcesso);
			this.desenhos = ((VariaveisProcesso) tarefaInstancia
					.getVariaveisProcesso()).getDesenhos();
		}
		return "/pages/processo/detalhe.xhtml";
	}

	public String telaDetalhe() {
		return "/pages/processo/detalhe.xhtml";
	}

	public String consultarDesenho(Desenho desenho) {
		this.desenho = desenho;
		return "/pages/processo/desenho.xhtml";

	}

	public void downloadDesenho(Desenho desenho) {
		InputStream temp = alfrescoServico.baixarArquivo(
				desenho.getNomeArquivo(), desenho.getUuid());
		file = new DefaultStreamedContent(temp, null, desenho.getNomeArquivo());
	}

	public void downloadDesenho() {
		InputStream temp = alfrescoServico.baixarArquivo(
				this.desenho.getNomeArquivo(), this.desenho.getUuid());

		file = new DefaultStreamedContent(temp, null, desenho.getNomeArquivo());
	}

	public void downloadDesenhoSubstituido() {
		// verifica se tem arquivo anexado
		if (this.desenho.getUuidSubstituido() == null
				|| this.desenho.getUuidSubstituido().length() < 1)
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"favor anexar um arqu.",
									"favor anexar um arquivo."));
		else {
			InputStream temp = alfrescoServico.baixarArquivo(
					this.desenho.getNomeArquivoSubstituido(),
					this.desenho.getUuidSubstituido());
			file = new DefaultStreamedContent(temp, null,
					desenho.getNomeArquivoSubstituido());
		}

	}

	public void downloadArquivo() {
		String nomeArquivo = this.variaveisProcesso.getArquivo()
				.getNomeArquivo();
		String uuidArquivo = this.variaveisProcesso.getArquivo().getUuid();

		InputStream temp = alfrescoServico.baixarArquivo(nomeArquivo,
				uuidArquivo);
		file = new DefaultStreamedContent(temp, null, nomeArquivo);
	}

	public void handleFileUpload(FileUploadEvent event) {
		try {

			this.arquivo.setFile(event.getFile());
			this.arquivo.setNomeArquivo(this.arquivo.getFile().getFileName());

			this.habilitar = true;

			FacesMessage msg = new FacesMessage("o documento "
					+ this.arquivo.getFile().getFileName()
					+ " foi anexado na solicitação ", this.arquivo.getFile()
					.getFileName() + " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handleFileUploadDesenho(FileUploadEvent event) {
		this.desenho.setFile(event.getFile());
		this.habilitarDesenho = true;

		FacesMessage msg = new FacesMessage(
				"o arquivo "
						+ this.desenho.getFile().getFileName()
						+ " foi carregado. Clique no botão Anexar Desenho para incluir o desenho ",
				this.desenho.getFile().getFileName() + " ");
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public String uploadNovoDesenho() {

		if (this.desenho.getFile() != null) {
			FacesMessage msg = new FacesMessage("o desenho "
					+ this.desenho.getFile().getFileName()
					+ " foi incluído na solicitação ", this.desenho.getFile()
					.getFileName() + " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			addDesenho();
			this.habilitarDesenho = false;
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"favor selecionar o arquivo.", "o arquivo "));
		}

		return "/pages/processo/lista_desenho.xhtml";
	}

	public String listaConfirmacao() {

		if (this.processoInstancia.getBusinessKey() != null
				&& this.processoInstancia.getBusinessKey().length() > 0)
			this.pendencias = pendenciaRepositorio
					.listarPendenciaPorBusinessKey(this.processoInstancia
							.getBusinessKey());

		return "/pages/processo/confirmacao_leitura.xhtml";
	}

	public FamiliaProduto getFamiliaProduto() {
		return familiaProduto;
	}

	public void setFamiliaProduto(FamiliaProduto familiaProduto) {
		this.familiaProduto = familiaProduto;
	}

	public Origem getOrigem() {
		return origem;
	}

	public void setOrigem(Origem origem) {
		this.origem = origem;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public List<FamiliaProduto> getFamiliaProdutos() {
		return this.familiaProdutos;
	}

	public void setFamiliaProdutos(List<FamiliaProduto> familiaProdutos) {
		this.familiaProdutos = familiaProdutos;
	}

	public List<Origem> getOrigems() {
		return this.origems;
	}

	public void setOrigems(List<Origem> origems) {
		this.origems = origems;
	}

	public List<TipoDocumento> getTipoDocumentos() {
		return this.tipoDocumentos;
	}

	public void setTipoDocumentos(List<TipoDocumento> tipoDocumentos) {
		this.tipoDocumentos = tipoDocumentos;
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

	public List<SelectItem> getListaStatus() {
		this.listaStatus = new ArrayList<SelectItem>();
		for (Status s : Status.values()) {
			this.listaStatus.add(new SelectItem(s, s.getNome()));
		}
		return this.listaStatus;
	}

	public Desenho getDesenho() {
		return desenho;
	}

	public void setDesenho(Desenho desenho) {
		this.desenho = desenho;
	}

	public List<Desenho> getDesenhos() {
		return desenhos;
	}

	public void setDesenhos(List<Desenho> desenhos) {
		this.desenhos = desenhos;
	}

	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
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

	public List<MetadadoDesenho> getMetadadoDesenhos() {
		return metadadoDesenhos;
	}

	public void setMetadadoDesenhos(List<MetadadoDesenho> metadadoDesenhos) {
		this.metadadoDesenhos = metadadoDesenhos;
	}

	public MetadadoDesenho getMetadadoDesenho() {
		return metadadoDesenho;
	}

	public void setMetadadoDesenho(MetadadoDesenho metadadoDesenho) {
		this.metadadoDesenho = metadadoDesenho;
	}

	public List<SelectItem> getSituacaos() {
		List<SelectItem> enumSitucao = new ArrayList<SelectItem>();
		for (Situacao ms : Situacao.values()) {
			enumSitucao.add(new SelectItem(ms, ms.getNome()));
		}
		return enumSitucao;
	}

	public void setSituacaos(Situacao situacaos) {
		this.situacaos = situacaos;
	}

	public List<SelectItem> getSituacaoDesenhoSubstituidos() {
		List<SelectItem> enumSitucao = new ArrayList<SelectItem>();
		for (Situacao ms : Situacao.values()) {
			enumSitucao.add(new SelectItem(ms, ms.getNome()));
		}
		return enumSitucao;
	}

	public void setSituacaoDesenhoSubstituidos(Situacao situacaos) {
		this.situacaos = situacaos;
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

	public String uploadArquivo() {
		if (this.arquivo.getFile() != null) {
			FacesMessage msg = new FacesMessage("Succesful", this.arquivo
					.getFile().getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {

		}

		return "pages/processo/arquivo.xhtml";

	}

	public String excluir(Desenho desenho) {
		this.desenho = desenho;
		excluirItemLista(desenho.getId());
		return "pages/processo/lista_desenho.xhtml";
	}

	public String excluir() {
		return "pages/processo/lista_desenho.xhtml";
	}

	public String addDesenho() {

		if (this.familiaProdutos.contains(this.familiaProduto)) {
			this.familiaProduto = this.familiaProdutos.get(this.familiaProdutos
					.indexOf(this.familiaProduto));
		}

		if (this.tipoDesenhos.contains(this.tipoDesenho)) {
			this.tipoDesenho = this.tipoDesenhos.get(this.tipoDesenhos
					.indexOf(this.tipoDesenho));
		}

		this.desenho.setId(this.desenhos.size());
		this.desenho.setFamilia(this.familiaProduto);
		this.desenho.setTipoDesenho(this.tipoDesenho);
		this.desenhos.add(this.desenho);
		this.desenho = new Desenho();
		return "pages/processo/lista_desenho.xhtml";
	}

	public void excluirItemLista(int i) {
		List<Desenho> list = new ArrayList<Desenho>();
		this.desenhos.remove(i);
		list = this.desenhos;
		this.desenhos = new ArrayList<Desenho>();
		int j = 0;
		for (Desenho desenho : list) {
			desenho.setId(j);
			j++;
			this.desenhos.add(desenho);
		}

	}

	private Map<String, Object> filtroVariaveis() {

		Map<String, Object> var = new HashMap<String, Object>();

		if (this.origem.getId() != null && this.origem.getId() != 0) {
			var.put("origemId", this.origem.getId());
		}
		if (this.tipoDocumento.getId() != null
				&& this.tipoDocumento.getId() != 0) {
			var.put("tipoDocumentoId", this.tipoDocumento.getId());
		}
		if (this.sequencial != null && !this.sequencial.equals("")) {
			var.put("sequencial", this.sequencial);
		}
		if (this.ano != null && !this.ano.equals("")) {
			var.put("ano", this.ano);
		}
		return var;
	}

	public FamiliaProdutoRepositorio getFamiliaProdutoRepositorio() {
		return familiaProdutoRepositorio;
	}

	public void setFamiliaProdutoRepositorio(
			FamiliaProdutoRepositorio familiaProdutoRepositorio) {
		this.familiaProdutoRepositorio = familiaProdutoRepositorio;
	}

	public OrigemRepositorio getOrigemRepositorio() {
		return origemRepositorio;
	}

	public void setOrigemRepositorio(OrigemRepositorio origemRepositorio) {
		this.origemRepositorio = origemRepositorio;
	}

	public TipoDocumentoRepositorio getTipoDocumentoRepositorio() {
		return tipoDocumentoRepositorio;
	}

	public void setTipoDocumentoRepositorio(
			TipoDocumentoRepositorio tipoDocumentoRepositorio) {
		this.tipoDocumentoRepositorio = tipoDocumentoRepositorio;
	}

	public TipoDesenhoRepositorio getTipoDesenhoRepositorio() {
		return tipoDesenhoRepositorio;
	}

	public void setTipoDesenhoRepositorio(
			TipoDesenhoRepositorio tipoDesenhoRepositorio) {
		this.tipoDesenhoRepositorio = tipoDesenhoRepositorio;
	}

	public ProtocoloRepositorio getProtocoloRepositorio() {
		return protocoloRepositorio;
	}

	public void setProtocoloRepositorio(
			ProtocoloRepositorio protocoloRepositorio) {
		this.protocoloRepositorio = protocoloRepositorio;
	}

	public MetadadoDesenhoRepositorio getMetadadoDesenhoRepositorio() {
		return metadadoDesenhoRepositorio;
	}

	public void setMetadadoDesenhoRepositorio(
			MetadadoDesenhoRepositorio metadadoDesenhoRepositorio) {
		this.metadadoDesenhoRepositorio = metadadoDesenhoRepositorio;
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

	/*
	 * TODO - Solucao temporario ate resolvermos o ploblema com o filtro de
	 * pesquisa. O ideal e que o JSF guarde todo o objeto ao inves do atributo
	 * id ou nome
	 */
	private void setValoresDeCollectionParaObjetos() {

		for (Origem origem : this.origems) {
			if (origem.getId() == this.origem.getId()) {
				this.origem = origem;
				break;
			}
		}

		for (TipoDocumento tipoDocumento : this.tipoDocumentos) {
			if (tipoDocumento.getId() == this.tipoDocumento.getId()) {
				this.tipoDocumento = tipoDocumento;
				break;
			}
		}

		for (Usuario uAprovador : this.usuariosGrupoRevisores) {
			if (uAprovador.getUserName().equals(this.aprovador.getUserName())) {
				this.aprovador = uAprovador;
				break;
			}
		}

		for (Usuario uRevisor1 : this.usuariosGrupoRevisores) {
			if (uRevisor1.getUserName().equals(this.revisor1.getUserName())) {
				this.revisor1 = uRevisor1;
				break;
			}
		}

		for (Usuario uRevisor2 : this.usuariosGrupoRevisores) {
			if (uRevisor2.getUserName().equals(this.revisor2.getUserName())) {
				this.revisor2 = uRevisor2;
				break;
			}
		}
	}

	public StreamedContent getFile() {
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public VariaveisProcesso getVariaveisProcesso() {
		return variaveisProcesso;
	}

	public void setVariaveisProcesso(VariaveisProcesso variaveisProcesso) {
		this.variaveisProcesso = variaveisProcesso;
	}

	public PendenciaRepositorio getPendenciaRepositorio() {
		return pendenciaRepositorio;
	}

	public void setPendenciaRepositorio(
			PendenciaRepositorio pendenciaRepositorio) {
		this.pendenciaRepositorio = pendenciaRepositorio;
	}

	public List<Pendencia> getPendencias() {
		return pendencias;
	}

	public void setPendencias(List<Pendencia> pendencias) {
		this.pendencias = pendencias;
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

	public TipoDesenho getTipoDesenho() {
		return tipoDesenho;
	}

	public void setTipoDesenho(TipoDesenho tipoDesenho) {
		this.tipoDesenho = tipoDesenho;
	}

	public List<TipoDesenho> getTipoDesenhos() {
		return tipoDesenhos;
	}

	public void setTipoDesenhos(List<TipoDesenho> tipoDesenhos) {
		this.tipoDesenhos = tipoDesenhos;
	}

	public void pesquisarDesenhos() {
		this.metadadoDesenhos = metadadoDesenhoRepositorio
				.pesquisarDesenhoAnterior(metadadoDesenho);
	}

	public void selecionarMetadadoDesenho(MetadadoDesenho metadadoDesenho) {
		System.out.println(metadadoDesenho.getBusinessKey());
		System.out.println(metadadoDesenho.getUuid());
		this.desenho.setProtocoloAnterior(metadadoDesenho.getBusinessKey());
		this.desenho.setUuidSubstituido(metadadoDesenho.getUuid());
		this.desenho
				.setNomeArquivoSubstituido(metadadoDesenho.getNomeArquivo());

	}

	public String pesquisarSolicitacao() {

		pesquisar();

		return telaSearch();
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
					null,
					new JRBeanCollectionDataSource(this.pendencias, false));

			JasperExportManager.exportReportToPdfStream(impressao,
					response.getOutputStream());

			context.responseComplete();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro: " + e.getMessage());
		}
	}
}