package br.fucapi.ads.modelo.controlador;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.fucapi.ads.modelo.dominio.Alerta;
import br.fucapi.ads.modelo.enumerated.StatusProcesso;
import br.fucapi.ads.modelo.servico.AlertaServico;
import br.fucapi.ads.modelo.servico.Servico;
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

	private int notificarVencimento;

	@ManagedProperty(value = "#{alfrescoServicoImpl}")
	private AlfrescoServico alfrescoServico;

	@ManagedProperty(value = "#{alertaServicoImpl}")
	private AlertaServico servico;

	@ManagedProperty(value = "#{activitiServicoImpl}")
	private ActivitiServico activitiServico;

	private String nomeRelatorio = "alerta.jasper";

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

}