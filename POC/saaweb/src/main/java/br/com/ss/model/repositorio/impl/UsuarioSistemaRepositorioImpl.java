package br.com.ss.model.repositorio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ss.model.entidade.UsuarioSistema;
import br.com.ss.model.repositorio.UsuarioSistemaRepositorio;

@Repository
public class UsuarioSistemaRepositorioImpl extends
		GenericRepositorioImpl<UsuarioSistema, Long> implements
		UsuarioSistemaRepositorio {

	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioSistema> pesquisar(UsuarioSistema abstractEntity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from UsuarioSistema est ");

		/*
		 * if (notEmpty(abstractEntity.getNome())) {
		 * condictions.add(" lower( est.nome ) like :nome "); }
		 */

		String orderBy = "  ";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);

		/*
		 * if (notEmpty(abstractEntity.getNome())) { query.setParameter("nome",
		 * "%" + abstractEntity.getNome().trim().toLowerCase() + "%"); }
		 */

		return query.getResultList();
	}
}