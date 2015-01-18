package br.fucapi.ads.modelo.dominio;

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
@Table(name = "ECM_SETOR")
public class Setor extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 7516652276680517473L;

	@Id
	@GeneratedValue(generator = "SEQ_ECM_SETOR", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_ECM_SETOR", name = "SEQ_ECM_SETOR")
	@Column(name = "ID_ECM_SETOR")
	private Long id;

	@Column(length = 30, nullable = false, unique = true)
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "ID_ECM_UNIDADE", nullable=false)
	private Unidade unidade;

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

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public String getLabel() {
		return nome;
	}

	public void setLabel(String nome) {
		this.nome = nome;
	}

	public Setor(String nome) {
		this.nome = nome;
	}

	public Setor() {
	}
	
	public boolean equals(Object o) {
		if (o instanceof Setor && ((Setor) o).getId() == this.id)
			return true;
		else
			return false;
	}

	public static Setor fromJsonToObject(String json) {
		return new JSONDeserializer<Setor>().use(null, Setor.class)
				.deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer()
				.exclude("*.class")
				.transform(new DateTransformer("dd/MM/yyyy HH:mm:ss"),
						Date.class).serialize(this);
	}

	public static String toJsonArray(Collection<Setor> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

}