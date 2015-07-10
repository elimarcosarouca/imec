package br.fucapi.ads.modelo.controlador;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.fucapi.ads.modelo.dominio.Alerta;
import br.fucapi.ads.modelo.dominio.Unidade;
import br.fucapi.ads.modelo.enumerated.Constants;
import br.fucapi.ads.modelo.enumerated.StatusProcesso;
import br.fucapi.ads.modelo.servico.AlertaServico;
import br.fucapi.ads.modelo.servico.Servico;
import br.fucapi.ads.modelo.servico.UnidadeServico;
import br.fucapi.ads.modelo.utils.GeralUtils;
import br.fucapi.bpms.activiti.servico.ActivitiServico;
import br.fucapi.bpms.activiti.util.JsonUtil;
import br.fucapi.bpms.alfresco.dominio.Usuario;
import br.fucapi.bpms.alfresco.servico.AlfrescoServico;

@ManagedBean
@SessionScoped
public class AlertaControlador extends ControladorGenerico<Alerta> {

	private static final long serialVersionUID = -6832271293709421841L;

	private Usuario usuario = new Usuario();

	private List<Usuario> usuarios;

	private List<Unidade> unidades;

	private int notificarVencimento;

	@ManagedProperty(value = "#{alfrescoServicoImpl}")
	private AlfrescoServico alfrescoServico;

	@ManagedProperty(value = "#{alertaServicoImpl}")
	private AlertaServico servico;

	@ManagedProperty(value = "#{unidadeServicoImpl}")
	private UnidadeServico unidadeServico;

	@ManagedProperty(value = "#{activitiServicoImpl}")
	private ActivitiServico activitiServico;

	private String nomeRelatorio = "alerta.jasper";

	public String telaPesquisa() {
		super.setup();
		return redirecionar(PESQUISA);
	}

	public String redirecionar(String page) {
		try {

			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			HttpServletRequest request = (HttpServletRequest) context
					.getRequest();

			String fullUrl = request.getRequestURL().toString();
			String path = FacesContext.getCurrentInstance()
					.getExternalContext().getRealPath("");
			String url = path + Constants.BARRA + "paginas/alerta"
					+ Constants.BARRA + page + Constants.EXTENSION
					+ Constants.REDIRECT;

			context.redirect(url);
			FacesContext.getCurrentInstance().responseComplete();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return page;
	}

	@Override
	protected String getNomeRelatorioJasper() {
		return this.nomeRelatorio;
	}

	@Override
	public void pesquisar() {
		this.pesquisa.setDataAlerta(new Date());
		this.pesquisa.setStatus(StatusProcesso.ATIVO);
		super.pesquisar();
	}

	public String showModalRevalidar(Alerta alerta) {
		this.entidade = alerta;
		this.notificarVencimento = 30;
		return null;
	}

	public String salvarRevalidacao() {
		this.entidade.setDataCadastro(new Date());
		this.entidade.setDataAlerta(GeralUtils.gerarDataNotificacao(
				this.entidade.getDataVencimento(), this.notificarVencimento));

		this.servico.merge(this.entidade);
		this.pesquisar();

		return this.redirect(PESQUISA);
	}

	public void atualizarVencimento() {
		Map<String, Object> variaveis = new HashMap<String, Object>();

		variaveis.put("dataVencimento", this.entidade.getDataVencimento());
		variaveis.put("dataNotificacao", this.entidade.getDataAlerta());

		String json = JsonUtil.converterVariaveisToJson(variaveis);
		this.activitiServico.atualizarVariaveis(
				this.entidade.getProcessInstanceId(), json);
	}

	@Override
	protected void init() {
		this.usuarios = alfrescoServico.getUsuarios();
		this.unidades = unidadeServico.listAll();
	}

	@Override
	public String getTituloRelatorio() {
		return "REGISTRO DE TREINAMENTO";
	}

	@Override
	protected Servico<Alerta, Long> getService() {
		return servico;
	}

	public AlertaServico getServico() {
		return servico;
	}

	public void setServico(AlertaServico servico) {
		this.servico = servico;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
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

	public int getNotificarVencimento() {
		return notificarVencimento;
	}

	public void setNotificarVencimento(int notificarVencimento) {
		this.notificarVencimento = notificarVencimento;
	}

	public ActivitiServico getActivitiServico() {
		return activitiServico;
	}

	public void setActivitiServico(ActivitiServico activitiServico) {
		this.activitiServico = activitiServico;
	}

	public List<Unidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidade> unidades) {
		this.unidades = unidades;
	}

	public UnidadeServico getUnidadeServico() {
		return unidadeServico;
	}

	public void setUnidadeServico(UnidadeServico unidadeServico) {
		this.unidadeServico = unidadeServico;
	}

}