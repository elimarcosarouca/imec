package br.com.ss.controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.ss.model.entidade.Legislacao;
import br.com.ss.model.servico.LegislacaoServico;
import br.com.ss.model.servico.Servico;

@ManagedBean
@SessionScoped
public class LegislacaoControlador extends ControladorGenerico<Legislacao> {

	private static final long serialVersionUID = -6832271293709421841L;

	@ManagedProperty(value = "#{LegislacaoServicoImpl}")
	private LegislacaoServico servico;

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
	protected Servico<Legislacao, Long> getService() {
		return this.servico;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

	public LegislacaoServico getServico() {
		return servico;
	}

	public void setServico(LegislacaoServico servico) {
		this.servico = servico;
	}
}