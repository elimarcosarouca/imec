package br.com.ss.controlador;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import br.com.ss.enumerado.UF;
import br.com.ss.model.entidade.Usuario;
import br.com.ss.model.servico.Servico;
import br.com.ss.model.servico.UsuarioServico;

@ManagedBean
@SessionScoped
public class UsuarioControlador extends ControladorGenerico<Usuario> {

	private static final long serialVersionUID = -6832271293709421841L;

	@ManagedProperty(value = "#{usuarioServicoImpl}")
	private UsuarioServico servico;

	private List<SelectItem> ufList;

	private UF ufSelecionada;

	private String nomeRelatorio = "usuario.jasper";

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
	protected Servico<Usuario, Long> getService() {
		return this.servico;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

	public UsuarioServico getServico() {
		return servico;
	}

	public void setServico(UsuarioServico servico) {
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