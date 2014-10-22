package br.com.ss.core.seguranca.dominio;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 
 * @author altitdb
 */
@Embeddable 
public class UsuarioPerfilPk implements Serializable {

	private static final long serialVersionUID = -8682045998279798805L;

	@ManyToOne
	@JoinColumn(name = "idUsuario")
	private Usuario usuario;
	@ManyToOne
	@JoinColumn(name = "idPerfil")
	private Perfil perfil;

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		if (!( obj instanceof UsuarioPerfilPk)) {
			return false;
		}
		
		final UsuarioPerfilPk other = (UsuarioPerfilPk) obj;
		if (this.usuario != other.usuario
				&& (this.usuario == null || !this.usuario.equals(other.usuario))) {
			return false;
		}
		if (this.perfil != other.perfil
				&& (this.perfil == null || !this.perfil.equals(other.perfil))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 97 * hash + (this.usuario != null ? this.usuario.hashCode() : 0);
		hash = 97 * hash + (this.perfil != null ? this.perfil.hashCode() : 0);
		return hash;
	}

}
