package br.fucapi.ads.modelo.controlador;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.fucapi.ads.modelo.dominio.Setor;
import br.fucapi.ads.modelo.dominio.Unidade;
import br.fucapi.ads.modelo.servico.Servico;
import br.fucapi.ads.modelo.servico.SetorServico;
import br.fucapi.ads.modelo.servico.UnidadeServico;

@ManagedBean
@SessionScoped
public class SetorControlador extends
		ControladorGenerico<Setor> {

	private static final long serialVersionUID = -6832271293709421841L;
	
	private List<Unidade> unidades;

	@ManagedProperty(value = "#{setorServicoImpl}")
	private SetorServico servico;
	
	@ManagedProperty(value = "#{unidadeServicoImpl}")
	private UnidadeServico unidadeServico;

	private String nomeRelatorio = "setor.jasper";
	
	public String telaPesquisa() {
		super.setup();
		return "/paginas/setor/pesquisa.xhtml?faces-redirect=true";
	}
	
	@Override
	public String novo(){
		this.unidades = unidadeServico.listAll();
		return super.novo();
		
	}
	
	/**
	 * Lista os Responsaveis - para a lista do auto-complete da tela de
	 * pesquisa.
	 */
	public List<Unidade> listarUnidade(String nome) {
		Unidade unidade = new Unidade();
		unidade.setNome(nome);
		this.unidades = unidadeServico.pesquisar(unidade);
		
		return this.unidades;
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
	protected Servico<Setor, Long> getService() {
		return servico;
	}

	public SetorServico getServico() {
		return servico;
	}

	public void setServico(SetorServico servico) {
		this.servico = servico;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

	public UnidadeServico getUnidadeServico() {
		return unidadeServico;
	}

	public void setUnidadeServico(UnidadeServico unidadeServico) {
		this.unidadeServico = unidadeServico;
	}

	public List<Unidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidade> unidades) {
		this.unidades = unidades;
	}

}