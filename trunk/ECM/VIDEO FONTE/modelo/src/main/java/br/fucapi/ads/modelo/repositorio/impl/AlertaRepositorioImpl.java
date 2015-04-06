package br.fucapi.ads.modelo.repositorio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.fucapi.ads.modelo.dominio.Alerta;
import br.fucapi.ads.modelo.repositorio.AlertaRepositorio;

@Repository
@SuppressWarnings("unchecked")
public class AlertaRepositorioImpl extends GenericRepositorioImpl<Alerta, Long>
		implements AlertaRepositorio {

	@Override
	public List<Alerta> pesquisar(Alerta abstractEntity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from Alerta est ");

		if (notEmpty(abstractEntity.getProtocolo())) {
			condictions.add(" est.protocolo =:protocolo ");
		}
		
		if (notEmpty(abstractEntity.getDataAlerta())) {
			condictions.add(" est.dataAlerta >= :dataAlerta ");
		}
		
		condictions.add(" est.concluida =:concluida ");

		String orderBy = " order by est.protocolo";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);
		if (notEmpty(abstractEntity.getProtocolo())) {
			query.setParameter("protocolo", abstractEntity.getProtocolo());
		}
		
		if (notEmpty(abstractEntity.getDataAlerta())) {
			query.setParameter("dataAlerta", abstractEntity.getDataAlerta());
		}
		
		query.setParameter("concluida", abstractEntity.isConcluida());

		return query.getResultList();
	}
}