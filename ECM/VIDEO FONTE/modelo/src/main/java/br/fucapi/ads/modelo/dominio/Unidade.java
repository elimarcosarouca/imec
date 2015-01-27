package br.fucapi.ads.modelo.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

/**
 * The persistent class for the saa_sistema database table.
 * 
 */
@Entity
@Table(name = "ECM_UNIDADE")
public class Unidade extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 7516652276680517473L;

	@Id
	@GeneratedValue(generator = "SEQ_ECM_UNIDADE", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_ECM_UNIDADE", name = "SEQ_ECM_UNIDADE")
	@Column(name = "ID_ECM_UNIDADE")
	private Long id;

	@Column(length = 30, nullable = false, unique = true)
	private String nome;
	
	@Column( nullable = false )
	private Boolean global;

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

	public Unidade(String nome) {
		super();
		this.nome = nome;
	}

	public Unidade() {
	}

	public boolean equals(Object o) {
		if (o instanceof Unidade && ((Unidade) o).getId() == this.id)
			return true;
		else
			return false;
	}

	public static Unidade fromJsonToUnidade(String json) {
		return new JSONDeserializer<Unidade>().use(null, Unidade.class)
				.deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

}