package br.com.ss.controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.com.ss.model.entidade.PostoCopia;
import br.com.ss.model.servico.PostoCopiaServico;
import br.com.ss.model.servico.Servico;

@ManagedBean
@SessionScoped
public class PostoCopiaControlador extends ControladorGenerico<PostoCopia> {

	private static final long serialVersionUID = -6832271293709421841L;

	@ManagedProperty(value = "#{postoCopiaServicoImpl}")
	private PostoCopiaServico servico;

	private String nomeRelatorio = "empresa.jasper";

	@Override
	protected String getNomeRelatorioJasper() {
		return this.nomeRelatorio;
	}

	@Override
	public String getTituloRelatorio() {
		return "RELATÓRIO DE CURSO";
	}

	@Override
	protected Servico<PostoCopia, Long> getService() {
		return this.servico;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

	public PostoCopiaServico getServico() {
		return servico;
	}

	public void setServico(PostoCopiaServico servico) {
		this.servico = servico;
	}

}