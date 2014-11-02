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
		@AssociationOverride(name = "usuarioPerfilPk.usuario", joinColumns = @JoinColumn(name = "idUsuario")),
		@AssociationOverride(name = "usuarioPerfilPk.perfil", joinColumns = @JoinColumn(name = "idPerfil")) })
@Table( name = "saa_usuario_perfil" )
public class UsuarioPerfil implements Serializable {

	private static final long serialVersionUID = -1220797610390530939L;

	@EmbeddedId
	private UsuarioPerfilPk usuarioPerfilPk = new UsuarioPerfilPk();

	@Transient
	private Usuario usuario;

	@Transient
	private Perfil perfil;

	@Temporal(TemporalType.TIMESTAMP)
	private Date data;

	
	@Transient
	private String nomePerfil;
	
	
	public Perfil getPerfil() {
		return usuarioPerfilPk.getPerfil();
	}

	public void setPerfil(Perfil perfil) {
		usuarioPerfilPk.setPerfil(perfil);
	}

	public Usuario getUsuario() {
		return usuarioPerfilPk.getUsuario();
	}

	public void setUsuario(Usuario usuario) {
		usuarioPerfilPk.setUsuario(usuario);
	}

	public UsuarioPerfilPk getUsuarioPerfilPk() {
		return usuarioPerfilPk;
	}

	public void setUsuarioPerfilPk(UsuarioPerfilPk usuarioPerfilPk) {
		this.usuarioPerfilPk = usuarioPerfilPk;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public boolean equals(Object obj) {
		return usuarioPerfilPk.equals( ( ( UsuarioPerfil ) obj ).getUsuarioPerfilPk() );
	}

	@Override
	public int hashCode() {
		return usuarioPerfilPk.hashCode();
	}


	public String getNomePerfil() {
		return nomePerfil;
	}

	public void setNomePerfil(String nomePerfil) {
		this.nomePerfil = nomePerfil;
	}
	
}