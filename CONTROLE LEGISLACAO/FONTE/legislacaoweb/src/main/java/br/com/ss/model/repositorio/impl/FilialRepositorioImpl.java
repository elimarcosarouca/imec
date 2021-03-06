package br.com.ss.model.repositorio.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ss.model.entidade.Filial;
import br.com.ss.model.repositorio.FilialRepositorio;

@Repository
@SuppressWarnings("unchecked")
public class FilialRepositorioImpl extends GenericRepositorioImpl<Filial, Long>
		implements FilialRepositorio, Serializable {

	private static final long serialVersionUID = 4598749258534199622L;

	@Override
	public List<Filial> pesquisar(Filial abstractEntity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from Filial est ");

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