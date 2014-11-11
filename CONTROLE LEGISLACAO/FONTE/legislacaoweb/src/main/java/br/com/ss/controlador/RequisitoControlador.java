package br.com.ss.controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.ss.model.entidade.Requisito;
import br.com.ss.model.servico.RequisitoServico;
import br.com.ss.model.servico.Servico;

@ManagedBean
@SessionScoped
public class RequisitoControlador extends
		ControladorGenerico<Requisito> {

	private static final long serialVersionUID = -6832271293709421841L;

	@ManagedProperty(value = "#{RequisitoServicoImpl}")
	private RequisitoServico servico;

	private String nomeRelatorio = "estado.jasper";

	@Override
	protected String getNomeRelatorioJasper() {
		return this.nomeRelatorio;
	}

	@Override
	public String getTituloRelatorio() {
		return "RELATÓRIO DE CURSO";
	}

	@Override
	protected Servico<Requisito, Long> getService() {
		return this.servico;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

	public RequisitoServico getServico() {
		return servico;
	}

	public void setServico(RequisitoServico servico) {
		this.servico = servico;
	}
}