package br.com.ss.model.repositorio.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ss.model.entidade.PalavraChave;
import br.com.ss.model.repositorio.PalavraChaveRepositorio;

@Repository
@SuppressWarnings("unchecked")
public class PalavraChaveRepositorioImpl extends
		GenericRepositorioImpl<PalavraChave, Long> implements PalavraChaveRepositorio,
		Serializable {

	private static final long serialVersionUID = 4598749258534199622L;

	@Override
	public List<PalavraChave> pesquisar(PalavraChave abstractEntity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from PalavraChave est ");

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