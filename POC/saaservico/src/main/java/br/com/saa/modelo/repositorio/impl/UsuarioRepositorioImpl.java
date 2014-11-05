package br.com.saa.modelo.repositorio.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.saa.modelo.entidade.Usuario;
import br.com.saa.modelo.repositorio.UsuarioRepositorio;

@Repository
@Transactional
public class UsuarioRepositorioImpl extends
		GenericRepositorioImpl<Usuario, Serializable> implements
		UsuarioRepositorio {

	@Override
	public Usuario findByLoginAndSenha(String login, String senha)
			throws NoResultException {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select usu from Usuario usu ");

		if (notEmpty(login) && notEmpty(login)) {
			condictions.add(" rot.login = :login ");
			condictions.add(" rot.senha = :senha ");

		} else {
			return null;
		}

		String orderBy = " order by rot.nome ";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);

		if (notEmpty(login) && notEmpty(login)) {
			query.setParameter("login", login);
			query.setParameter("senha", senha);
		}

		return (Usuario) query.getSingleResult();
	}

}
