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

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

/**
 * The persistent class for the saa_sistema database table.
 * 
 */
@Entity
@Table(name = "SAA_MUNICIPIO")
public class Municipio extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 6773795801485294271L;

	@Id
	@GeneratedValue(generator = "SEQ_SAA_MUNICIPIO", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_SAA_MUNICIPIO", name = "SEQ_SAA_MUNICIPIO")
	@Column(name = "ID_SAA_MUNICIPIO")
	private Long id;

	@Column(length = 30, nullable = false, unique = true)
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "ID_SAA_ESTADO", nullable = false)
	private Estado estado;

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

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public static Municipio fromJsonToObject(String json) {
		return new JSONDeserializer<Municipio>().use(null, Municipio.class)
				.deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer()
				.exclude("*.class")
				.transform(new DateTransformer("dd/MM/yyyy HH:mm:ss"),
						Date.class).serialize(this);
	}

	public static String toJsonArray(Collection<Municipio> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

}