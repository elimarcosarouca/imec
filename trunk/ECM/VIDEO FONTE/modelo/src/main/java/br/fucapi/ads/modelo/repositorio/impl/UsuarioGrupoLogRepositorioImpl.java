package br.fucapi.ads.modelo.repositorio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.fucapi.ads.modelo.dominio.UsuarioGrupoLog;
import br.fucapi.ads.modelo.repositorio.UsuarioGrupoLogRepositorio;

@Repository
@SuppressWarnings("unchecked")
public class UsuarioGrupoLogRepositorioImpl extends
		GenericRepositorioImpl<UsuarioGrupoLog, Long> implements
		UsuarioGrupoLogRepositorio {

	@Override
	public List<UsuarioGrupoLog> pesquisar(UsuarioGrupoLog abstractEntity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from UsuarioGrupoLog est ");

		if (notEmpty(abstractEntity.getGrupo())) {
			condictions.add(" est.grupo = :grupo ");
		}

		if (notEmpty(abstractEntity.getLogin())) {
			condictions.add(" est.login = :login ");
		}

		String orderBy = " order by est.login";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);

		if (notEmpty(abstractEntity.getGrupo())) {
			query.setParameter("grupo", abstractEntity.getGrupo());
		}

		if (notEmpty(abstractEntity.getLogin())) {
			query.setParameter("login", abstractEntity.getLogin());
		}

		return query.getResultList();
	}
}