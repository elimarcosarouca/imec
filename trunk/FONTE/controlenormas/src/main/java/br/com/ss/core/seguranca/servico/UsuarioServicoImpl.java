package br.com.ss.core.seguranca.servico;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.ss.core.seguranca.dominio.Usuario;
import br.com.ss.core.seguranca.repositorio.ServicoImpl;
import br.com.ss.core.seguranca.repositorio.UsuarioRepositorio;
import br.com.ss.core.seguranca.repositorio.UsuarioRepositorioHql;

@Service
public class UsuarioServicoImpl extends ServicoImpl<Usuario, Long> implements UsuarioServico {

	private static final long serialVersionUID = -530498056582804371L;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private UsuarioRepositorioHql usuarioRepositorioHql;

	@Override
	public Usuario findByLoginAndSenha(String login, String senha) throws NoResultException {
		return this.usuarioRepositorio.findByLoginAndSenha(login, senha);
	}

	@Override
	public Usuario findByLogin(String login) {
		return this.usuarioRepositorio.findByLogin(login);
	}

	@Override
	public List<Usuario> findByNomeLike(String nome) throws NoResultException {
		return this.usuarioRepositorio.findByNomeLike(nome);
	}

	@Override
	public List<Usuario> pesquisar(Usuario entity) {
		return usuarioRepositorioHql.pesquisar(entity);
	}

	@Override
	protected JpaRepository<Usuario, Long> getRepository() {
		return usuarioRepositorio;
	}

}