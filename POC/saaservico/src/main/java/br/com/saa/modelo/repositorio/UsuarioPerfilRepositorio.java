package br.com.saa.modelo.repositorio;

import java.util.Set;

import br.com.saa.modelo.entidade.Usuario;
import br.com.saa.modelo.entidade.UsuarioPerfil;

public interface UsuarioPerfilRepositorio extends
		GenericRepositorio<UsuarioPerfil, Long> {

	public Set<UsuarioPerfil> findByUsuario(Usuario usuario);

	/*
	 * @Query("select up from UsuarioPerfil up " +
	 * " where up.usuarioPerfilPk.usuario = :usuario " +
	 * " and up.usuarioPerfilPk.perfil = :perfil ") public UsuarioPerfil
	 * findByUsuarioAndPerfil(
	 * 
	 * @Param("usuario") Usuario usuario,
	 * 
	 * @Param("perfil") Perfil perfil );
	 * 
	 * @Query("select up from UsuarioPerfil up " +
	 * " where up.usuarioPerfilPk.usuario = :usuario ") public
	 * List<UsuarioPerfil> findByUsuario(
	 * 
	 * @Param("usuario") Usuario usuario);
	 */

}