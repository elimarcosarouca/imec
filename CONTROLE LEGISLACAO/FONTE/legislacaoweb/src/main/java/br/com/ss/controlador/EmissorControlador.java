package br.com.ss.controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.ss.model.entidade.Emissor;
import br.com.ss.model.servico.Servico;
import br.com.ss.model.servico.EmissorServico;

@ManagedBean
@SessionScoped
public class EmissorControlador extends
		ControladorGenerico<Emissor> {

	private static final long serialVersionUID = -6832271293709421841L;

	@ManagedProperty(value = "#{emissorServicoImpl}")
	private EmissorServico servico;

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
	protected Servico<Emissor, Long> getService() {
		return this.servico;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

	public EmissorServico getServico() {
		return servico;
	}

	public void setServico(EmissorServico servico) {
		this.servico = servico;
	}
}