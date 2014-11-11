package br.com.ss.controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.ss.model.entidade.Filial;
import br.com.ss.model.servico.FilialServico;
import br.com.ss.model.servico.Servico;

@ManagedBean
@SessionScoped
public class FilialControlador extends
		ControladorGenerico<Filial> {

	private static final long serialVersionUID = -6832271293709421841L;

	@ManagedProperty(value = "#{filialServicoImpl}")
	private FilialServico servico;

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
	protected Servico<Filial, Long> getService() {
		return this.servico;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

	public FilialServico getServico() {
		return servico;
	}

	public void setServico(FilialServico servico) {
		this.servico = servico;
	}
}