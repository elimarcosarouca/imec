package br.com.ss.model.repositorio;

import java.util.List;

import br.com.ss.model.entidade.Usuario;
import br.com.ss.model.entidade.UsuarioPerfil;

public interface UsuarioPerfilRepositorio extends
		GenericRepositorio<UsuarioPerfil, Long> {

	List<UsuarioPerfil> findByUsuario(Usuario usuario);

	List<UsuarioPerfil> pesquisar(UsuarioPerfil abstractEntity);

}