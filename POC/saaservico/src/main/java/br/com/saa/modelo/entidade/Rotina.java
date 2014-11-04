package br.com.saa.modelo.entidade;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.saa.enumerated.StatusRotina;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

/**
 * 
 * @author altitdb
 */
@Entity
@Table(name = "saa_rotina")
public class Rotina extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 55549693990924773L;

	@Id
	@GeneratedValue(generator = "SEQ_SAA_ROTINA", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_SAA_ROTINA", name = "SEQ_SAA_ROTINA")
	@Column(name = "id_saa_rotina")
	private Long id;

	@Column(length = 60)
	private String imagem;

	@Column(length = 100, nullable = false)
	private String nome;

	@Column(length = 100, nullable = false)
	private String acao;

	@ManyToOne
	@JoinColumn(name = "id_saa_sistema")
	private Sistema sistema;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 1)
	private StatusRotina status;

	@OneToMany(mappedBy = "rotina")
	@Column(name = "id_saa_rotina")
	private Set<PerfilRotina> perfilRotinas;

	@Override
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

	public StatusRotina getStatus() {
		return status;
	}

	public void setStatus(StatusRotina status) {
		this.status = status;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public Set<PerfilRotina> getPerfilRotinas() {
		return perfilRotinas;
	}

	public void setPerfilRotinas(Set<PerfilRotina> perfilRotinas) {
		this.perfilRotinas = perfilRotinas;
	}

	public static Rotina fromJsonToObject(String json) {
		return new JSONDeserializer<Rotina>().use(null, Rotina.class)
				.deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer()
				.exclude("*.class")
				.transform(new DateTransformer("dd/MM/yyyy HH:mm:ss"),
						Date.class).serialize(this);
	}

	public static String toJsonArray(Collection<Rotina> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}
}