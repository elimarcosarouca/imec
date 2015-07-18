package br.fucapi.ads.modelo.controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.fucapi.ads.modelo.dominio.Categoria;
import br.fucapi.ads.modelo.servico.CategoriaServico;
import br.fucapi.ads.modelo.servico.Servico;

@ManagedBean
@SessionScoped
public class CategoriaControlador extends ControladorGenerico<Categoria> {

	private static final long serialVersionUID = -6832271293709421841L;

	@ManagedProperty(value = "#{categoriaServicoImpl}")
	private CategoriaServico servico;

	private String nomeRelatorio = "tipoDocumento.jasper";
	
	public String telaPesquisa() {
		super.setup();
		return "/paginas/categoria/pesquisa.xhtml?faces-redirect=true";
	}

	@Override
	protected String getNomeRelatorioJasper() {
		return this.nomeRelatorio;
	}

	@Override
	public String getTituloRelatorio() {
		return "RELATÓRIO DE Tipo Documento";
	}

	@Override
	protected Servico<Categoria, Long> getService() {
		return servico;
	}

	public CategoriaServico getServico() {
		return servico;
	}

	public void setServico(CategoriaServico servico) {
		this.servico = servico;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

}