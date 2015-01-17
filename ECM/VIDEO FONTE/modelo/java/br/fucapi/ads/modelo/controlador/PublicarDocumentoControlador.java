package br.fucapi.ads.modelo.controlador;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.apache.commons.io.FileUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

import br.fucapi.ads.modelo.dominio.Arquivo;
import br.fucapi.ads.modelo.dominio.PostoCopia;
import br.fucapi.ads.modelo.dominio.Protocolo;
import br.fucapi.ads.modelo.dominio.VariaveisTarefa;
import br.fucapi.ads.modelo.dominio.VariaveisTreinamento;
import br.fucapi.ads.modelo.dominio.VariavelPublicarDocumento;
import br.fucapi.ads.modelo.regranegocio.TreinamentoRN;
import br.fucapi.ads.modelo.servico.PostoCopiaServico;
import br.fucapi.ads.modelo.servico.ProtocoloServico;
import br.fucapi.ads.modelo.servico.SetorServico;
import br.fucapi.ads.modelo.servico.TipoDocumentoServico;
import br.fucapi.ads.modelo.servico.UnidadeServico;
import br.fucapi.ads.modelo.servico.VariaveisTarefaServico;
import br.fucapi.bpms.activiti.dominio.ProcessoDefinicao;
import br.fucapi.bpms.activiti.dominio.ProcessoInstancia;
import br.fucapi.bpms.activiti.dominio.TarefaInstancia;
import br.fucapi.bpms.activiti.servico.ActivitiServico;
import br.fucapi.bpms.activiti.util.JsonUtil;
import br.fucapi.bpms.alfresco.dominio.Usuario;
import br.fucapi.bpms.alfresco.dominio.UsuarioGrupo;
import br.fucapi.bpms.alfresco.servico.AlfrescoServico;

import com.sun.xml.ws.api.PropertySet.Property;

@ManagedBean
@SessionScoped
public class PublicarDocumentoControlador implements Serializable {

	private static final long serialVersionUID = 13244234324234332L;

	private VariavelPublicarDocumento variaveis;

	private ProcessoInstancia processoInstancia;
	private ProcessoDefinicao processoDefinicao;

	private List<ProcessoInstancia> lista;

	private List<ProcessoDefinicao> listaProcessosDefinicao;
	private List<TarefaInstancia> tarefaInstancias;

	private List<TarefaInstancia> tarefas;

	private TarefaInstancia tarefa;

	private VariaveisTarefa variaveisTarefa;

	private String descricao;
	private Protocolo protocolo;

	private Usuario usuarioLogado;

	private List<UsuarioGrupo> gruposAlfresco;
	private List<Usuario> usuariosGrupoRevisores;

	private DualListModel<UsuarioGrupo> gruposDualListModel;

	private List<Usuario> usuarios;
	
	private List<PostoCopia> postosCopia;

	private String imagem;

	private String motivoCancelamento;

	private VariaveisTreinamento variaveisTreinamento;

	private ProcessoInstancia processoStart;
	
	// Atributos aprovadores
	private List<Usuario> aprovadoresSource;
	
    private List<Usuario> aprovadoresTarget;
    
    private DualListModel<Usuario> aprovadores;

    // Atributos Concensao
    
	private List<Usuario> listaUsuarioConcensaoSource;
	
    private List<Usuario> listaUsuarioConcensaoTarget;
    
    private DualListModel<Usuario> listaUsuarioConcensao;
    
    // Atributo PostoCopia
    
	private List<PostoCopia> listaPostosCopiaSource;
	
    private List<PostoCopia> listaPostosCopiaTarget;
    
    private DualListModel<PostoCopia> listaPostosCopia;
    
    private UploadedFile uploadedfile;
    
	@ManagedProperty(value = "#{activitiServicoImpl}")
	private ActivitiServico activitiServico;

	@ManagedProperty(value = "#{alfrescoServicoImpl}")
	private AlfrescoServico alfrescoServico;

	@Property(value = "#{web.properties}")
	private Properties bpmswebproperties;

	@ManagedProperty(value = "#{paginaCentralControladorBean}")
	private PaginaCentralControladorBean paginaCentralControladorBean;

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

	@ManagedProperty(value = "#{tipoDocumentoServicoImpl}")
	private TipoDocumentoServico tipoDocumentoServico;

	private String TELA_PESQUISA = "paginas/solicitacao/pesquisa.xhtml";
	
	private String TELA_CADASTRO = "paginas/solicitacao/publicardocumento/cadastro.xhtml";

	private String TELA_DETALHE = "paginas/solicitacao/publicardocumento/detalhe.xhtml";

	private String TELA_DETALHE_TAREFA = "paginas/solicitacao/publicardocumento/detalhetarefa.xhtml";

	public String inicioNovaSolicitacao() {
		
		this.variaveis = new VariavelPublicarDocumento();

		this.processoDefinicao = new ProcessoDefinicao();
		this.processoDefinicao.setKey(variaveis.getTipoSolicitacao());

		this.gruposDualListModel = new DualListModel<UsuarioGrupo>();
		this.usuarios = alfrescoServico.getUsuarios();

		this.variaveis.setProprietarios(usuarios);

		this.usuarioLogado = (Usuario) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		this.variaveis.setUnidades(unidadeServico.listAll());
		
		this.postosCopia = postoCopiaServico.listAll();
		
		this.variaveis.setSetores(setorServico.listAll());
		this.variaveis.setTipoDocumentos(tipoDocumentoServico.listAll());

		// TODO foi incluido o redirect porque as paginas não estavam
		// carregando os componentes da paginas, devido a um bug do primefaces
		
		// TODO Eh necessario Pesquisar a lista de usuarios que tenham perfil de aprovador
		this.aprovadoresSource = new ArrayList<Usuario>();
		this.aprovadoresSource.addAll(usuarios);
		
		this.aprovadoresTarget = new ArrayList<Usuario>();
	    this.aprovadores = new DualListModel<Usuario>(this.aprovadoresSource, this.aprovadoresTarget);
		
	    // TODO Eh necessario Pesquisar a lista de usuarios que tenham perfil de concensao
 		this.listaUsuarioConcensaoSource = new ArrayList<Usuario>();
 		this.listaUsuarioConcensaoSource.addAll(usuarios);
 		
 		this.listaUsuarioConcensaoTarget = new ArrayList<Usuario>();
 	    this.listaUsuarioConcensao = new DualListModel<Usuario>(this.listaUsuarioConcensaoSource, this.listaUsuarioConcensaoTarget);
 	    
 	    // Lista posto copia
 	    
 	    this.listaPostosCopiaSource = new ArrayList<PostoCopia>();
		this.listaPostosCopiaSource.addAll(this.postosCopia);
		
		this.listaPostosCopiaTarget = new ArrayList<PostoCopia>();
	    this.listaPostosCopia = new DualListModel<PostoCopia>(this.listaPostosCopiaSource, this.listaPostosCopiaTarget);
		
		paginaCentralControladorBean.setPaginaCentral(this.TELA_CADASTRO);
		
		return "index.xhtml?faces-redirect=true";
	}

	@PostConstruct
	public void init() {

		this.pesquisar();
	}

	/**
	 * Metodo responsavel por salvar uma nova solicitacao de treinamento no
	 * activiti
	 */
	public String salvarNovaSolicitacao() {

		this.protocolo = protocoloServico.gerarProtocolo();

//		this.salvarArquivoAlfresco();
		
		// Trata as listas de aprovadores e de concensos para que apesnas o login e o email destes sejam enviados ao Activiti
		this.variaveis.tratarAtributosParaSalvar(this.aprovadoresTarget, this.listaUsuarioConcensaoTarget);
		
		// Seta no processo os dados do Solicitante
		this.variaveis.setSolicitante(this.usuarioLogado.getUserName());
		this.variaveis.setProprietario(this.usuarioLogado);
		
		this.variaveis.setSequencial(this.protocolo.getSequencial());
		this.variaveis.setAno(this.protocolo.getAno());
		
		// Converte as variaveis de processo em um Objeto Json
		// String json = variaveisTreinamento.ObjectToJson(
		// this.processoDefinicao.getKey(), this.protocolo);

		// Envia o Objeto Json referente ao novo processo para ser executado
		// atraves de um servico Rest
		// this.activitiServico.iniciarInstanciaProcesso(json);

		// TODO - Realizacao de testes
		this.activitiServico.iniciarInstanciaProcessoPorParametrosByKey(
				variaveis.getPUBLICAR_DOCUMENTO(), this.protocolo.toString(), variaveis
						.converterVariaveis());

		RequestContext request = RequestContext.getCurrentInstance();
		request.execute("sucessoDialog.show()");

		this.variaveis = new VariavelPublicarDocumento();
		
		this.telaPesquisa();

		return "index.xhtml?faces-redirect=true";
	}

	public void cancelar() {

		Map<String, String> variaveis = new HashMap<String, String>();
		variaveis.put("parecer", this.getMotivoCancelamento());
		variaveis.put("acaoParecer", "PROCESSO CONCELADO");

		String json = JsonUtil.converterVariaveisToJson(variaveis);
		this.activitiServico.atualizarVariaveis(this.processoInstancia.getId(),
				json);

		this.activitiServico.cancelarProcessoInstaciado(processoInstancia
				.getId());

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "",
				"Solicitação cancelado com sucesso!");
		FacesContext.getCurrentInstance().addMessage("msg", message);

		this.telaPesquisa();
	}

	public void detalhe(ProcessoInstancia entity) throws ParseException {

		this.processoInstancia = entity;

		this.tarefaInstancias = activitiServico
				.getHistoricoTarefasPorVariaveis(null, null, null, null,
						this.processoInstancia.getId());

		for (TarefaInstancia tarefaInstancia : this.tarefaInstancias) {
			this.variaveisTreinamento = new VariaveisTreinamento();

			this.variaveisTreinamento
					.converterListaVariaveisParaVariaveisProcesso(tarefaInstancia
							.getVariables());
			// TODO
			// this.variaveisTarefa = variaveisTarefaServico.findById(new
			// Long(tarefaInstancia.getId()));
			this.variaveisTarefa = null;

			// Atualiza as variaveis da tarefa com os dados inseridos no banco
			if (this.variaveisTarefa != null)
				this.variaveisTreinamento
						.converterVariaveisTarefa(this.variaveisTarefa);

			if (tarefaInstancia.getEndTime() != null) {
				this.variaveisTreinamento.setSituacao("FINALIZADO");
			} else {
				this.variaveisTreinamento.setSituacao("EM ANDAMENTO");
			}
			tarefaInstancia.setVariaveisProcesso(this.variaveisTreinamento);
		}

		paginaCentralControladorBean.setPaginaCentral(this.TELA_DETALHE);

	}

	public void telaPesquisa() {
		paginaCentralControladorBean.setPaginaCentral(this.TELA_PESQUISA);

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

	public void pesquisar() {

		List<ProcessoInstancia> listaResultado = null;
		// Variaveis variaveisProcesso = null;
		this.lista = new ArrayList<ProcessoInstancia>();

		Map<String, Object> var = this.filtroVariaveis();
		listaResultado = activitiServico.getHistoricoProcessosFiltroVariaveis(
				var, "PENDENTE");

		for (ProcessoInstancia pInstancia : listaResultado) {
			this.variaveis = new VariavelPublicarDocumento();
			this.variaveis.converterListaVariaveis(pInstancia.getVariables());

			pInstancia.setVariaveis(variaveis);
			this.lista.add(pInstancia);
		}
	}

	private Map<String, Object> filtroVariaveis() {

		Map<String, Object> var = new HashMap<String, Object>();

		return var;
	}

	public void detalheTarefa(TarefaInstancia tarefa) {
		this.tarefa = tarefa;
		// TODO
		// this.variaveisTarefa =
		// variaveisTarefaServico.findById(Long.valueOf(tarefa.getId()));
		this.variaveisTarefa = null;

		if (this.variaveisTarefa != null) {
			((VariaveisTreinamento) this.tarefa.getVariaveisProcesso())
					.setAcao(this.variaveisTarefa.getAcao());
			((VariaveisTreinamento) this.tarefa.getVariaveisProcesso())
					.setParecer(this.variaveisTarefa.getParecer());
		}
		this.paginaCentralControladorBean
				.setPaginaCentral(this.TELA_DETALHE_TAREFA);
	}

	public void abrirImagemProcesso(ProcessoInstancia entity) {

		System.out.println("processoInstancia.getId()"
				+ processoInstancia.getId());
		this.processoInstancia = entity;
		setImagem(activitiServico
				.getProcessoDiagrama(processoInstancia.getId()));

	}

	public void telaDetalhe() {
		this.paginaCentralControladorBean.setPaginaCentral(this.TELA_DETALHE);
	}
	
	public void upload() {
		if (uploadedfile != null) {
			FacesMessage message = new FacesMessage("Sucecesful", uploadedfile.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public void salvarArquivoAlfresco() {
		Arquivo arquivoTemp = new Arquivo();

		try {
			String uuid = null;
			
			String nomePasta = ""+this.protocolo.getSequencial()+this.protocolo.getAno();

			// Bloco responsavel por salvar no repositorio Alfresco os Documentos publicados
			
			File file = new File(this.uploadedfile.getFileName());
			
			FileUtils.copyInputStreamToFile(this.uploadedfile.getInputstream(), file);

			uuid = alfrescoServico.anexarArquivo(
				bpmswebproperties.getProperty("uuid.parent.requerimentos"),
				nomePasta, "",
				this.descricao, this.usuarioLogado.getTicket(), file);

			arquivoTemp.setUuid(uuid);
			arquivoTemp.setNomeArquivo(file.getName());
				
			this.variaveis.setArquivo(arquivoTemp);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			/*
			 * Bloco responsavel por salvar os metadados de desenhos TODO -
			 * E necessario alterar para que o esses metadados sejam salvos
			 * no Alfresco
			 */

//			MetadadoDesenho metadadoDesenho = new MetadadoDesenho(
//					desenhoTemp, this.protocolo.toString(), this.origem,
//					this.tipoDocumento, this.desenhos.get(i)
//							.getTipoDesenho());
//			metadadoDesenho.setDataCadastro(new Date());
//			metadadoDesenhoRepositorio.persist(metadadoDesenho);
		
	}
	
	// Teste
	private boolean skip;
	
	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}
	// Teste
	
	public String onFlowProcess(FlowEvent event) {
		if (skip) {
			skip = false;
			return "confirm";
		} else {
			return event.getNewStep();
		}
	}
	
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
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
	
	public List<Usuario> getAprovadoresSource() {
		return aprovadoresSource;
	}

	public void setAprovadoresSource(List<Usuario> aprovadoresSource) {
		this.aprovadoresSource = aprovadoresSource;
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

	public List<Usuario> getListaUsuarioConcensaoSource() {
		return listaUsuarioConcensaoSource;
	}

	public void setListaUsuarioConcensaoSource(
			List<Usuario> listaUsuarioConcensaoSource) {
		this.listaUsuarioConcensaoSource = listaUsuarioConcensaoSource;
	}

	public List<Usuario> getListaUsuarioConcensaoTarget() {
		return listaUsuarioConcensaoTarget;
	}

	public void setListaUsuarioConcensaoTarget(
			List<Usuario> listaUsuarioConcensaoTarget) {
		this.listaUsuarioConcensaoTarget = listaUsuarioConcensaoTarget;
	}

	public DualListModel<Usuario> getListaUsuarioConcensao() {
		return listaUsuarioConcensao;
	}

	public void setListaUsuarioConcensao(
			DualListModel<Usuario> listaUsuarioConcensao) {
		this.listaUsuarioConcensao = listaUsuarioConcensao;
	}

	public void setAprovadoresTarget(List<Usuario> aprovadoresTarget) {
		this.aprovadoresTarget = aprovadoresTarget;
	}

	public List<PostoCopia> getListaPostosCopiaSource() {
		return listaPostosCopiaSource;
	}

	public void setListaPostosCopiaSource(List<PostoCopia> listaPostosCopiaSource) {
		this.listaPostosCopiaSource = listaPostosCopiaSource;
	}

	public List<PostoCopia> getListaPostosCopiaTarget() {
		return listaPostosCopiaTarget;
	}

	public void setListaPostosCopiaTarget(List<PostoCopia> listaPostosCopiaTarget) {
		this.listaPostosCopiaTarget = listaPostosCopiaTarget;
	}

	public DualListModel<PostoCopia> getListaPostosCopia() {
		return listaPostosCopia;
	}

	public void setListaPostosCopia(DualListModel<PostoCopia> listaPostosCopia) {
		this.listaPostosCopia = listaPostosCopia;
	}

	public UploadedFile getUploadedfile() {
		return uploadedfile;
	}

	public void setUploadedfile(UploadedFile uploadedfile) {
		this.uploadedfile = uploadedfile;
	}

	public AlfrescoServico getAlfrescoServico() {
		return alfrescoServico;
	}

	public void setAlfrescoServico(AlfrescoServico alfrescoServico) {
		this.alfrescoServico = alfrescoServico;
	}

	public VariaveisTreinamento getVariaveisTreinamento() {
		return variaveisTreinamento;
	}

	public void setVariaveisTreinamento(
			VariaveisTreinamento variaveisTreinamento) {
		this.variaveisTreinamento = variaveisTreinamento;
	}

	public PaginaCentralControladorBean getPaginaCentralControladorBean() {
		return paginaCentralControladorBean;
	}

	public void setPaginaCentralControladorBean(
			PaginaCentralControladorBean paginaCentralControladorBean) {
		this.paginaCentralControladorBean = paginaCentralControladorBean;
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

	public List<PostoCopia> getPostosCopia() {
		return postosCopia;
	}

	public void setPostosCopia(List<PostoCopia> postosCopia) {
		this.postosCopia = postosCopia;
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
			builder.append(((Usuario) item).getFirstName()).append("<br />");
		}

		FacesMessage msg = new FacesMessage();
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		msg.setSummary("Items Transferred");
		msg.setDetail(builder.toString());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
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

	public TipoDocumentoServico getTipoDocumentoServico() {
		return tipoDocumentoServico;
	}

	public void setTipoDocumentoServico(
			TipoDocumentoServico tipoDocumentoServico) {
		this.tipoDocumentoServico = tipoDocumentoServico;
	}

}