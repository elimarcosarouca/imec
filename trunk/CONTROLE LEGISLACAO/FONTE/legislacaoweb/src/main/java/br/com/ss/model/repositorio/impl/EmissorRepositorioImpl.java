package br.com.ss.model.repositorio.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ss.model.entidade.Emissor;
import br.com.ss.model.repositorio.EmissorRepositorio;

@Repository
@SuppressWarnings("unchecked")
public class EmissorRepositorioImpl extends
		GenericRepositorioImpl<Emissor, Long> implements EmissorRepositorio,
		Serializable {

	private static final long serialVersionUID = 4598749258534199622L;

	@Override
	public List<Emissor> pesquisar(Emissor abstractEntity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from Emissor est ");

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