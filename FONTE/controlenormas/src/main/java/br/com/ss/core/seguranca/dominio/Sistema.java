package br.com.ss.core.seguranca.dominio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the saa_sistema database table.
 * 
 */
@Entity
@Table(name = "saa_sistema")
public class Sistema extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -1348896933495695497L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idSistema;

	@Column(length = 6, nullable = false, unique = true)
	private String codigo;

	@Column(length = 200, nullable = false)
	private String descricao;

	@Column(length = 100, nullable = false)
	private String nome;

	@Column(length = 100, nullable = false)
	private String imagem;

	// bi-directional many-to-one association to Perfil
	@OneToMany(mappedBy = "sistema")
	private List<Perfil> perfils;

	// bi-directional many-to-one association to Rotina
//	@OneToMany(mappedBy = "sistema")
//	private List<Rotina> rotinas;

	public Sistema() {
	}

	@Override
	public Long getId() {
		return this.idSistema;
	}

	public Long getIdSistema() {
		return idSistema;
	}

	public void setIdSistema(Long idSistema) {
		this.idSistema = idSistema;
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

	public List<Perfil> getPerfils() {
		return this.perfils;
	}

	public void setPerfils(List<Perfil> perfils) {
		this.perfils = perfils;
	}

	public Perfil addPerfil(Perfil perfil) {
		getPerfils().add(perfil);
		perfil.setSistema(this);

		return perfil;
	}

	public Perfil removePerfil(Perfil perfil) {
		getPerfils().remove(perfil);
		perfil.setSistema(null);

		return perfil;
	}

//	public List<Rotina> getRotinas() {
//		return this.rotinas;
//	}
//
//	public void setRotinas(List<Rotina> rotinas) {
//		this.rotinas = rotinas;
//	}

//	public Rotina addRotina(Rotina rotina) {
//		getRotinas().add(rotina);
//		rotina.setSistema(this);
//
//		return rotina;
//	}
//
//	public Rotina removeRotina(Rotina rotina) {
//		getRotinas().remove(rotina);
//		rotina.setSistema(null);
//
//		return rotina;
//	}

}