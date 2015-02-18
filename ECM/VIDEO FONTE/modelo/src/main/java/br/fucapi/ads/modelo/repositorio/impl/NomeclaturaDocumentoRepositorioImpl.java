package br.fucapi.ads.modelo.repositorio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.fucapi.ads.modelo.dominio.NomeclaturaDocumento;
import br.fucapi.ads.modelo.repositorio.NomeclaturaDocumentoRepositorio;

@Repository
@SuppressWarnings("unchecked")
public class NomeclaturaDocumentoRepositorioImpl extends
		GenericRepositorioImpl<NomeclaturaDocumento, Long> implements
		NomeclaturaDocumentoRepositorio {

	@Override
	public List<NomeclaturaDocumento> pesquisar(
			NomeclaturaDocumento abstractEntity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from NomeclaturaDocumento est ");

		String orderBy = " order by est.id";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);
		
		return query.getResultList();
	}

	@Override
	public NomeclaturaDocumento pegarSequencial(
			NomeclaturaDocumento abstractEntity) {

		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();
		
		NomeclaturaDocumento nomeclaturaDocumento = new NomeclaturaDocumento();

		sb.append(" select est from NomeclaturaDocumento est ");

		/*if (notEmpty(abstractEntity.getCategoria())) {
			condictions.add(" est.categoria =:categoria ");
		}

		if (notEmpty(abstractEntity.getSetor())) {
			condictions.add(" est.setor =:setor ");
		}

		if (notEmpty(abstractEntity.getUnidade())) {
			condictions.add(" est.unidade =:unidade ");
		}*/

		String orderBy = " order by est.id";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);
		
		/*if (notEmpty(abstractEntity.getCategoria())) {
			query.setParameter("categoria", abstractEntity.getCategoria());
		}
		if (notEmpty(abstractEntity.getSetor())) {
			query.setParameter("setor", abstractEntity.getSetor());
		}
		if (notEmpty(abstractEntity.getUnidade())) {
			query.setParameter("unidade", abstractEntity.getUnidade());
		}*/
		
		try {
			
			return (NomeclaturaDocumento) query.getSingleResult();
			
		} catch (NoResultException e) {
			abstractEntity.setSequencial(1);
			this.saveOrUpdate(abstractEntity);
		}catch (Exception e) {
			System.out.println("ocorreu um erro:  " );
		}

		return abstractEntity;

	}
}