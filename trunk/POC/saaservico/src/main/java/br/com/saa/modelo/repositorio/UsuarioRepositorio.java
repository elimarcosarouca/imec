package br.com.saa.modelo.repositorio;

import javax.persistence.NoResultException;

import br.com.saa.modelo.entidade.Usuario;

public interface UsuarioRepositorio extends GenericRepositorio<Usuario, Long> {

	public Usuario findByLoginAndSenha(String login, String senha)
			throws NoResultException;

}