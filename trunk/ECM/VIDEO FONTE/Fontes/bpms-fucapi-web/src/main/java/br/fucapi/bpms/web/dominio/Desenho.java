package br.fucapi.bpms.web.dominio;

public class Desenho extends Arquivo {

	private static final long serialVersionUID = 1L;

	private TipoModificacao tipoModificacao;
	private Situacao situacaoVersaoAtual;
	private FamiliaProduto familia;
	private TipoDesenho tipoDesenho;

	private String grupo;
	private String complemento;
	private String revisaoEdicao;
	private String uuidSubstituido;
	private String nomeArquivoSubstituido;

	private String protocoloAnterior;
	private String observacaoDesenhoAnterior;
	private Situacao situacaoVersaoAnterior;

	private int id;

	public TipoModificacao getTipoModificacao() {
		return tipoModificacao;
	}

	public void setTipoModificacao(TipoModificacao tipoModificacao) {
		this.tipoModificacao = tipoModificacao;
	}

	public Situacao getSituacaoVersaoAtual() {
		return situacaoVersaoAtual;
	}

	public void setSituacaoVersaoAtual(Situacao situacaoVersaoAtual) {
		this.situacaoVersaoAtual = situacaoVersaoAtual;
	}

	public FamiliaProduto getFamilia() {
		return familia;
	}

	public void setFamilia(FamiliaProduto familia) {
		this.familia = familia;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getRevisaoEdicao() {
		return revisaoEdicao;
	}

	public void setRevisaoEdicao(String revisaoEdicao) {
		this.revisaoEdicao = revisaoEdicao;
	}

	public String getUuidSubstituido() {
		return uuidSubstituido;
	}

	public void setUuidSubstituido(String uuidSubstituido) {
		this.uuidSubstituido = uuidSubstituido;
	}

	public String getProtocoloAnterior() {
		return protocoloAnterior;
	}

	public void setProtocoloAnterior(String protocoloAnterior) {
		this.protocoloAnterior = protocoloAnterior;
	}

	public Situacao getSituacaoVersaoAnterior() {
		return situacaoVersaoAnterior;
	}

	public void setSituacaoVersaoAnterior(Situacao situacaoVersaoAnterior) {
		this.situacaoVersaoAnterior = situacaoVersaoAnterior;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getObservacaoDesenhoAnterior() {
		return observacaoDesenhoAnterior;
	}

	public void setObservacaoDesenhoAnterior(String observacaoDesenhoAnterior) {
		this.observacaoDesenhoAnterior = observacaoDesenhoAnterior;
	}

	public TipoDesenho getTipoDesenho() {
		return tipoDesenho;
	}

	public void setTipoDesenho(TipoDesenho tipoDesenho) {
		this.tipoDesenho = tipoDesenho;
	}

	public String getNomeArquivoSubstituido() {
		return nomeArquivoSubstituido;
	}

	public void setNomeArquivoSubstituido(String nomeArquivoSubstituido) {
		this.nomeArquivoSubstituido = nomeArquivoSubstituido;
	}

}
