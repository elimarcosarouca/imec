package br.com.ss.core.seguranca.dominio;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * 
 * @author altitdb
 */
@Entity
@AssociationOverrides({
		@AssociationOverride(name = "perfilRotinaPk.rotina", joinColumns = @JoinColumn(name = "idRotina")),
		@AssociationOverride(name = "perfilRotinaPk.perfil", joinColumns = @JoinColumn(name = "idPerfil")) })
@Table( name = "saa_perfil_rotina" )
public class PerfilRotina implements Serializable {

	private static final long serialVersionUID = -1220797610390530939L;

	@EmbeddedId
	private PerfilRotinaPk perfilRotinaPk = new PerfilRotinaPk();

	@Transient
	private Rotina rotina;

	@Transient
	private Perfil perfil;

	@Temporal(TemporalType.TIMESTAMP)
	private Date data;

	
	public Perfil getPerfil() {
		return perfilRotinaPk.getPerfil();
	}

	public void setPerfil(Perfil perfil) {
		perfilRotinaPk.setPerfil(perfil);
	}

	public Rotina getRotina() {
		return perfilRotinaPk.getRotina();
	}

	public void setRotina(Rotina rotina) {
		perfilRotinaPk.setRotina(rotina);
	}

	public PerfilRotinaPk getPerfilRotinaPk() {
		return perfilRotinaPk;
	}

	public void setPerfilRotinaPk(PerfilRotinaPk perfilRotinaPk) {
		this.perfilRotinaPk = perfilRotinaPk;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	@Override
	public boolean equals(Object obj) {
		return perfilRotinaPk.equals(obj);
	}

	@Override
	public int hashCode() {
		return perfilRotinaPk.hashCode();
	}
	
}