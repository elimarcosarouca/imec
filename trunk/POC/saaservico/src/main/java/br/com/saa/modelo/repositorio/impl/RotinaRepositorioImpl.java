package br.com.saa.modelo.repositorio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.saa.modelo.entidade.Perfil;
import br.com.saa.modelo.entidade.Rotina;
import br.com.saa.modelo.entidade.Sistema;
import br.com.saa.modelo.repositorio.RotinaRepositorio;

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
	public List<Rotina> listaRotinasPorPerfil(Long id) {
		return entityManager
				.createNativeQuery(
						"SELECT i.* FROM saa_rotina i, saa_perfil_rotina b "
						+ "where i.id_rotina = b.id_rotina and id_perfil = "
								+ id, Rotina.class).getResultList();

	}

	@Override
	public List<Rotina> pesquisar(Rotina entity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select rot from Rotina rot ");

		if (notEmpty(entity.getSistema())) {
			condictions.add(" rot.sistema = :sistema ");
		}
		if (notEmpty(entity.getNome())) {
			condictions.add(" rot.nome like :nome ");
		}
		String orderBy = " order by rot.nome ";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);
		if (notEmpty(entity.getSistema())) {
			query.setParameter("sistema", entity.getSistema());
		}
		if (notEmpty(entity.getNome())) {
			query.setParameter("nome", "%" + entity.getNome() + "%");
		}
		return query.getResultList();
	}

}