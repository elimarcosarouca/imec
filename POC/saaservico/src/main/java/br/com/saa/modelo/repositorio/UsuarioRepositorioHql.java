package br.com.saa.modelo.repositorio;

import java.util.List;

import br.com.saa.modelo.entidade.Usuario;

public interface UsuarioRepositorioHql {

	List<Usuario> pesquisar(Usuario entity);

}
