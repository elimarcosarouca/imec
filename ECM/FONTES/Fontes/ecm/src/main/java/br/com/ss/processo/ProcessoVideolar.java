package br.com.ss.processo;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.model.UploadedFile;

import br.com.ss.model.entidade.PostoCopia;
import br.com.ss.model.entidade.Setor;
import br.com.ss.model.entidade.TipoDocumento;
import br.com.ss.model.entidade.Unidade;

public class ProcessoVideolar implements Serializable {

	private static final long serialVersionUID = -1524955302977434501L;

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
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

}