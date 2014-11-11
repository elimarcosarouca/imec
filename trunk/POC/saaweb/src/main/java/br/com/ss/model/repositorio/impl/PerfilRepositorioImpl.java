package br.com.ss.model.repositorio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ss.model.entidade.Perfil;
import br.com.ss.model.entidade.Sistema;
import br.com.ss.model.repositorio.PerfilRepositorio;

@Repository
@SuppressWarnings("unchecked")
public class PerfilRepositorioImpl extends GenericRepositorioImpl<Perfil, Long>
		implements PerfilRepositorio {

	@Override
	public List<Perfil> listaPerfilPorSistemaPorUsuario(int sistemaId,
			Long usuarioId) {
		return entityManager.createNativeQuery(
				"SELECT distinct i.* FROM saa_perfil i, saa_usuario_perfil b "
						+ "where i.id_perfil = b.id_perfil and i.id_sistema = "
						+ sistemaId + " and b.id_usuario =  " + usuarioId,
				Perfil.class).getResultList();
	}

	@Override
	public List<Perfil> listaPerfil(Sistema sistema) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select per from Perfil per ");

		if (notEmpty(sistema)) {
			condictions.add(" per.sistema = :sistema ");
		}
		String orderBy = " order by per.nome ";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);
		if (notEmpty(sistema)) {
			query.setParameter("sistema", sistema);
		}
		return query.getResultList();
	}

	@Override
	public List<Perfil> pesquisar(Perfil abstractEntity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from Perfil est ");

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