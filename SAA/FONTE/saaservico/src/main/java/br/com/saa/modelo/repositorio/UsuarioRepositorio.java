package br.com.saa.modelo.repositorio;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.saa.modelo.entidade.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

	@Query("select u from Usuario u where u.login = :login")
	public Usuario findByLogin(@Param("login") String login);

	@Query("select u from Usuario u where u.nome like :nome")
	public List<Usuario> findByNomeLike(@Param("nome") String nome);

	@Query("select u from Usuario u where u.login = :login and u.senha = :senha")
	public Usuario findByLoginAndSenha(@Param("login") String login,
			@Param("senha") String senha) throws NoResultException;

}