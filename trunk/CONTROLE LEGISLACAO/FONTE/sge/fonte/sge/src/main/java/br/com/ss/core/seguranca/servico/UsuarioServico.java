package br.com.ss.core.seguranca.servico;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.ss.core.seguranca.dominio.Usuario;

public interface UsuarioServico extends IService<Usuario, Long> {

	public Usuario findByLogin(String login);

	public Usuario findByLoginAndSenha(String login, String senha) throws NoResultException;

	public List<Usuario> findByNomeLike(String nome) throws NoResultException;
}
