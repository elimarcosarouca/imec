package br.com.ss.model.repositorio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ss.model.entidade.Usuario;
import br.com.ss.model.repositorio.UsuarioRepositorio;

@Repository
public class UsuarioRepositorioImpl extends
		GenericRepositorioImpl<Usuario, Long> implements UsuarioRepositorio {

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

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> pesquisar(Usuario abstractEntity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from Usuario est ");

		if (notEmpty(abstractEntity.getNome())) {
			condictions.add(" lower( est.nome ) like :nome ");
		}

		String orderBy = "  ";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);

		if (notEmpty(abstractEntity.getNome())) {
			query.setParameter("nome", "%"
					+ abstractEntity.getNome().trim().toLowerCase() + "%");
		}

		return query.getResultList();
	}
}