package br.com.saa.modelo.entidade;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

/**
 * The persistent class for the saa_sistema database table.
 * 
 */
@Entity
@Table(name = "saa_licenca")
public class Licenca extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 6773795801485294271L;

	@Id
	@GeneratedValue(generator = "SEQ_SAA_LICENCA", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_SAA_LICENCA", name = "SEQ_SAA_LICENCA")
	@Column(name = "id_saa_licenca")
	private Long id;

	@Column(name = "data_inclusao", nullable = false)
	private Date dataInclusao;
	
	@Column(name = "inicio", nullable = false)
	private Date inicio;
	
	@Column(name = "fim")
	private Date fim;
	
	@Column(name = "valor_cobrado", nullable = false)
	private Double valorCobrado;
	
	@Column(name = "valor_pago")
	private Double valorPago;
	
	@Column(name = "trial")
	private Boolean trial;
	
	@ManyToOne
	@JoinColumn(name = "id_saa_usuario", nullable = false)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "id_saa_usuario_baixou")
	private Usuario usuarioQueBaixou;
	
	@Column(name = "data_baixa")
	private Date dataBaixa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public Double getValorCobrado() {
		return valorCobrado;
	}

	public void setValorCobrado(Double valorCobrado) {
		this.valorCobrado = valorCobrado;
	}

	public Double getValorPago() {
		return valorPago;
	}

	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}

	public Boolean getTrial() {
		return trial;
	}

	public void setTrial(Boolean trial) {
		this.trial = trial;
	}

	public Usuario getUsuarioQueBaixou() {
		return usuarioQueBaixou;
	}

	public void setUsuarioQueBaixou(Usuario usuarioQueBaixou) {
		this.usuarioQueBaixou = usuarioQueBaixou;
	}
	
	public static Licenca fromJsonToObject(String json) {
		return new JSONDeserializer<Licenca>().use(null, Licenca.class)
				.deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer()
				.exclude("*.class")
				.transform(new DateTransformer("dd/MM/yyyy HH:mm:ss"),
						Date.class).serialize(this);
	}

	public static String toJsonArray(Collection<Licenca> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

}