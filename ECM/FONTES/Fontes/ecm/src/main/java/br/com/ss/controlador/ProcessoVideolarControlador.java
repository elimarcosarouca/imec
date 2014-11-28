package br.com.ss.controlador;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import br.com.ss.model.entidade.PostoCopia;
import br.com.ss.model.entidade.Setor;
import br.com.ss.model.entidade.TipoDocumento;
import br.com.ss.model.entidade.Unidade;
import br.com.ss.model.servico.PostoCopiaServico;
import br.com.ss.model.servico.SetorServico;
import br.com.ss.model.servico.TipoDocumentoServico;
import br.com.ss.model.servico.UnidadeServico;

@ManagedBean
@SessionScoped
public class ProcessoVideolarControlador implements Serializable {

	private static final long serialVersionUID = 5739593327217530162L;

	private String descricao;

	private UploadedFile file;

	private Unidade unidade;

	private TipoDocumento tipoDocumento;

	private Setor setor;

	private PostoCopia postoCopia;

	private List<Unidade> unidades;

	private List<TipoDocumento> tipoDocumentos;

	private List<Setor> setores;

	private List<PostoCopia> postoCopias;

	@ManagedProperty(value = "#{unidadeServicoImpl}")
	private UnidadeServico unidadeServico;

	@ManagedProperty(value = "#{tipoDocumentoServicoImpl}")
	private TipoDocumentoServico tipoDocumentoServico;

	@ManagedProperty(value = "#{setorServicoImpl}")
	private SetorServico setorServico;

	@ManagedProperty(value = "#{postoCopiaServicoImpl}")
	private PostoCopiaServico postoCopiaServico;

	@PostConstruct
	public void inti() {
		this.inicializarObjetos();
	}

	public void inicializarObjetos() {

		this.postoCopia = new PostoCopia();
		this.tipoDocumento = new TipoDocumento();
		this.setor = new Setor();
		this.unidade = new Unidade();
	}

	public void upload() {
		if (file != null) {
			FacesMessage message = new FacesMessage("Succesful",
					file.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	/**
	 * Lista os Setores - para a lista do auto-complete da tela de pesquisa.
	 */
	public List<Setor> listarSetor(String nome) {
		return this.setores = setorServico.pesquisar(new Setor(nome));
	}

	/**
	 * Lista os Unidade - para a lista do auto-complete da tela de pesquisa.
	 */
	public List<Unidade> listarUnidade(String nome) {
		return this.unidades = unidadeServico.pesquisar(new Unidade(nome));
	}

	/**
	 * Lista os Tipod - para a lista do auto-complete da tela de pesquisa.
	 */
	public List<TipoDocumento> listarTipoDocumento(String nome) {
		return this.tipoDocumentos = tipoDocumentoServico
				.pesquisar(new TipoDocumento(nome));
	}

	/**
	 * Lista os Tipod - para a lista do auto-complete da tela de pesquisa.
	 */
	public List<PostoCopia> listarPostoCopia(String nome) {
		return this.postoCopias = postoCopiaServico.pesquisar(new PostoCopia(
				nome));
	}

	public UnidadeServico getUnidadeServico() {
		return unidadeServico;
	}

	public void setUnidadeServico(UnidadeServico unidadeServico) {
		this.unidadeServico = unidadeServico;
	}

	public TipoDocumentoServico getTipoDocumentoServico() {
		return tipoDocumentoServico;
	}

	public void setTipoDocumentoServico(
			TipoDocumentoServico tipoDocumentoServico) {
		this.tipoDocumentoServico = tipoDocumentoServico;
	}

	public SetorServico getSetorServico() {
		return setorServico;
	}

	public void setSetorServico(SetorServico setorServico) {
		this.setorServico = setorServico;
	}

	public PostoCopiaServico getPostoCopiaServico() {
		return postoCopiaServico;
	}

	public void setPostoCopiaServico(PostoCopiaServico postoCopiaServico) {
		this.postoCopiaServico = postoCopiaServico;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public PostoCopia getPostoCopia() {
		return postoCopia;
	}

	public void setPostoCopia(PostoCopia postoCopia) {
		this.postoCopia = postoCopia;
	}

	public List<Unidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidade> unidades) {
		this.unidades = unidades;
	}

	public List<TipoDocumento> getTipoDocumentos() {
		return tipoDocumentos;
	}

	public void setTipoDocumentos(List<TipoDocumento> tipoDocumentos) {
		this.tipoDocumentos = tipoDocumentos;
	}

	public List<Setor> getSetores() {
		return setores;
	}

	public void setSetores(List<Setor> setores) {
		this.setores = setores;
	}

	public List<PostoCopia> getPostoCopias() {
		return postoCopias;
	}

	public void setPostoCopias(List<PostoCopia> postoCopias) {
		this.postoCopias = postoCopias;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}