package br.fucapi.ads.modelo.repositorio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.fucapi.ads.modelo.dominio.UsuarioTarefa;
import br.fucapi.ads.modelo.repositorio.UsuarioTarefaRepositorio;

@Repository
@SuppressWarnings("unchecked") class UsuarioTarefaRepositorioImpl extends
		GenericRepositorioImpl<UsuarioTarefa, Long> implements
		UsuarioTarefaRepositorio {
	
	@Override
	public List<UsuarioTarefa> pesquisar(UsuarioTarefa abstractEntity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from UsuarioTarefa est ");

		if (notEmpty(abstractEntity.getIdTarefa())) {
			condictions.add(" est.idTarefa = :idTarefa ");
		}

		String orderBy = " order by est.idTarefa";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);

		if (notEmpty(abstractEntity.getIdTarefa())) {
			query.setParameter("idTarefa", abstractEntity.getIdTarefa());
		}

		return query.getResultList();
	}
}