package br.fucapi.ads.modelo.repositorio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
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

		if (notEmpty(abstractEntity.getNomenclatura())) {
			condictions.add(" est.nomenclatura =:nomenclatura ");
		}

		if (notEmpty(abstractEntity.getTitulo())) {
			condictions.add(" est.titulo  =:titulo ");
		}

		if (notEmpty(abstractEntity.getUnidade())) {
			condictions.add(" est.unidade  =:unidade ");
		}

		String orderBy = " order by est.dataAlerta";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);
		if (notEmpty(abstractEntity.getProtocolo())) {
			query.setParameter("protocolo", abstractEntity.getProtocolo());
		}

		if (notEmpty(abstractEntity.getNomenclatura())) {
			query.setParameter("nomenclatura", abstractEntity.getNomenclatura());
		}

		if (notEmpty(abstractEntity.getTitulo())) {
			query.setParameter("titulo", abstractEntity.getTitulo());
		}

		if (notEmpty(abstractEntity.getUnidade())) {
			query.setParameter("unidade", abstractEntity.getUnidade());
		}
		return query.getResultList();
	}

	@Override
	public Alerta pesquisarProcessInstanceId(String processInstanceId) {

		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		Alerta alerta = null;

		sb.append(" select est from Alerta est ");

		if (notEmpty(processInstanceId)) {
			condictions.add(" est.processInstanceId =:processInstanceId ");
		}

		String orderBy = " order by est.dataAlerta";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);

		if (notEmpty(processInstanceId)) {
			query.setParameter("processInstanceId", processInstanceId);
		}

		try {
			alerta = (Alerta) query.getSingleResult();
		} catch (NoResultException e) {
			System.out.println("alerta não encontrado");
		} catch (Exception e) {
			System.out.println("ocorreu um erro");
		}

		return alerta;
	}
}