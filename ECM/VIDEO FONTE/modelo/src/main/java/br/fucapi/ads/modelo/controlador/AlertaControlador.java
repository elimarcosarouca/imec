package br.fucapi.ads.modelo.controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.fucapi.ads.modelo.dominio.Alerta;
import br.fucapi.ads.modelo.servico.AlertaServico;
import br.fucapi.ads.modelo.servico.Servico;

@ManagedBean
@SessionScoped
public class AlertaControlador extends ControladorGenerico<Alerta> {

	private static final long serialVersionUID = -6832271293709421841L;

	@ManagedProperty(value = "#{alertaServicoImpl}")
	private AlertaServico servico;

	private String nomeRelatorio = "alerta.jasper";

	@Override
	protected String getNomeRelatorioJasper() {
		return this.nomeRelatorio;
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

}