package br.fucapi.ads.modelo.controlador;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.springframework.security.core.context.SecurityContextHolder;

import br.fucapi.ads.modelo.dominio.UsuarioTarefa;	
import br.fucapi.ads.modelo.dominio.VariaveisTarefa;
import br.fucapi.ads.modelo.dominio.VariaveisTreinamento;
import br.fucapi.ads.modelo.servico.UsuarioTarefaServico;
import br.fucapi.ads.modelo.servico.VariaveisTarefaServico;
import br.fucapi.bpms.activiti.dominio.ProcessoDefinicao;
import br.fucapi.bpms.activiti.dominio.TarefaInstancia;
import br.fucapi.bpms.activiti.servico.ActivitiServico;
import br.fucapi.bpms.activiti.util.DataUtil;
import br.fucapi.bpms.alfresco.dominio.Usuario;
import br.fucapi.bpms.alfresco.servico.AlfrescoServico;

@ManagedBean
@SessionScoped
public class ConsultaTarefaControlador implements Serializable {

	private static final long serialVersionUID = 4559845634690927048L;

	private Date dataInicial;
	private Date dataFinal;

	private String status;

	private Usuario usuario = new Usuario();
	
	private Usuario usuarioExecutor = new Usuario();
	
	private Usuario usuarioLogado;

	private List<SelectItem> listaStatus;

	private List<Usuario> listaUsuarios = new ArrayList<Usuario>();

	private TarefaInstancia entityDetalhe;

	private List<TarefaInstancia> listaTarefa;
	
	private TarefaInstancia tarefaAlteracao;

	@ManagedProperty(value = "#{activitiServicoImpl}")
	private ActivitiServico activitiServico;
	
	@ManagedProperty(value = "#{messengerProperties}")
	private Properties messengerProperties;

	@ManagedProperty(value = "#{alfrescoServicoImpl}")
	private AlfrescoServico alfrescoServico;

	@ManagedProperty(value = "#{paginaCentralControladorBean}")
	private PaginaCentralControladorBean paginaCentralControladorBean;

	private VariaveisTreinamento variaveisSolicitacao = new VariaveisTreinamento();

	private List<ProcessoDefinicao> listaProcessosDefinicao;
	
	@ManagedProperty(value = "#{usuarioTarefaServicoImpl}")
	private UsuarioTarefaServico usuarioTarefaServico;
	
	@ManagedProperty(value = "#{emailControlador}")
	private EmailControlador emailControlador;
	
	@ManagedProperty(value = "#{variaveisTarefaServicoImpl}")
	private VariaveisTarefaServico variaveisTarefaServico;
	
	private final String DETALHE_CONSULTA_TAREFA = "paginas/tarefa/detalheconsultatarefa.xhtml";

	private final String TELA_CONSULTA_TAREFA = "paginas/tarefa/consultatarefas.xhtml";

	public void init() {

		this.listaTarefa = new ArrayList<TarefaInstancia>();
		
		this.usuarioLogado = (Usuario) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		
		if (this.usuarioLogado.getCapabilities().isAdmin()) {
			this.listaUsuarios = alfrescoServico.getUsuarios();
		} else {
			this.listaUsuarios = new ArrayList<Usuario>();
		}
		
		this.listaProcessosDefinicao = activitiServico.getProcessosDefinicaoPorQueryLastVersion();
	}

	public String pesquisar() throws ParseException {

		if (this.getDataInicial() != null
				&& !"".equals(this.getDataInicial())
				&& (this.getDataFinal() == null || "".equals(this
						.getDataFinal()))) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Data final deve ser informada!", ""));

		} else if (this.getDataFinal() != null
				&& !"".equals(this.getDataFinal())
				&& (this.getDataInicial() == null || "".equals(this
						.getDataInicial()))) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Data inicial deve ser informada!", ""));

		} else if (this.getDataFinal() != null
				&& !"".equals(this.getDataFinal())
				&& (this.getDataInicial() != null || !"".equals(this
						.getDataInicial()))) {

			if (this.dataFinal.compareTo(this.dataInicial) < 0) {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"Data inicial deve ser menor que a data final!",
										""));
				return "";
			} 
		}
			
				Map<String, Object> filtro = this.filtroVariaveis();

				String userNameTemp = null;
				Boolean statusTarefaTemp = null;

				if (!this.usuarioLogado.getCapabilities().isAdmin()) {
					userNameTemp = this.usuarioLogado.getUserName();
				} else if (this.usuario.getUserName() != null
						&& !"".equals(this.usuario.getUserName())) {
					userNameTemp = this.usuario.getUserName();
				}
		
				if ("PENDENTE".equals(this.status)) {
					statusTarefaTemp = true;
				} else if ("CONCLUÍDO".equals(this.status)) {
					statusTarefaTemp = false;
				}

				this.listaTarefa = activitiServico.getHistoricoTarefasPorVariaveis(filtro, userNameTemp, 
						this.variaveisSolicitacao.getTipoSolicitacao(), statusTarefaTemp, null);
				
				VariaveisTreinamento varTemp = null;
				for (TarefaInstancia tarefaInstancia : this.listaTarefa) {
					varTemp = new VariaveisTreinamento();
					varTemp.converterListaVariaveisParaVariaveisProcesso(tarefaInstancia
							.getVariables());
					
					// Solucao adotada para exibir no formulario o nome do processo sem o ID.
					if (tarefaInstancia.getProcessDefinitionId() != null) {
						String args[] = tarefaInstancia.getProcessDefinitionId().split(":");
						tarefaInstancia.setProcessDefinitionId(args[0]);
					}
					tarefaInstancia.setVariaveisProcesso(varTemp);
				}
				this.variaveisSolicitacao = new VariaveisTreinamento();
			
		return "";
	}

	private Map<String, Object> filtroVariaveis() {

		Map<String, Object> var = new HashMap<String, Object>();

		if (this.variaveisSolicitacao.getProtocolo() != null
				&& !this.variaveisSolicitacao.getProtocolo().equals("")) {
			var.put("protocolo", this.variaveisSolicitacao.getProtocolo());
		
		} else if (this.getDataInicial() != null
				&& !"".equals(this.getDataInicial())
				&& this.getDataFinal() != null
				&& !"".equals(this.getDataFinal())) {
			var.put("dataInicial", DataUtil.formatarData(this.getDataInicial().toString()));
			var.put("dataFinal",  DataUtil.formatarData(this.getDataFinal().toString()));
		}

		return var;
	}

	public void detalhe(TarefaInstancia tarefa) {
//		TODO
//		VariaveisTarefa variaveis = this.variaveisTarefaServico
//				.findById(new Long(tarefa.getId()));
		
		VariaveisTarefa variaveis = null;

		if (variaveis != null) {
			((VariaveisTreinamento) tarefa.getVariaveisProcesso())
					.setAcao(variaveis.getAcao());
			((VariaveisTreinamento) tarefa.getVariaveisProcesso())
					.setParecer((variaveis.getParecer() != null) ? variaveis
							.getParecer() : "");
		}

		this.entityDetalhe = tarefa;
		this.paginaCentralControladorBean
				.setPaginaCentral(this.DETALHE_CONSULTA_TAREFA);
	}

	public void alterarExecutor() throws ParseException {
		this.activitiServico.alterarExecutor(this.tarefaAlteracao.getId(),
				usuarioExecutor.getUserName());

		// Bloco responsavel por salvar log da alteracao no banco de dados
		UsuarioTarefa usuarioTarefa = new UsuarioTarefa();
		usuarioTarefa.setDataAlteracao(new Date());
		usuarioTarefa.setIdTarefa(this.tarefaAlteracao.getId());
		usuarioTarefa.setLoginAdm(this.usuarioLogado.getUserName());
		usuarioTarefa.setLoginAnterior(this.tarefaAlteracao.getAssignee());
		usuarioTarefa.setLoginNovo(this.usuarioExecutor.getUserName());

		this.usuarioTarefaServico.save(usuarioTarefa);

		this.pesquisar();

		try {
			this.tarefaAlteracao.setAssignee(usuarioExecutor.getUserName());
			this.emailControlador.enviarEmailNovoExecutor(this.tarefaAlteracao);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Erro",
					"Erro ao enviar email para o novo executor da tarefa. Entre em contato com o Administrador do sistema!");
			FacesContext.getCurrentInstance().addMessage("msg", message);
			return;
		}

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Sucesso", "Executor da tarefa alterado com sucesso!");
		FacesContext.getCurrentInstance().addMessage("msg", message);
	}

	public void telaConsultaTarefas() {
		paginaCentralControladorBean
				.setPaginaCentral(this.TELA_CONSULTA_TAREFA);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioExecutor() {
		return usuarioExecutor;
	}

	public void setUsuarioExecutor(Usuario usuarioExecutor) {
		this.usuarioExecutor = usuarioExecutor;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public List<SelectItem> getListaStatus() {
		return listaStatus;
	}

	public void setListaStatus(List<SelectItem> listaStatus) {
		this.listaStatus = listaStatus;
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

	public PaginaCentralControladorBean getPaginaCentralControladorBean() {
		return paginaCentralControladorBean;
	}

	public void setPaginaCentralControladorBean(
			PaginaCentralControladorBean paginaCentralControladorBean) {
		this.paginaCentralControladorBean = paginaCentralControladorBean;
	}

	public VariaveisTreinamento getVariaveisSolicitacao() {
		return variaveisSolicitacao;
	}

	public void setVariaveisSolicitacao(
			VariaveisTreinamento variaveisSolicitacao) {
		this.variaveisSolicitacao = variaveisSolicitacao;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public TarefaInstancia getEntityDetalhe() {
		return entityDetalhe;
	}

	public void setEntityDetalhe(TarefaInstancia entityDetalhe) {
		this.entityDetalhe = entityDetalhe;
	}

	public List<TarefaInstancia> getListaTarefa() {
		return listaTarefa;
	}

	public void setListaTarefa(List<TarefaInstancia> listaTarefa) {
		this.listaTarefa = listaTarefa;
	}

	public TarefaInstancia getTarefaAlteracao() {
		return tarefaAlteracao;
	}

	public void setTarefaAlteracao(TarefaInstancia tarefaAlteracao) {
		this.tarefaAlteracao = tarefaAlteracao;
	}

	public List<ProcessoDefinicao> getListaProcessosDefinicao() {
		return listaProcessosDefinicao;
	}

	public void setListaProcessosDefinicao(
			List<ProcessoDefinicao> listaProcessosDefinicao) {
		this.listaProcessosDefinicao = listaProcessosDefinicao;
	}

	public UsuarioTarefaServico getUsuarioTarefaServico() {
		return usuarioTarefaServico;
	}

	public void setUsuarioTarefaServico(
			UsuarioTarefaServico usuarioTarefaServico) {
		this.usuarioTarefaServico = usuarioTarefaServico;
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

	public Properties getMessengerProperties() {
		return messengerProperties;
	}

	public void setMessengerProperties(Properties messengerProperties) {
		this.messengerProperties = messengerProperties;
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

}
