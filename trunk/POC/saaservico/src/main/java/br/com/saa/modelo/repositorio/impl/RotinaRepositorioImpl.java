package br.com.saa.modelo.repositorio.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.saa.modelo.entidade.Perfil;
import br.com.saa.modelo.entidade.Rotina;
import br.com.saa.modelo.entidade.Sistema;
import br.com.saa.modelo.repositorio.RotinaRepositorio;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class RotinaRepositorioImpl extends
		GenericRepositorioImpl<Rotina, Serializable> implements
		RotinaRepositorio {

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

}
