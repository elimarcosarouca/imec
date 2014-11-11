package br.com.ss.controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.ss.model.entidade.PalavraChave;
import br.com.ss.model.servico.PalavraChaveServico;
import br.com.ss.model.servico.Servico;

@ManagedBean
@SessionScoped
public class PalavraChaveControlador extends
		ControladorGenerico<PalavraChave> {

	private static final long serialVersionUID = -6832271293709421841L;

	@ManagedProperty(value = "#{palavraChaveServicoImpl}")
	private PalavraChaveServico servico;

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
	protected Servico<PalavraChave, Long> getService() {
		return this.servico;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

	public PalavraChaveServico getServico() {
		return servico;
	}

	public void setServico(PalavraChaveServico servico) {
		this.servico = servico;
	}
}