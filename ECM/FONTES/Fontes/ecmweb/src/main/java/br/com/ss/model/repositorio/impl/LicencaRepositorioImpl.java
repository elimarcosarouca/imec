package br.com.ss.model.repositorio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ss.model.entidade.Licenca;
import br.com.ss.model.repositorio.LicencaRepositorio;

@Repository
@SuppressWarnings("unchecked")
public class LicencaRepositorioImpl extends
		GenericRepositorioImpl<Licenca, Long> implements LicencaRepositorio {

	@Override
	public List<Licenca> pesquisar(Licenca abstractEntity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from Licenca est ");

		/*
		 * if (notEmpty(abstractEntity.getNome())) {
		 * condictions.add(" lower( est.nome ) like :nome "); }
		 */

		String orderBy = " ";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);
		/*
		 * if (notEmpty(abstractEntity.getNome())) { query.setParameter("nome",
		 * "%" + abstractEntity.getNome().trim().toLowerCase() + "%"); }
		 */

		return query.getResultList();
	}

}
