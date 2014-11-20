package br.com.ss.controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.ss.model.entidade.Unidade;
import br.com.ss.model.servico.Servico;
import br.com.ss.model.servico.UnidadeServico;

@ManagedBean
@SessionScoped
public class UnidadeControlador extends ControladorGenerico<Unidade> {

	private static final long serialVersionUID = -6832271293709421841L;

	@ManagedProperty(value = "#{unidadeServicoImpl}")
	private UnidadeServico servico;

	private String nomeRelatorio = "empresa.jasper";

	@Override
	protected String getNomeRelatorioJasper() {
		return this.nomeRelatorio;
	}

	@Override
	public String getTituloRelatorio() {
		return "RELATÓRIO DE CURSO";
	}

	@Override
	protected Servico<Unidade, Long> getService() {
		return this.servico;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

	public UnidadeServico getServico() {
		return servico;
	}

	public void setServico(UnidadeServico servico) {
		this.servico = servico;
	}

}