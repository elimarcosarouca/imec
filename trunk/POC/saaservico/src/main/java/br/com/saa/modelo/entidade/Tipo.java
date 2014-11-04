package br.com.saa.modelo.entidade;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "saa_tipo")
public class Tipo extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 6773795801485294271L;

	@Id
	@GeneratedValue(generator = "SEQ_SAA_TIPO", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_SAA_TIPO", name = "SEQ_SAA_TIPO")
	@Column(name = "id_saa_tipo")
	private Long id;

	@Column(length = 30, nullable = false, unique = true)
	private String nome;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public static Tipo fromJsonToObject(String json) {
		return new JSONDeserializer<Tipo>().use(null, Tipo.class).deserialize(
				json);
	}

	public String toJson() {
		return new JSONSerializer()
				.exclude("*.class")
				.transform(new DateTransformer("dd/MM/yyyy HH:mm:ss"),
						Date.class).serialize(this);
	}

	public static String toJsonArray(Collection<Tipo> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

}