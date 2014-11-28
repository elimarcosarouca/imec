package br.com.ss.model.entidade;

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
@Table(name = "ECM_PROTOCOLO")
public class Protocolo extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -8769403963489756354L;

	@Id
	@GeneratedValue(generator = "SEQ_ECM_PROTOCOLO", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_ECM_PROTOCOLO", name = "SEQ_ECM_PROTOCOLO")
	@Column(name = "ID_ECM_PROTOCOLO")
	private Long id;

	@Column(nullable = false, length = 10)
	private int sequencial;

	@Column(nullable = false, length = 4)
	private int ano;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSequencial() {
		return sequencial;
	}

	public void setSequencial(int sequencial) {
		this.sequencial = sequencial;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public static Protocolo fromJsonToObject(String json) {
		return new JSONDeserializer<Protocolo>().use(null, Protocolo.class)
				.deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer()
				.exclude("*.class")
				.transform(new DateTransformer("dd/MM/yyyy HH:mm:ss"),
						Date.class).serialize(this);
	}

	public static String toJsonArray(Collection<Protocolo> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

}