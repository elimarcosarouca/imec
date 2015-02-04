package br.fucapi.ads.modelo.controlador;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.fucapi.ads.modelo.dominio.Funcionario;
import br.fucapi.ads.modelo.dominio.PostoCopia;
import br.fucapi.ads.modelo.servico.FuncionarioServico;
import br.fucapi.ads.modelo.servico.PostoCopiaServico;
import br.fucapi.ads.modelo.servico.Servico;

@ManagedBean
@SessionScoped
public class FuncionarioControlador extends ControladorGenerico<Funcionario> {

	private static final long serialVersionUID = -6832271293709421841L;

	private List<PostoCopia> postosCopia;

	@ManagedProperty(value = "#{funcionarioServicoImpl}")
	private FuncionarioServico servico;

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
	protected Servico<Funcionario, Long> getService() {
		return servico;
	}

	public FuncionarioServico getServico() {
		return servico;
	}

	public void setServico(FuncionarioServico servico) {
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