package br.com.ss.model.repositorio.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ss.model.entidade.Legislacao;
import br.com.ss.model.repositorio.LegislacaoRepositorio;

@Repository
@SuppressWarnings("unchecked")
public class LegislacaoRepositorioImpl extends
		GenericRepositorioImpl<Legislacao, Long> implements LegislacaoRepositorio,
		Serializable {

	private static final long serialVersionUID = 4598749258534199622L;

	@Override
	public List<Legislacao> pesquisar(Legislacao abstractEntity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from Legislacao est ");

		if (notEmpty(abstractEntity.getNumero())) {
			condictions.add(" lower( est.numero ) like :numero ");
		}

		String orderBy = " order by est.numero";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);
		if (notEmpty(abstractEntity.getNumero())) {
			query.setParameter("nome", "%"
					+ abstractEntity.getNumero().trim().toLowerCase() + "%");
		}
		return query.getResultList();
	}

}