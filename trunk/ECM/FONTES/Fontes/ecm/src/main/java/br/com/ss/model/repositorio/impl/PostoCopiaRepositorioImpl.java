package br.com.ss.model.repositorio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ss.model.entidade.PostoCopia;
import br.com.ss.model.repositorio.PostoCopiaRepositorio;

@Repository
@SuppressWarnings("unchecked")
public class PostoCopiaRepositorioImpl extends
		GenericRepositorioImpl<PostoCopia, Long> implements
		PostoCopiaRepositorio {

	@Override
	public List<PostoCopia> pesquisar(PostoCopia abstractEntity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from PostoCopia est ");

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