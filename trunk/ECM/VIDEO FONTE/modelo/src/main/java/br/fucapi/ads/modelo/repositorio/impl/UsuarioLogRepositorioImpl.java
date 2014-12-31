package br.fucapi.ads.modelo.repositorio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.fucapi.ads.modelo.dominio.UsuarioLog;
import br.fucapi.ads.modelo.repositorio.UsuarioLogRepositorio;

@Repository
@SuppressWarnings("unchecked")
public class UsuarioLogRepositorioImpl extends
		GenericRepositorioImpl<UsuarioLog, Long> implements
		UsuarioLogRepositorio {

	@Override
	public List<UsuarioLog> pesquisar(UsuarioLog abstractEntity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from UsuarioGrupoLog est ");

		if (notEmpty(abstractEntity.getLogin())) {
			condictions.add(" est.login = :login ");
		}

		String orderBy = " order by est.login";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);

		if (notEmpty(abstractEntity.getLogin())) {
			query.setParameter("login", abstractEntity.getLogin());
		}

		return query.getResultList();
	}
}