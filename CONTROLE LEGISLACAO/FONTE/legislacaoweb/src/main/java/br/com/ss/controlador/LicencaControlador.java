package br.com.ss.controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.ss.model.entidade.Licenca;
import br.com.ss.model.servico.LicencaServico;
import br.com.ss.model.servico.Servico;

@ManagedBean
@SessionScoped
public class LicencaControlador extends ControladorGenerico<Licenca> {

	private static final long serialVersionUID = -6832271293709421841L;

	@ManagedProperty(value = "#{LicencaServicoImpl}")
	private LicencaServico servico;

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
	protected Servico<Licenca, Long> getService() {
		return this.servico;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

	public LicencaServico getServico() {
		return servico;
	}

	public void setServico(LicencaServico servico) {
		this.servico = servico;
	}
}