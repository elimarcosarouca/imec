package br.com.ss.model.repositorio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ss.model.entidade.Protocolo;
import br.com.ss.model.repositorio.ProtocoloRepositorio;

@Repository
@SuppressWarnings("unchecked")
public class ProtocoloRepositorioImpl extends
		GenericRepositorioImpl<Protocolo, Long> implements ProtocoloRepositorio {

	@Override
	public List<Protocolo> pesquisar(Protocolo abstractEntity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from Protocolo est ");

		if (notEmpty(abstractEntity.getAno())) {
			condictions.add("  est.ano = :ano ");
		}

		if (notEmpty(abstractEntity.getSequencial())) {
			condictions.add("  est.sequencial = :sequencial ");
		}

		String orderBy = " order by est.ano";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);
		if (notEmpty(abstractEntity.getAno())) {
			query.setParameter("ano", +abstractEntity.getAno());
		}

		if (notEmpty(abstractEntity.getSequencial())) {
			query.setParameter("sequencial", +abstractEntity.getSequencial());
		}

		return query.getResultList();
	}

	@Override
	public Protocolo pesquisarPorAno(Protocolo abstractEntity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from Protocolo est ");

		if (notEmpty(abstractEntity.getAno())) {
			condictions.add("  est.ano = :ano ");
		}

		String orderBy = " order by est.ano";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);
		if (notEmpty(abstractEntity.getAno())) {
			query.setParameter("ano", +abstractEntity.getAno());
		}

		return (Protocolo) query.getSingleResult();
	}
}