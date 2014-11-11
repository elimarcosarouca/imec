package br.com.ss.model.repositorio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ss.model.entidade.Perfil;
import br.com.ss.model.entidade.Rotina;
import br.com.ss.model.entidade.Sistema;
import br.com.ss.model.repositorio.RotinaRepositorio;

@Repository
@SuppressWarnings("unchecked")
public class RotinaRepositorioImpl extends GenericRepositorioImpl<Rotina, Long>
		implements RotinaRepositorio {

	@Override
	public List<Rotina> findByPerfil(Perfil perfil) {

		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select rot from Rotina rot ");

		if (notEmpty(perfil.getSistema())) {
			condictions.add(" rot.perfil = :perfil ");
		}

		String orderBy = " order by rot.nome ";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);

		if (notEmpty(perfil.getSistema())) {
			query.setParameter("perfil", perfil);
		}

		return query.getResultList();

	}

	@Override
	public List<Rotina> findBySistema(Sistema sistema) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select rot from Rotina rot ");

		if (notEmpty(sistema)) {
			condictions.add(" rot.sistema = :sistema ");
		}

		String orderBy = " order by rot.nome ";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);

		if (notEmpty(sistema)) {
			query.setParameter("sistema", sistema);
		}

		return query.getResultList();
	}

	@Override
	public List<Rotina> findBySistemaByNomeLike(Sistema sistema) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Rotina> pesquisar(Rotina abstractEntity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from PerfilRotina est ");

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