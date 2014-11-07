package br.com.saa.modelo.repositorio;

import java.util.List;

import br.com.saa.modelo.entidade.Usuario;
import br.com.saa.modelo.entidade.UsuarioPerfil;

public interface UsuarioPerfilRepositorio extends
		GenericRepositorio<UsuarioPerfil, Long> {

	public List<UsuarioPerfil> findByUsuario(Usuario usuario);

}