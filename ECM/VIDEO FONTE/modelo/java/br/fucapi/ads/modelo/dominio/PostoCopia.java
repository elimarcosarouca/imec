package br.fucapi.ads.modelo.dominio;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "ECM_POSTO_COPIA")
public class PostoCopia extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 7516652276680517473L;

	@Id
	@GeneratedValue(generator = "SEQ_ECM_POSTO_COPIA", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_ECM_POSTO_COPIA", name = "SEQ_ECM_POSTO_COPIA")
	@Column(name = "ID_ECM_POSTO_COPIA")
	private Long id;

	@Column(length = 30, nullable = false, unique = true)
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "ID_ECM_SETOR", nullable=false)
	private Setor setor;
	
	@Column(length = 30, nullable = false)
	private String loginResponsavel;
	
	@OneToMany(mappedBy = "postoCopia", cascade = CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval = true )
	private List<FuncionarioPostoCopia> funcionariosPostoCopia;
	

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

	public PostoCopia(String nome) {
		super();
		this.nome = nome;
	}

	public PostoCopia() {
		super();
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public boolean equals(Object o) {
		if (o instanceof PostoCopia && ((PostoCopia) o).getId() == this.id)
			return true;
		else
			return false;
	}

	public static PostoCopia fromJsonToPostoCopia(String json) {
		return new JSONDeserializer<PostoCopia>().use(null, PostoCopia.class)
				.deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer()
				.exclude("*.class")
				.transform(new DateTransformer("dd/MM/yyyy HH:mm:ss"),
						Date.class).serialize(this);
	}

	public static String toJsonArray(Collection<PostoCopia> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

}