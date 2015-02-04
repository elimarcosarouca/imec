package br.fucapi.ads.modelo.controlador;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.fucapi.ads.modelo.dominio.PostoCopia;
import br.fucapi.ads.modelo.dominio.Setor;
import br.fucapi.ads.modelo.servico.PostoCopiaServico;
import br.fucapi.ads.modelo.servico.Servico;
import br.fucapi.ads.modelo.servico.SetorServico;
import br.fucapi.bpms.alfresco.dominio.Usuario;
import br.fucapi.bpms.alfresco.servico.AlfrescoServico;

@ManagedBean
@SessionScoped
public class PostoCopiaControlador extends ControladorGenerico<PostoCopia> {

	private static final long serialVersionUID = -6832271293709421841L;

	private List<Setor> setores;
	
	private List<Usuario> usuarios;

	@ManagedProperty(value = "#{postoCopiaServicoImpl}")
	private PostoCopiaServico servico;

	@ManagedProperty(value = "#{setorServicoImpl}")
	private SetorServico setorServico;

	@ManagedProperty(value = "#{alfrescoServicoImpl}")
	private AlfrescoServico alfrescoServico;
	
	private String nomeRelatorio = "setor.jasper";
	
	public void init(){
		this.usuarios = alfrescoServico.getUsuarios();
	}

	/**
	 * Lista os Responsaveis - para a lista do auto-complete da tela de
	 * pesquisa.
	 */
	public List<Setor> listarSetor(String nome) {
		return this.setores = setorServico.pesquisar(new Setor(nome));
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
	protected Servico<PostoCopia, Long> getService() {
		return servico;
	}

	public PostoCopiaServico getServico() {
		return servico;
	}

	public void setServico(PostoCopiaServico servico) {
		this.servico = servico;
	}

	public void setNomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

	public List<Setor> getSetores() {
		return setores;
	}

	public void setSetores(List<Setor> setores) {
		this.setores = setores;
	}

	public SetorServico getSetorServico() {
		return setorServico;
	}

	public void setSetorServico(SetorServico setorServico) {
		this.setorServico = setorServico;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public AlfrescoServico getAlfrescoServico() {
		return alfrescoServico;
	}

	public void setAlfrescoServico(AlfrescoServico alfrescoServico) {
		this.alfrescoServico = alfrescoServico;
	}

}