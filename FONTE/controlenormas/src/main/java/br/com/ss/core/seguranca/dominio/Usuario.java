package br.com.ss.core.seguranca.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.ss.core.web.enumerated.StatusUsuario;
import br.com.ss.core.web.enumerated.TipoUsuario;

/**
 * @author claudemirferreira
 */
@Entity
@Table(name = "saa_usuario")
public class Usuario extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -7789936704890560797L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idUsuario;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioPerfilPk.usuario")
	private List<UsuarioPerfil> usuarioPerfil = new ArrayList<UsuarioPerfil>();

	@Column(nullable = false, length = 30)
	private String nome;

	@Column(unique = true, length = 30)
	private String login;

	@Column(length = 64)
	private String senha;

	@Enumerated
	@Column(name = "status", length = 1, nullable = false)
	private StatusUsuario status;
	
	@Column( length = 100, nullable = false)
	private String email;
	
	@Enumerated
	@Column(name = "tipo_usuario", length = 1, nullable = false)
	private TipoUsuario tipoUsuario;
	
	
	@Override
	public Long getId() {
		return idUsuario;
	}

	
	public Usuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Usuario() {
	}

	@Transient
	public UsuarioPerfil getUsuarioPerfilByPerfil(Perfil perfil) {
		for (UsuarioPerfil up : usuarioPerfil) {
			if (up.getUsuarioPerfilPk().getPerfil().equals(perfil)) {
				return up;
			}
		}
		return null;
	}

	@Transient
	public boolean containsPerfil(Perfil perfil) {
		return getUsuarioPerfilByPerfil(perfil) != null;
	}

	public List<UsuarioPerfil> getUsuarioPerfil() {
		return usuarioPerfil;
	}

	public void setUsuarioPerfil(List<UsuarioPerfil> usuarioPerfil) {
		this.usuarioPerfil = usuarioPerfil;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public StatusUsuario getStatus() {
		return status;
	}

	public void setStatus(StatusUsuario status) {
		this.status = status;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

}