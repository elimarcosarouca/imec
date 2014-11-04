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
 * The persistent class for the saa_usuario_sistema database table.
 * 
 */
@Entity
@Table(name = "saa_usuario_sistema")
public class UsuarioSistema extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -1348896933495695497L;

	@Id
	@GeneratedValue(generator = "SEQ_SAA_USUARIO_SISTEMA", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_SAA_USUARIO_SISTEMA", name = "SEQ_SAA_USUARIO_SISTEMA")
	@Column(name = "id_saa_usuario_sistema")
	private Long id;

	@Column(name = "data_inclusao", nullable = false)
	private Date dataInclusao;

	@Column(name = "data_exclusao")
	private Date dataExclusao;

	@ManyToOne
	@JoinColumn(name = "id_saa_usuario", nullable = false)
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "id_saa_sistema", nullable = false)
	private Sistema sistema;

	public UsuarioSistema() {
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Date getDataExclusao() {
		return dataExclusao;
	}

	public void setDataExclusao(Date dataExclusao) {
		this.dataExclusao = dataExclusao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	public static UsuarioSistema fromJsonToObject(String json) {
		return new JSONDeserializer<UsuarioSistema>().use(null,
				UsuarioSistema.class).deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer()
				.exclude("*.class")
				.transform(new DateTransformer("dd/MM/yyyy HH:mm:ss"),
						Date.class).serialize(this);
	}

	public static String toJsonArray(Collection<UsuarioSistema> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

}