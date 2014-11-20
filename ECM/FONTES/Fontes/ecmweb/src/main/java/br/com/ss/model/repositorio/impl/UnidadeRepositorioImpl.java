package br.com.ss.model.repositorio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ss.model.entidade.Unidade;
import br.com.ss.model.repositorio.UnidadeRepositorio;

@Repository
@SuppressWarnings("unchecked")
public class UnidadeRepositorioImpl extends
		GenericRepositorioImpl<Unidade, Long> implements UnidadeRepositorio {

	@Override
	public List<Unidade> pesquisar(Unidade abstractEntity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from Unidade est ");

		if (notEmpty(abstractEntity.getNome())) {
			condictions.add(" lower( est.nome ) like :nome ");
		}

		String orderBy = " order by est.nome";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);
		if (notEmpty(abstractEntity.getNome())) {
			query.setParameter("nome", "%"
					+ abstractEntity.getNome().trim().toLowerCase() + "%");
		}

		return query.getResultList();
	}
}