package br.fucapi.bpms.web.dominio;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "siem_metadado_desenho")
public class MetadadoDesenho {

	@Id
	@Column(name = "meta_dese_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "meta_dese_businessKey", nullable = false, length = 15)
	private String businessKey;

	@Column(name = "meta_dese_situacaoVersaoAtual", nullable = false, length = 15)
	@Enumerated(EnumType.STRING)
	private Situacao situacaoVersaoAtual;

	@Column(name = "meta_dese_grupo", nullable = false, length = 30)
	private String grupo;

	@Column(name = "meta_dese_complemento", nullable = true, length = 30)
	private String complemento;

	@Column(name = "meta_dese_uuid", nullable = false, length = 200)
	private String uuid;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "meta_dado_ori_id")
	private Origem origem;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "meta_dado_tipo_docu_id")
	private TipoDocumento tipoDocumento;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "meta_dado_tipo_dese_id")
	private TipoDesenho tipoDesenho;

	@Column(name = "meta_dese_nomeArquivo", nullable = false, length = 60)
	private String nomeArquivo;

	@Column(name = "meta_dese_protocoloAnterior", length = 15)
	private String protocoloAnterior;

	@Column(name = "meta_dese_uuidSubstituido", length = 200)
	private String uuidSubstituido;

	@Column(name = "meta_dese_obs_des_anterior", length = 200)
	private String observacaoDesenhoAnterior;

	@Column(name = "meta_dese_situacaoVersaoAnterior", length = 15)
	@Enumerated(EnumType.STRING)
	private Situacao situacaoVersaoAnterior;

	@Column(name = "meta_dese_data_cadastro", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;

	@Column(name = "meta_dese_data_alteracao")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAlteracao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public Situacao getSituacaoVersaoAtual() {
		return situacaoVersaoAtual;
	}

	public void setSituacaoVersaoAtual(Situacao situacaoVersaoAtual) {
		this.situacaoVersaoAtual = situacaoVersaoAtual;
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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Origem getOrigem() {
		return origem;
	}

	public void setOrigem(Origem origem) {
		this.origem = origem;
	}

	public String getProtocoloAnterior() {
		return protocoloAnterior;
	}

	public void setProtocoloAnterior(String protocoloAnterior) {
		this.protocoloAnterior = protocoloAnterior;
	}

	public String getUuidSubstituido() {
		return uuidSubstituido;
	}

	public void setUuidSubstituido(String uuidSubstituido) {
		this.uuidSubstituido = uuidSubstituido;
	}

	public String getObservacaoDesenhoAnterior() {
		return observacaoDesenhoAnterior;
	}

	public void setObservacaoDesenhoAnterior(String observacaoDesenhoAnterior) {
		this.observacaoDesenhoAnterior = observacaoDesenhoAnterior;
	}

	public Situacao getSituacaoVersaoAnterior() {
		return situacaoVersaoAnterior;
	}

	public void setSituacaoVersaoAnterior(Situacao situacaoVersaoAnterior) {
		this.situacaoVersaoAnterior = situacaoVersaoAnterior;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public TipoDesenho getTipoDesenho() {
		return tipoDesenho;
	}

	public void setTipoDesenho(TipoDesenho tipoDesenho) {
		this.tipoDesenho = tipoDesenho;
	}

	public MetadadoDesenho(Desenho desenho, String businessKey, Origem origem,
			TipoDocumento tipoDocumento, TipoDesenho tipoDesenho) {
		this.complemento = desenho.getComplemento();
		this.grupo = desenho.getGrupo();
		this.situacaoVersaoAnterior = desenho.getSituacaoVersaoAnterior();
		this.situacaoVersaoAtual = desenho.getSituacaoVersaoAtual();
		this.uuid = desenho.getUuid();
		this.uuidSubstituido = desenho.getUuidSubstituido();
		this.nomeArquivo = desenho.getNomeArquivo();
		this.businessKey = businessKey;
		this.origem = origem;
		this.observacaoDesenhoAnterior = desenho.getObservacaoDesenhoAnterior();
		this.protocoloAnterior = desenho.getProtocoloAnterior();
		this.tipoDocumento = tipoDocumento;
		this.tipoDesenho = tipoDesenho;
	}

	public MetadadoDesenho() {
	}
}
