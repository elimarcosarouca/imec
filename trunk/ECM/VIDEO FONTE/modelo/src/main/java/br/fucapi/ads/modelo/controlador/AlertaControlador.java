package br.fucapi.ads.modelo.controlador;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.fucapi.ads.modelo.dominio.Alerta;
import br.fucapi.ads.modelo.servico.AlertaServico;
import br.fucapi.ads.modelo.servico.Servico;
import br.fucapi.bpms.alfresco.dominio.Usuario;
import br.fucapi.bpms.alfresco.servico.AlfrescoServico;

@ManagedBean
@SessionScoped
public class AlertaControlador extends ControladorGenerico<Alerta> {

	private static final long serialVersionUID = -6832271293709421841L;

	private Usuario usuario = new Usuario();

	private List<Usuario> usuarios;

	@ManagedProperty(value = "#{alfrescoServicoImpl}")
	private AlfrescoServico alfrescoServico;

	@ManagedProperty(value = "#{alertaServicoImpl}")
	private AlertaServico servico;

	private String nomeRelatorio = "alerta.jasper";

	@Override
	protected String getNomeRelatorioJasper() {
		return this.nomeRelatorio;
	}

	@Override
	public void pesquisar() {

		this.pesquisa.setSolicitante(this.usuario.getUserName());
		super.pesquisar();
	}

	@Override
	protected void init() {
		this.pesquisa.setConcluida(false);
		this.usuarios = alfrescoServico.getUsuarios();
	}

	@Override
	public String getTituloRelatorio() {
		return "RELATÓRIO DE Tipo Documento";
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

}