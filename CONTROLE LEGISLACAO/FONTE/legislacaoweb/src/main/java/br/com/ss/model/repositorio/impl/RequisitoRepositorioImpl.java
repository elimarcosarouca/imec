package br.com.ss.model.repositorio.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ss.model.entidade.Requisito;
import br.com.ss.model.repositorio.RequisitoRepositorio;

@Repository
@SuppressWarnings("unchecked")
public class RequisitoRepositorioImpl extends
		GenericRepositorioImpl<Requisito, Long> implements RequisitoRepositorio,
		Serializable {

	private static final long serialVersionUID = 4598749258534199622L;

	@Override
	public List<Requisito> pesquisar(Requisito abstractEntity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from Requisito est ");

		if (notEmpty(abstractEntity.getDescricao())) {
			condictions.add(" lower( est.nome ) like :nome ");
		}

		String orderBy = " order by est.nome";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);
		if (notEmpty(abstractEntity.getDescricao())) {
			query.setParameter("nome", "%"
					+ abstractEntity.getDescricao().trim().toLowerCase() + "%");
		}
		return query.getResultList();
	}

}