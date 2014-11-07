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
@Table(name = "saa_sistema")
public class Sistema extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -1348896933495695497L;

	@Id
	@GeneratedValue(generator = "SEQ_SAA_SISTEMA", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_SAA_SISTEMA", name = "SEQ_SAA_SISTEMA")
	@Column(name = "id_saa_sistema")
	private Long id;

	@Column(length = 6, nullable = false, unique = true)
	private String codigo;

	@Column(length = 200, nullable = false)
	private String descricao;

	@Column(length = 100, nullable = false)
	private String nome;

	@Column(length = 100, nullable = false)
	private String imagem;

	@Column
	private Double valor;

	// bi-directional many-to-one association to Perfil
	/*
	 * @OneToMany(mappedBy = "sistema") private List<Perfil> perfils;
	 */

	// bi-directional many-to-one association to Rotina
	// @OneToMany(mappedBy = "sistema")
	// private List<Rotina> rotinas;

	@OneToMany(mappedBy = "sistema")
	@Column(name = "id_saa_sistema")
	private Set<UsuarioSistema> usuarioSistemas;

	public Sistema() {
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	/*
	 * public List<Perfil> getPerfils() { return this.perfils; }
	 * 
	 * public void setPerfils(List<Perfil> perfils) { this.perfils = perfils; }
	 * 
	 * public Perfil addPerfil(Perfil perfil) { getPerfils().add(perfil);
	 * perfil.setSistema(this);
	 * 
	 * return perfil; }
	 * 
	 * public Perfil removePerfil(Perfil perfil) { getPerfils().remove(perfil);
	 * perfil.setSistema(null);
	 * 
	 * return perfil; }
	 */

	// public List<Rotina> getRotinas() {
	// return this.rotinas;
	// }
	//
	// public void setRotinas(List<Rotina> rotinas) {
	// this.rotinas = rotinas;
	// }

	// public Rotina addRotina(Rotina rotina) {
	// getRotinas().add(rotina);
	// rotina.setSistema(this);
	//
	// return rotina;
	// }
	//
	// public Rotina removeRotina(Rotina rotina) {
	// getRotinas().remove(rotina);
	// rotina.setSistema(null);
	//
	// return rotina;
	// }

	public static Sistema fromJsonToObject(String json) {
		return new JSONDeserializer<Sistema>().use(null, Sistema.class)
				.deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer()
				.exclude("*.class")
				.transform(new DateTransformer("dd/MM/yyyy HH:mm:ss"),
						Date.class).serialize(this);
	}

	public static String toJsonArray(Collection<Sistema> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

}