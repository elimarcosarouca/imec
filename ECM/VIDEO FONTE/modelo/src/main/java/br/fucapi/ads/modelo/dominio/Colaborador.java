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
@Table(name = "ECM_COLABORADOR")
public class Colaborador extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 7516652276680517473L;

	@Id
	@GeneratedValue(generator = "SEQ_ECM_COLABORADOR", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_ECM_COLABORADOR", name = "SEQ_ECM_COLABORADOR")
	@Column(name = "ID_ECM_COLABORADOR")
	private Long id;

	@Column(length = 60, nullable = false)
	private String nome;
	
	@Column(length = 30, nullable = false, unique = true)
	private String matricula;
	
	@ManyToOne
	@JoinColumn(name = "ID_ECM_POSTO_COPIA", nullable=false)
	private PostoCopia postoCopia;

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

	public String getLabel() {
		return nome;
	}

	public void setLabel(String nome) {
		this.nome = nome;
	}

	public Colaborador(String nome) {
		this.nome = nome;
	}

	public Colaborador() {
	}
	
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public PostoCopia getPostoCopia() {
		return postoCopia;
	}

	public void setPostoCopia(PostoCopia postoCopia) {
		this.postoCopia = postoCopia;
	}

	public boolean equals(Object o) {
		if (o instanceof Colaborador && ((Colaborador) o).getId() == this.id)
			return true;
		else
			return false;
	}

	public static Colaborador fromJsonToObject(String json) {
		return new JSONDeserializer<Colaborador>().use(null, Colaborador.class)
				.deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer()
				.exclude("*.class")
				.transform(new DateTransformer("dd/MM/yyyy HH:mm:ss"),
						Date.class).serialize(this);
	}

	public static String toJsonArray(Collection<Colaborador> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

}