package br.fucapi.ads.modelo.repositorio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.fucapi.ads.modelo.dominio.GrupoLog;
import br.fucapi.ads.modelo.repositorio.GrupoLogRepositorio;
import br.fucapi.ads.modelo.repositorio.PostoCopiaRepositorio;

@Repository
@SuppressWarnings("unchecked")
public class GrupoLogRepositorioImpl extends
		GenericRepositorioImpl<GrupoLog, Long> implements
		GrupoLogRepositorio {

	@Override
	public List<GrupoLog> pesquisar(GrupoLog abstractEntity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from GrupoLog est ");

		if (notEmpty(abstractEntity.getGrupo())) {
			condictions.add(" lower( est.grupo ) like :nome ");
		}

		String orderBy = " order by est.grupo";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);
		if (notEmpty(abstractEntity.getGrupo())) {
			query.setParameter("nome", "%"
					+ abstractEntity.getGrupo().trim().toLowerCase() + "%");
		}

		return query.getResultList();
	}
}