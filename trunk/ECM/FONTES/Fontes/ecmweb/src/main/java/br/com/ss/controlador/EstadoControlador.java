package br.com.ss.controlador;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import br.com.ss.enumerado.UF;
import br.com.ss.model.entidade.Estado;
import br.com.ss.model.servico.EstadoServico;
import br.com.ss.model.servico.Servico;

@ManagedBean
@SessionScoped
public class EstadoControlador extends ControladorGenerico<Estado> {

	private static final long serialVersionUID = -6832271293709421841L;

	@ManagedProperty(value = "#{estadoServicoImpl}")
	private EstadoServico servico;

	private List<SelectItem> ufList;

	private UF ufSelecionada;

	private String nomeRelatorio = "estado.jasper";

	@Override
	protected void init() {
		ufList = new ArrayList<SelectItem>();
		for (UF sm : UF.values()) {
			ufList.add(new SelectItem(sm, sm.getDescricao()));
		}
		pesquisar();
	}

	@Override
	protected String getNomeRelatorioJasper() {
		return this.nomeRelatorio;
	}

	@Override
	public String getTituloRelatorio() {
		return "RELATÓRIO DE CURSO";
	}

	@Override
	protected Servico<Estado, Long> getService() {
		return this.servico;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

	public EstadoServico getServico() {
		return servico;
	}

	public void setServico(EstadoServico servico) {
		this.servico = servico;
	}

	public List<SelectItem> getUfList() {
		return ufList;
	}

	public void setUfList(List<SelectItem> ufList) {
		this.ufList = ufList;
	}

	public UF getUfSelecionada() {
		return ufSelecionada;
	}

	public void setUfSelecionada(UF ufSelecionada) {
		this.ufSelecionada = ufSelecionada;
	}

}