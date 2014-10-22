package br.com.ss.core.seguranca.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * @author altitdb
 */
@Entity
@Table(name = "saa_perfil")
public class Perfil extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 7371241296081749393L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idPerfil;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioPerfilPk.perfil")
	private List<UsuarioPerfil> usuarioPerfil = new ArrayList<UsuarioPerfil>();

	@OneToMany(fetch = FetchType.EAGER,
			mappedBy = "perfilRotinaPk.perfil")
	private List<PerfilRotina> perfilRotina = new ArrayList<PerfilRotina>();

	@Column(length = 30, nullable = false)
	private String nome;

	@Column(length = 60 )
	private String imagem;

	@ManyToOne
	@JoinColumn(name = "id_sistema")
	private Sistema sistema;

	@Transient
	private boolean checked;

	@Override
	public Long getId() {
		return idPerfil;
	}

	public Long getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}

	public List<UsuarioPerfil> getUsuarioPerfil() {
		return usuarioPerfil;
	}

	public void setUsuarioPerfil(List<UsuarioPerfil> usuarioPerfil) {
		this.usuarioPerfil = usuarioPerfil;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
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

	public List<PerfilRotina> getPerfilRotina() {
		return perfilRotina;
	}

	public void setPerfilRotina(List<PerfilRotina> perfilRotina) {
		this.perfilRotina = perfilRotina;
	}

}