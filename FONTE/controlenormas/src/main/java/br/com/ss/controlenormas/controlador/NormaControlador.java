package br.com.ss.controlenormas.controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.ss.controlenormas.dominio.Norma;
import br.com.ss.controlenormas.servico.NormaServico;
import br.com.ss.core.seguranca.servico.IService;
import br.com.ss.core.web.controlador.ControladorGenerico;

@ManagedBean
@SessionScoped
public class NormaControlador extends ControladorGenerico<Norma> {

	private static final long serialVersionUID = -6832271293709421841L;

	@ManagedProperty(value = "#{normaServicoImpl}")
	private NormaServico servico;

	private String nomeRelatorio = "norma.jasper";

	@Override
	protected String getNomeRelatorioJasper() {
		return this.nomeRelatorio;
	}

	@Override
	public String getTituloRelatorio() {
		return "RELATÓRIO DE NORMA";
	}

	@Override
	protected IService<Norma, Long> getService() {
		return servico;
	}

	public NormaServico getServico() {
		return servico;
	}

	public void setServico(NormaServico servico) {
		this.servico = servico;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

}