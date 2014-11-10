package br.com.ss.model.repositorio.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ss.model.entidade.Estado;
import br.com.ss.model.repositorio.EstadoRepositorio;

@Repository
public class EstadoRepositorioImpl extends GenericRepositorioImpl<Estado, Long>
		implements EstadoRepositorio, Serializable {

	private static final long serialVersionUID = 4598749258534199622L;

	@Override
	public List<Estado> pesquisar(Estado abstractEntity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from Estado est ");

		if (notEmpty(abstractEntity.getNome())) {
			condictions.add(" lower( est.nome ) like :nome ");
		}
		if (notEmpty(abstractEntity.getUf())) {
			condictions.add(" est.uf = :uf ");
		}

		String orderBy = " order by est.nome";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);
		if (notEmpty(abstractEntity.getNome())) {
			query.setParameter("nome", "%"
					+ abstractEntity.getNome().trim().toLowerCase() + "%");
		}
		if (notEmpty(abstractEntity.getUf())) {
			query.setParameter("uf", abstractEntity.getUf());
		}
		return query.getResultList();
	}

}