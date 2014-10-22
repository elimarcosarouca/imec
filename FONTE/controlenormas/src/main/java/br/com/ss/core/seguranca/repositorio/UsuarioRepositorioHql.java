package br.com.ss.core.seguranca.repositorio;

import java.util.List;

import br.com.ss.core.seguranca.dominio.Usuario;

public interface UsuarioRepositorioHql {

	List<Usuario> pesquisar(Usuario entity);

}
