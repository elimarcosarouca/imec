package br.fucapi.ads.modelo.controlador;

import java.io.Serializable;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.springframework.security.core.context.SecurityContextHolder;

import br.fucapi.ads.modelo.dominio.Protocolo;
import br.fucapi.ads.modelo.dominio.VariaveisTarefa;
import br.fucapi.ads.modelo.dominio.VariaveisTreinamento;
import br.fucapi.ads.modelo.regranegocio.TreinamentoRN;
import br.fucapi.ads.modelo.servico.ProtocoloServico;
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
public class TreinamentoControlador implements Serializable {

	private static final long serialVersionUID = 13244234324234332L;

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

	private Usuario coordenador;
	private Usuario diretor;
	private Usuario RH;

	private Usuario usuarioLogado;
	private Usuario funcionario;

	private List<UsuarioGrupo> gruposAlfresco;
	private List<Usuario> usuariosGrupoRevisores;

	private String status;

	private DualListModel<UsuarioGrupo> gruposDualListModel;

	private List<Usuario> usuarios;

	private String sequencial;
	private String ano;
	
	private String imagem;
	
	private String motivoCancelamento;

	private VariaveisTreinamento variaveisTreinamento;

	private ProcessoInstancia processoStart;

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
	
	private String TELA_PESQUISA = "paginas/solicitacao/pesquisa.xhtml";
	
	private String TELA_DETALHE = "paginas/solicitacao/treinamento/detalhe.xhtml";
	
	private String TELA_DETALHE_TAREFA = "paginas/solicitacao/treinamento/detalhetarefa.xhtml";

//	private String dataInicial;
//	private String dataFinal;
	
	public String inicioNovaSolicitacao() {

		this.variaveisTreinamento = new VariaveisTreinamento();
		this.variaveisTreinamento.setSistema("FUCAPI");
		this.variaveisTreinamento.setTipoSolicitacao("TREINAMENTO");

		this.processoDefinicao = new ProcessoDefinicao();
		this.processoDefinicao.setKey("TREINAMENTO");

		this.gruposDualListModel = new DualListModel<UsuarioGrupo>();
		this.usuarios = alfrescoServico.getUsuarios();
		this.usuarioLogado = (Usuario) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		// TODO foi incluido o redirect porque as paginas não estavam
		// carregando os componentes da paginas, devido a um bug do primefaces
		return "index.xhtml?faces-redirect=true";
	}

	@PostConstruct
	public void init() {

		this.inicioNovaSolicitacao();

		this.pesquisar();
	}

	/**
	 * Metodo responsavel por salvar uma nova solicitacao de treinamento no
	 * activiti
	 */
	public void salvarNovaSolicitacao() {

		try {
			this.treinamentoRN.iniciarProcesso(this.variaveisTreinamento,
					this.funcionario);
		} catch (RemoteException e) {
			e.printStackTrace();
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Erro",
					"Ocorreu um erro ao inserir os campos informados na solicitação. Por favor, entre em contato com o administrador do sistema!");
			FacesContext.getCurrentInstance().addMessage("msg", message);
			return;
		}

		this.protocolo = protocoloServico
				.gerarProtocolo(this.variaveisTreinamento.getTipoSolicitacao());

		this.variaveisTreinamento.setSequencial(protocolo.getSequencial());
		this.variaveisTreinamento.setAno(protocolo.getAno());

		// Seta no processo os dados do Solicitante do Treinamento
		this.variaveisTreinamento.setSolicitante(this.usuarioLogado
				.getUserName());
		this.variaveisTreinamento.setEmailSolicitante(this.usuarioLogado
				.getEmail());

		// Converte as variaveis de processo em um Objeto Json
		String json = variaveisTreinamento.ObjectToJson(
				this.processoDefinicao.getKey(), this.protocolo);

		// Envia o Objeto Json referente ao novo processo para ser executado
		// atraves de um servico Rest
		this.activitiServico.iniciarInstanciaProcesso(json);

//		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
//				"Sucesso", "Solicitação de treinamento realizada!");
//		FacesContext.getCurrentInstance().addMessage("msg", message);
		
		RequestContext request = RequestContext.getCurrentInstance();
		request.execute("sucessoDialog.show()");
		
		this.variaveisTreinamento = new VariaveisTreinamento();
	}

	public void cancelar() {

		Map<String, String> variaveis = new HashMap<String, String>();
		variaveis.put("parecer", this.getMotivoCancelamento());
		variaveis.put("acaoParecer", "PROCESSO CONCELADO");

		String json = JsonUtil.converterVariaveisToJson(variaveis);
		this.activitiServico
				.atualizarVariaveis(this.processoInstancia.getId(), json);

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
				.getHistoricoTarefasPorVariaveis( null, null, null, null,
						this.processoInstancia.getId());


		for (TarefaInstancia tarefaInstancia : this.tarefaInstancias) {
			this.variaveisTreinamento = new VariaveisTreinamento();
			
			this.variaveisTreinamento
					.converterListaVariaveisParaVariaveisProcesso(tarefaInstancia
							.getVariables());
			
			this.variaveisTarefa = variaveisTarefaServico.findById(new Long(tarefaInstancia.getId()));
			
			// Atualiza as variaveis da tarefa com os dados inseridos no banco
			if (this.variaveisTarefa != null)
				this.variaveisTreinamento.converterVariaveisTarefa(this.variaveisTarefa);

			if (tarefaInstancia.getEndTime() != null) {
				this.variaveisTreinamento.setSituacao("FINALIZADO");
			} else {
				this.variaveisTreinamento.setSituacao("EM ANDAMENTO");
			}
			tarefaInstancia.setVariaveisProcesso(this.variaveisTreinamento);
		}

		paginaCentralControladorBean
				.setPaginaCentral(this.TELA_DETALHE);

	}

	public void telaPesquisa() {
		paginaCentralControladorBean
				.setPaginaCentral(this.TELA_PESQUISA);

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
		VariaveisTreinamento variaveisProcesso = null;
		this.lista = new ArrayList<ProcessoInstancia>();

		listaResultado = activitiServico.getHistoricoProcessosFiltroVariaveis(
				new HashMap<String, Object>(), "TODOS");

		// Map<String, Object> var = this.filtroVariaveis();
		// listaResultado = activitiServico
		// .getHistoricoProcessosFiltroVariaveis(var, this.status);
		// }

		for (ProcessoInstancia pInstancia : listaResultado) {
			variaveisProcesso = new VariaveisTreinamento();
			variaveisProcesso
					.converterListaVariaveisParaVariaveisProcesso(pInstancia
							.getVariables());

			pInstancia.setVariaveisProcesso(variaveisProcesso);
			this.lista.add(pInstancia);
		}

	}
	
	public void detalheTarefa(TarefaInstancia tarefa) {
		this.tarefa = tarefa;
		this.variaveisTarefa = variaveisTarefaServico.findById(Long.valueOf(tarefa.getId()));
		
		if (this.variaveisTarefa != null) {
			((VariaveisTreinamento)this.tarefa.getVariaveisProcesso()).setAcao(this.variaveisTarefa.getAcao());
			((VariaveisTreinamento)this.tarefa.getVariaveisProcesso()).setParecer(this.variaveisTarefa.getParecer());
		}
		this.paginaCentralControladorBean.setPaginaCentral(this.TELA_DETALHE_TAREFA);
	}
	
	
	public void abrirImagemProcesso(ProcessoInstancia entity) {

		System.out.println("processoInstancia.getId()" + processoInstancia.getId());
		this.processoInstancia = entity;
		setImagem(activitiServico.getProcessoDiagrama(processoInstancia
				.getId()));

	}
	
	public void telaDetalhe() {
		this.paginaCentralControladorBean.setPaginaCentral(this.TELA_DETALHE);
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

	public AlfrescoServico getAlfrescoServico() {
		return alfrescoServico;
	}

	public void setAlfrescoServico(AlfrescoServico alfrescoServico) {
		this.alfrescoServico = alfrescoServico;
	}

	public Usuario getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(Usuario coordenador) {
		this.coordenador = coordenador;
	}

	public Usuario getDiretor() {
		return diretor;
	}

	public void setDiretor(Usuario diretor) {
		this.diretor = diretor;
	}

	public Usuario getRH() {
		return RH;
	}

	public void setRH(Usuario rH) {
		RH = rH;
	}

	public String getSequencial() {
		return sequencial;
	}

	public void setSequencial(String sequencial) {
		this.sequencial = sequencial;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
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

	public Usuario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Usuario funcionario) {
		this.funcionario = funcionario;
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
			builder.append(((Usuario) item).getFirstName()).append("<br />");
		}

		FacesMessage msg = new FacesMessage();
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		msg.setSummary("Items Transferred");
		msg.setDetail(builder.toString());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

//	public String getDataInicial() {
//		return dataInicial;
//	}
//
//	public void setDataInicial(String dataInicial) {
//		this.dataInicial = dataInicial;
//	}
//
//	public String getDataFinal() {
//		return dataFinal;
//	}
//
//	public void setDataFinal(String dataFinal) {
//		this.dataFinal = dataFinal;
//	}

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
	
}