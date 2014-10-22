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
public class PerfilRotinaPk implements Serializable {

	private static final long serialVersionUID = -8682045998279798805L;

	@ManyToOne
	@JoinColumn(name = "idRotina")
	private Rotina rotina;
	@ManyToOne
	@JoinColumn(name = "idPerfil")
	private Perfil perfil;

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Rotina getRotina() {
		return rotina;
	}

	public void setRotina(Rotina rotina) {
		this.rotina = rotina;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PerfilRotinaPk other = (PerfilRotinaPk) obj;
		if (this.rotina != other.rotina
				&& (this.rotina == null || !this.rotina.equals(other.rotina))) {
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
		hash = 97 * hash + (this.rotina != null ? this.rotina.hashCode() : 0);
		hash = 97 * hash + (this.perfil != null ? this.perfil.hashCode() : 0);
		return hash;
	}

}
