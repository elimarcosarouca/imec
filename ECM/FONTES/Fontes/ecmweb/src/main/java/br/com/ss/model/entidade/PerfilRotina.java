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
@Table(name = "SAA_PERFIL_ROTINA")
public class PerfilRotina extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -1220797610390530939L;

	@Id
	@GeneratedValue(generator = "SEQ_SAA_PERFIL_ROTINA", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_SAA_PERFIL_ROTINA", name = "SEQ_SAA_PERFIL_ROTINA")
	@Column(name = "ID_SAA_PERFIL_ROTINA")
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date data;

	@ManyToOne
	@JoinColumn(name = "ID_SAA_PERFIL")
	private Perfil perfil;

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

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Rotina getRotina() {
		return rotina;
	}

	public void setRotina(Rotina rotina) {
		this.rotina = rotina;
	}

	public static PerfilRotina fromJsonToObject(String json) {
		return new JSONDeserializer<PerfilRotina>().use(null,
				PerfilRotina.class).deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer()
				.exclude("*.class")
				.transform(new DateTransformer("dd/MM/yyyy HH:mm:ss"),
						Date.class).serialize(this);
	}

	public static String toJsonArray(Collection<PerfilRotina> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

}