package br.com.ss.model.entidade;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

/**
 * 
 * @author altitdb
 */

@Entity
@Table(name = "SAA_USUARIO_PERFIL")
public class UsuarioPerfil extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -1220797610390530939L;

	@Id
	@GeneratedValue(generator = "SEQ_SAA_USUARIO_PERFIL", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_SAA_USUARIO_PERFIL", name = "SEQ_SAA_USUARIO_PERFIL")
	@Column(name = "ID_SAA_USUARIO_PERFIL")
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date data;

	@ManyToOne
	@JoinColumn(name = "ID_SAA_USUARIO")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "ID_SAA_ROTINA")
	private Rotina rotina;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

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

	public Rotina getRotina() {
		return rotina;
	}

	public void setRotina(Rotina rotina) {
		this.rotina = rotina;
	}

	public static UsuarioPerfil fromJsonToObject(String json) {
		return new JSONDeserializer<UsuarioPerfil>().use(null,
				UsuarioPerfil.class).deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer()
				.exclude("*.class")
				.transform(new DateTransformer("dd/MM/yyyy HH:mm:ss"),
						Date.class).serialize(this);
	}

	public static String toJsonArray(Collection<UsuarioPerfil> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

}