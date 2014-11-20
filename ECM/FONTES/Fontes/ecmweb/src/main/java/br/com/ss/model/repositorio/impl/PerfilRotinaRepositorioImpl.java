package br.com.ss.model.repositorio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ss.model.entidade.PerfilRotina;
import br.com.ss.model.entidade.Rotina;
import br.com.ss.model.repositorio.PerfilRotinaRepositorio;

@Repository
@SuppressWarnings("unchecked")
public class PerfilRotinaRepositorioImpl extends
		GenericRepositorioImpl<PerfilRotina, Long> implements
		PerfilRotinaRepositorio {

	@Override
	public List<Rotina> listaRotinaNotInPerfil(Long idPerfil) {
		return entityManager.createNativeQuery(
				"select r.* from saa_rotina r " + "where r.id_rotina not in ( "
						+ "select id_rotina from saa_perfil_rotina pr "
						+ "where pr.id_perfil = " + idPerfil + ")",
				Rotina.class).getResultList();

	}

	@Override
	public List<PerfilRotina> pesquisar(PerfilRotina abstractEntity) {
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