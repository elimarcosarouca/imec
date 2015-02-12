package br.fucapi.ads.modelo.controlador;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.fucapi.ads.modelo.dominio.Colaborador;
import br.fucapi.ads.modelo.dominio.PostoCopia;
import br.fucapi.ads.modelo.servico.ColaboradorServico;
import br.fucapi.ads.modelo.servico.PostoCopiaServico;
import br.fucapi.ads.modelo.servico.Servico;

@ManagedBean
@SessionScoped
public class ColaboradorControlador extends ControladorGenerico<Colaborador> {

	private static final long serialVersionUID = -6832271293709421841L;

	private List<PostoCopia> postosCopia;

	@ManagedProperty(value = "#{colaboradorServicoImpl}")
	private ColaboradorServico servico;

	@ManagedProperty(value = "#{postoCopiaServicoImpl}")
	private PostoCopiaServico postoCopiaServico;

	private String nomeRelatorio = "setor.jasper";

	/**
	 * Lista os Responsaveis - para a lista do auto-complete da tela de
	 * pesquisa.
	 */
	public List<PostoCopia> listarPostoCopia(String nome) {
		return postoCopiaServico.pesquisar(new PostoCopia(nome));
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
	protected Servico<Colaborador, Long> getService() {
		return servico;
	}

	public ColaboradorServico getServico() {
		return servico;
	}

	public void setServico(ColaboradorServico servico) {
		this.servico = servico;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

	public List<PostoCopia> getPostosCopia() {
		return postosCopia;
	}

	public void setPostosCopia(List<PostoCopia> postosCopia) {
		this.postosCopia = postosCopia;
	}

	public PostoCopiaServico getPostoCopiaServico() {
		return postoCopiaServico;
	}

	public void setPostoCopiaServico(PostoCopiaServico postoCopiaServico) {
		this.postoCopiaServico = postoCopiaServico;
	}

}