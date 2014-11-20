package br.com.ss.model.repositorio;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.ss.model.entidade.Usuario;

public interface UsuarioRepositorio extends GenericRepositorio<Usuario, Long> {

	Usuario findByLoginAndSenha(String login, String senha)
			throws NoResultException;

	List<Usuario> pesquisar(Usuario abstractEntity);

}