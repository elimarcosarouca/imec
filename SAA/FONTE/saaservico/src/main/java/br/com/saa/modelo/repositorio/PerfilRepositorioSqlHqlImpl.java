package br.com.saa.modelo.repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.saa.modelo.entidade.Perfil;

@SuppressWarnings("unchecked")
@Repository
public class PerfilRepositorioSqlHqlImpl extends RepositorioGenerico implements
		PerfilRepositorioSqlHql {

	@Override
	public List<Perfil> listaPerfilPorSistemaPorUsuario(int sistemaId,
			Long usuarioId) {
		return entityManager.createNativeQuery(
				"SELECT distinct i.* FROM saa_perfil i, saa_usuario_perfil b "
						+ "where i.id_perfil = b.id_perfil and i.id_sistema = "
						+ sistemaId + " and b.id_usuario =  " + usuarioId,
				Perfil.class).getResultList();
	}

	public List<Perfil> listaPerfil(Perfil perfil) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select per from Perfil per ");

		if (notEmpty(perfil.getSistema())) {
			condictions.add(" per.sistema = :sistema ");
		}
		if (notEmpty(perfil.getNome())) {
			condictions.add(" per.nome like :nome ");
		}
		String orderBy = " order by per.nome ";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);
		if (notEmpty(perfil.getSistema())) {
			query.setParameter("sistema", perfil.getSistema());
		}
		if (notEmpty(perfil.getNome())) {
			query.setParameter("nome", "%" + perfil.getNome() + "%");
		}
		return query.getResultList();
	}

}