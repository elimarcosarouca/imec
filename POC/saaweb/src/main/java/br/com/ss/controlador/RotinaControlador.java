package br.com.ss.controlador;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import br.com.ss.model.entidade.Rotina;
import br.com.ss.model.servico.RotinaServico;
import br.com.ss.model.servico.Servico;

@ManagedBean
@SessionScoped
public class RotinaControlador extends ControladorGenerico<Rotina> {

	private static final long serialVersionUID = -6832271293709421841L;

	@ManagedProperty(value = "#{rotinaServicoImpl}")
	private RotinaServico servico;

	private List<SelectItem> statusRotinaList;

	private String nomeRelatorio = "rotina.jasper";

	@Override
	protected String getNomeRelatorioJasper() {
		return this.nomeRelatorio;
	}

	@Override
	public String getTituloRelatorio() {
		return "RELATÓRIO DE CURSO";
	}

	@Override
	protected Servico<Rotina, Long> getService() {
		return this.servico;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

	public RotinaServico getServico() {
		return servico;
	}

	public void setServico(RotinaServico servico) {
		this.servico = servico;
	}

	public List<SelectItem> getStatusRotinaList() {
		return statusRotinaList;
	}

	public void setStatusRotinaList(List<SelectItem> statusRotinaList) {
		this.statusRotinaList = statusRotinaList;
	}
}