package br.fucapi.ads.modelo.controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.fucapi.ads.modelo.dominio.Unidade;
import br.fucapi.ads.modelo.servico.Servico;
import br.fucapi.ads.modelo.servico.UnidadeServico;

@ManagedBean
@SessionScoped
public class UnidadeControlador extends
		ControladorGenerico<Unidade> {

	private static final long serialVersionUID = -6832271293709421841L;

	@ManagedProperty(value = "#{unidadeServicoImpl}")
	private UnidadeServico servico;

	private String nomeRelatorio = "unidade.jasper";
	
	public String telaPesquisa() {
		super.setup();
		return "/paginas/unidade/pesquisa.xhtml?faces-redirect=true";
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
	protected Servico<Unidade, Long> getService() {
		return servico;
	}

	public UnidadeServico getServico() {
		return servico;
	}

	public void setServico(UnidadeServico servico) {
		this.servico = servico;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

}