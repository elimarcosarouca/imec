package br.fucapi.ads.modelo.controlador;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.springframework.security.core.context.SecurityContextHolder;

import br.fucapi.ads.modelo.dominio.VariaveisTreinamento;
import br.fucapi.ads.modelo.enumerated.StatusProcesso;
import br.fucapi.ads.modelo.ireport.RelatorioUtil;
import br.fucapi.bpms.activiti.dominio.ProcessoDefinicao;
import br.fucapi.bpms.activiti.dominio.ProcessoInstancia;
import br.fucapi.bpms.activiti.servico.ActivitiServico;
import br.fucapi.bpms.activiti.util.DataUtil;
import br.fucapi.bpms.activiti.util.JsonUtil;
import br.fucapi.bpms.alfresco.dominio.Usuario;
import br.fucapi.bpms.alfresco.servico.AlfrescoServico;

@ManagedBean
@SessionScoped
public class PesquisaSolicitacaoControlador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3618648495280619225L;
	
	private Date dataInicial;
	
	private Date dataFinal;

	private String status;

	private String imagem;

	private String motivoCancelamento;

	private Usuario usuarioLogado;

	private List<Usuario> usuarios;

	private Usuario usuarioSelecionado;

	private List<SelectItem> listaStatus;

	private ProcessoInstancia processoInstancia;

	private List<ProcessoInstancia> listaResultado;

	@ManagedProperty(value = "#{activitiServicoImpl}")
	private ActivitiServico activitiServico;

	@ManagedProperty(value = "#{alfrescoServicoImpl}")
	private AlfrescoServico alfrescoServico;

	@ManagedProperty(value = "#{usuarioControladorBean}")
	private UsuarioControladorBean usuarioControladorBean;

	@ManagedProperty(value = "#{bpmswebproperties}")
	private Properties bpmswebproperties;

	@ManagedProperty(value = "#{relatorioUtil}")
	private RelatorioUtil relatorioUtil;

	@ManagedProperty(value = "#{paginaCentralControladorBean}")
	private PaginaCentralControladorBean paginaCentralControladorBean;

	private List<ProcessoDefinicao> listaProcessosDefinicao;

	private VariaveisTreinamento variaveisSolicitacao = new VariaveisTreinamento();

	@PostConstruct
	public void init() {

		this.usuarioSelecionado = new Usuario();

		this.listaProcessosDefinicao = activitiServico
				.getProcessosDefinicaoPorQueryLastVersion();

		this.usuarioLogado = (Usuario) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		if (usuarioLogado.getCapabilities().isAdmin()) {
			this.usuarios = this.alfrescoServico.getUsuarios();
		}
	}

	public void initPesquisa() {

		this.listaResultado = new ArrayList<ProcessoInstancia>();

		this.usuarioLogado = ((Usuario) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal());

		// regrar implementada para desabilitar a listagem de usuario
		if (this.usuarioLogado.getCapabilities().isAdmin())
			this.usuarios = this.alfrescoServico.getUsuarios();

	}

	public void pesquisarPorParametro() throws ParseException {

		if (this.usuarioSelecionado != null
				&& this.usuarioSelecionado.getUserName() != ""
				&& this.usuarioSelecionado.getUserName() != null)
			this.variaveisSolicitacao.setSolicitante(this.usuarioSelecionado
					.getUserName());
		
		this.pesquisar();
	}
	
	public void pesquisarSolicitacaoUsuarioLogado() throws ParseException {
		this.variaveisSolicitacao.setSolicitante(this.usuarioLogado
					.getUserName());
		this.pesquisar();
	}

	public String pesquisar() throws ParseException {
		VariaveisTreinamento variaveisProcesso = null;
		this.listaResultado = new ArrayList<ProcessoInstancia>();
		
		
		if (this.getDataInicial() != null && !"".equals(this.getDataInicial()) 
				&& (this.getDataFinal() == null	|| "".equals(this.getDataFinal()))) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Data final deve ser informada!", ""));
			
		} else if (this.getDataFinal() != null && !"".equals(this.getDataFinal()) &&
				(this.getDataInicial() == null || "".equals(this.getDataInicial()))) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Data inicial deve ser informada!", ""));  
			
		} else if (this.getDataFinal() != null && !"".equals(this.getDataFinal()) &&
				(this.getDataInicial() != null || !"".equals(this.getDataInicial()))) {
			
			if(this.dataFinal.compareTo(this.dataInicial) < 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Data inicial deve ser menor que a data final!", ""));
			}
			else {
				this.variaveisSolicitacao.setDataInicial(DataUtil.formatarData(this.dataInicial.toString()));
				this.variaveisSolicitacao.setDataFinal(DataUtil.formatarData(this.dataFinal.toString()));
			}
			
		}
		
		Map<String, Object> var = this.filtroVariaveis();
//		this.listaResultado = activitiServico
//				.getHistoricoProcessosFiltroVariaveis(var, this.status);
		
		this.listaResultado = activitiServico
				.getHistoricoProcessos(var, this.status);
		

		for (ProcessoInstancia pInstancia : listaResultado) {
			variaveisProcesso = new VariaveisTreinamento();
			variaveisProcesso
					.converterListaVariaveisParaVariaveisProcesso(pInstancia
							.getVariables());

			pInstancia.setVariaveis(variaveisProcesso);
			
			pInstancia.getId();
		}
		variaveisProcesso = new VariaveisTreinamento();
		
		return "";
	}

	public String telaPesquisaAvancada() {
		
		this.variaveisSolicitacao = new VariaveisTreinamento();

		paginaCentralControladorBean
				.setPaginaCentral("paginas/solicitacao/treinamento/pesquisaavancada.xhtml");
		return "";
	}

	private Map<String, Object> filtroVariaveis() {

		Map<String, Object> var = new HashMap<String, Object>();
		// pegar o usuario logado

		// this.usuario =
		// (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (this.usuarioSelecionado.getUserName() != null
				&& !this.usuarioSelecionado.getUserName().equals("")) {
			var.put("solicitante", this.usuarioSelecionado.getUserName());

		}
		if (this.variaveisSolicitacao.getTreinamento() != null
				&& !this.variaveisSolicitacao.getTreinamento().equals("")) {
			var.put("treinamento", this.variaveisSolicitacao.getTreinamento());

		}
		
		if (this.variaveisSolicitacao.getProtocolo() != null
				&& !this.variaveisSolicitacao.getProtocolo().equals("")) {
			var.put("protocolo", this.variaveisSolicitacao.getProtocolo());

		} 
		
		if (this.variaveisSolicitacao.getTipoSolicitacao() != null
				&& !this.variaveisSolicitacao.getTipoSolicitacao().equals("")) {
			var.put("tipoSolicitacao",
					this.variaveisSolicitacao.getTipoSolicitacao());
		} 
		
		if (this.variaveisSolicitacao.getDataInicial() != null
				&& this.variaveisSolicitacao.getDataFinal() != null) {

			var.put("dataInicial", this.variaveisSolicitacao.getDataInicial());
			var.put("dataFinal", this.variaveisSolicitacao.getDataFinal());
		}
		
		if (this.variaveisSolicitacao.getSolicitante() != null
				&& !this.variaveisSolicitacao.getSolicitante().equals("")) {
			var.put("solicitante", this.variaveisSolicitacao.getSolicitante());
		}
				
		if (this.variaveisSolicitacao.getProtocolo() != null
				&& !this.variaveisSolicitacao.getProtocolo().equals("")) {
			var.put("protocolo", this.variaveisSolicitacao.getProtocolo());

		}

		if (this.variaveisSolicitacao.getCoordenador() != null
				&& !this.variaveisSolicitacao.getCoordenador().equals("")) {
			var.put("coordenador", this.variaveisSolicitacao.getCoordenador());

		}
		if (this.variaveisSolicitacao.getDiretor() != null
				&& !this.variaveisSolicitacao.getDiretor().equals("")) {
			var.put("diretor", this.variaveisSolicitacao.getDiretor());

		}
		if (this.variaveisSolicitacao.getFuncionario() != null
				&& !this.variaveisSolicitacao.getFuncionario().equals("")) {
			var.put("funcionario", this.variaveisSolicitacao.getFuncionario());

		}
		if (this.variaveisSolicitacao.getTreinamento() != null
				&& !this.variaveisSolicitacao.getTreinamento().equals("")) {
			var.put("treinamento", this.variaveisSolicitacao.getTreinamento());

		}
		if (this.variaveisSolicitacao.getCustoEstimado() != null
				&& !this.variaveisSolicitacao.getCustoEstimado().equals("")) {
			var.put("custoEstimado",
					this.variaveisSolicitacao.getCustoEstimado());
		}
		
		return var;
	}
	
	private Map<String, Object> filtroVariaveisPesquisaAvancada() {
		
		Map<String, Object> var = new HashMap<String, Object>();

		if (this.variaveisSolicitacao.getSolicitante() != null
				&& !this.variaveisSolicitacao.getSolicitante().equals("")) {
			var.put("solicitante", this.variaveisSolicitacao.getSolicitante());
		}
				
		if (this.variaveisSolicitacao.getProtocolo() != null
				&& !this.variaveisSolicitacao.getProtocolo().equals("")) {
			var.put("protocolo", this.variaveisSolicitacao.getProtocolo());

		}

		if (this.variaveisSolicitacao.getCoordenador() != null
				&& !this.variaveisSolicitacao.getCoordenador().equals("")) {
			var.put("coordenador", this.variaveisSolicitacao.getCoordenador());

		}
		if (this.variaveisSolicitacao.getDiretor() != null
				&& !this.variaveisSolicitacao.getDiretor().equals("")) {
			var.put("diretor", this.variaveisSolicitacao.getDiretor());

		}
		if (this.variaveisSolicitacao.getFuncionario() != null
				&& !this.variaveisSolicitacao.getFuncionario().equals("")) {
			var.put("funcionario", this.variaveisSolicitacao.getFuncionario());

		}
		if (this.variaveisSolicitacao.getTreinamento() != null
				&& !this.variaveisSolicitacao.getTreinamento().equals("")) {
			var.put("treinamento", this.variaveisSolicitacao.getTreinamento());

		}
		if (this.variaveisSolicitacao.getCustoEstimado() != null
				&& !this.variaveisSolicitacao.getCustoEstimado().equals("")) {
			var.put("custoEstimado",
					this.variaveisSolicitacao.getCustoEstimado());
		}
		
		return var;
		
	}

	public void cancelar() throws ParseException {

		Map<String, Object> variaveis = new HashMap<String, Object>();
		variaveis.put("motivoCancelamento", this.getMotivoCancelamento());
		variaveis.put("situacao", StatusProcesso.CANCELADO);
		variaveis.put("cancelado", "true");

		String json = JsonUtil.converterVariaveisToJson(variaveis);
		this.activitiServico.atualizarVariaveis(this.processoInstancia.getId(),
				json);

		this.activitiServico.cancelarProcessoInstaciado(processoInstancia
				.getId());

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Solicitação cancelada com sucesso!", "");
		FacesContext.getCurrentInstance().addMessage("msg", message);

		this.pesquisarSolicitacaoUsuarioLogado();

		this.telaPesquisa();
	}

	public void imprimir() {
		Map<String, Object> parametros = new HashMap<String, Object>();

		relatorioUtil.gerarRelatorioComDownload(this.listaResultado,
				parametros, "solicitacao.jasper");

	}

	public void telaPesquisa() {
		paginaCentralControladorBean
				.setPaginaCentral("paginas/solicitacao/pesquisa.xhtml");

	}

	public void abrirImagemProcesso(ProcessoInstancia entity) {

		System.out.println("processoInstancia.getId()" + entity.getId());
		setImagem(activitiServico.getProcessoDiagrama(entity.getId()));

	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
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

	public Properties getBpmswebproperties() {
		return bpmswebproperties;
	}

	public void setBpmswebproperties(Properties bpmswebproperties) {
		this.bpmswebproperties = bpmswebproperties;
	}

	public PaginaCentralControladorBean getPaginaCentralControladorBean() {
		return paginaCentralControladorBean;
	}

	public void setPaginaCentralControladorBean(
			PaginaCentralControladorBean paginaCentralControladorBean) {
		this.paginaCentralControladorBean = paginaCentralControladorBean;
	}

	public List<ProcessoDefinicao> getListaProcessosDefinicao() {
		return listaProcessosDefinicao;
	}

	public void setListaProcessosDefinicao(
			List<ProcessoDefinicao> listaProcessosDefinicao) {
		this.listaProcessosDefinicao = listaProcessosDefinicao;
	}

	public VariaveisTreinamento getVariaveisSolicitacao() {
		return variaveisSolicitacao;
	}

	public void setVariaveisSolicitacao(
			VariaveisTreinamento variaveisSolicitacao) {
		this.variaveisSolicitacao = variaveisSolicitacao;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public List<SelectItem> getListaStatus() {
		return listaStatus;
	}

	public void setListaStatus(List<SelectItem> listaStatus) {
		this.listaStatus = listaStatus;
	}

	public List<ProcessoInstancia> getListaResultado() {
		return listaResultado;
	}

	public void setListaResultado(List<ProcessoInstancia> listaResultado) {
		this.listaResultado = listaResultado;
	}

	public ProcessoInstancia getProcessoInstancia() {
		return processoInstancia;
	}

	public void setProcessoInstancia(ProcessoInstancia processoInstancia) {
		this.processoInstancia = processoInstancia;
	}

	public UsuarioControladorBean getUsuarioControladorBean() {
		return usuarioControladorBean;
	}

	public void setUsuarioControladorBean(
			UsuarioControladorBean usuarioControladorBean) {
		this.usuarioControladorBean = usuarioControladorBean;
	}

	public RelatorioUtil getRelatorioUtil() {
		return relatorioUtil;
	}

	public void setRelatorioUtil(RelatorioUtil relatorioUtil) {
		this.relatorioUtil = relatorioUtil;
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