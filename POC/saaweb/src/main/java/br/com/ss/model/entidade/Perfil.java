package br.com.ss.model.entidade;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
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
 * 
 * @author altitdb
 */
@Entity
@Table(name = "SAA_PERFIL")
public class Perfil extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 7371241296081749393L;

	@Id
	@GeneratedValue(generator = "SEQ_SAA_PERFIL", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_SAA_PERFIL", name = "SEQ_SAA_PERFIL")
	@Column(name = "ID_SAA_PERFIL")
	private Long id;

	@OneToMany(mappedBy = "perfil")
	@Column(name = "ID_SAA_PERFIL")
	private Set<PerfilRotina> perfilRotinas;

	@Column(length = 30, nullable = false)
	private String nome;

	@Column(length = 60)
	private String imagem;

	@ManyToOne
	@JoinColumn(name = "ID_SAA_SISTEMA")
	private Sistema sistema;

	@Override
	public Long getId() {
		return id;
	}

	public void setIdPerfil(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	public Set<PerfilRotina> getPerfilRotinas() {
		return perfilRotinas;
	}

	public void setPerfilRotinas(Set<PerfilRotina> perfilRotinas) {
		this.perfilRotinas = perfilRotinas;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public static Perfil fromJsonToObject(String json) {
		return new JSONDeserializer<Perfil>().use(null, Perfil.class)
				.deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer()
				.exclude("*.class")
				.transform(new DateTransformer("dd/MM/yyyy HH:mm:ss"),
						Date.class).serialize(this);
	}

	public static String toJsonArray(Collection<Perfil> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

}