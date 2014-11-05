package br.com.saa.modelo.entidade;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "saa_estado")
public class Estado extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 6773795801485294271L;

	@Id
	@GeneratedValue(generator = "SEQ_SAA_ESTADO", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_SAA_ESTADO", name = "SEQ_SAA_ESTADO")
	@Column(name = "id_saa_estado")
	private Long id;

	@Column(length = 30, nullable = false, unique = true)
	private String nome;

	@Column( nullable = false, columnDefinition="char(2)", unique = true)
	private String uf;

	@OneToMany(mappedBy = "estado")
	@Column(name = "id_saa_estado")
	private Set<Municipio> municipios;

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

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public static Estado fromJsonToObject(String json) {
		return new JSONDeserializer<Estado>().use(null, Estado.class)
				.deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer()
				.exclude("*.class")
				.transform(new DateTransformer("dd/MM/yyyy HH:mm:ss"),
						Date.class).serialize(this);
	}

	public static String toJsonArray(Collection<Estado> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

}