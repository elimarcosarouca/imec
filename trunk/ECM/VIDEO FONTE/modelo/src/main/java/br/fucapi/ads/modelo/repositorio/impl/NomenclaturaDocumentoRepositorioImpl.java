package br.fucapi.ads.modelo.repositorio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.fucapi.ads.modelo.dominio.NomenclaturaDocumento;
import br.fucapi.ads.modelo.repositorio.NomenclaturaDocumentoRepositorio;

@Repository
@SuppressWarnings("unchecked")
public class NomenclaturaDocumentoRepositorioImpl extends
		GenericRepositorioImpl<NomenclaturaDocumento, Long> implements
		NomenclaturaDocumentoRepositorio {

	@Override
	public List<NomenclaturaDocumento> pesquisar(
			NomenclaturaDocumento abstractEntity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from NomeclaturaDocumento est ");

		String orderBy = " order by est.id";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);
		
		return query.getResultList();
	}

	@Override
	public NomenclaturaDocumento pegarSequencial(
			NomenclaturaDocumento abstractEntity) {

		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();
		
		NomenclaturaDocumento nomeclaturaDocumento = new NomenclaturaDocumento();

		sb.append(" select est from NomenclaturaDocumento est ");

		if (notEmpty(abstractEntity.getCategoria())) {
			condictions.add(" est.categoria =:categoria ");
		}

		if (notEmpty(abstractEntity.getSetor())) {
			condictions.add(" est.setor =:setor ");
		}

		if (notEmpty(abstractEntity.getUnidade())) {
			condictions.add(" est.unidade =:unidade ");
		}

		String orderBy = " order by est.id";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);
		
		if (notEmpty(abstractEntity.getCategoria())) {
			query.setParameter("categoria", abstractEntity.getCategoria());
		}
		if (notEmpty(abstractEntity.getSetor())) {
			query.setParameter("setor", abstractEntity.getSetor());
		}
		if (notEmpty(abstractEntity.getUnidade())) {
			query.setParameter("unidade", abstractEntity.getUnidade());
		}
		
		try {
			
			return (NomenclaturaDocumento) query.getSingleResult();
			
		} catch (NoResultException e) {
			abstractEntity.setSequencial(1);
			this.saveOrUpdate(abstractEntity);
		}catch (Exception e) {
			System.out.println("ocorreu um erro:  " );
		}

		return abstractEntity;

	}
}