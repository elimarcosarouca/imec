package br.fucapi.ads.modelo.repositorio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.fucapi.ads.modelo.dominio.Colaborador;
import br.fucapi.ads.modelo.repositorio.ColaboradorRepositorio;

@Repository
@SuppressWarnings("unchecked")
public class ColaboradorRepositorioImpl extends
		GenericRepositorioImpl<Colaborador, Long> implements
		ColaboradorRepositorio {

	@Override
	public List<Colaborador> pesquisar(Colaborador abstractEntity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from Colaborador est ");

		if (notEmpty(abstractEntity.getNome())) {
			condictions.add(" lower( est.nome ) like :nome ");
		}

		if (notEmpty(abstractEntity.getPostoCopia())) {
			condictions.add(" est.postoCopia = :postoCopia ");
		}

		String orderBy = " order by est.nome";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);
		if (notEmpty(abstractEntity.getNome())) {
			query.setParameter("nome", "%"
					+ abstractEntity.getNome().trim().toLowerCase() + "%");
		}

		if (notEmpty(abstractEntity.getPostoCopia())) {
			query.setParameter("postoCopia", abstractEntity.getPostoCopia());
		}

		return query.getResultList();
	}
}