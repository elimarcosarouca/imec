package br.com.saa.modelo.repositorio.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.saa.modelo.entidade.Perfil;
import br.com.saa.modelo.entidade.Sistema;
import br.com.saa.modelo.repositorio.PerfilRepositorio;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class PerfilRepositorioImpl extends
		GenericRepositorioImpl<Perfil, Serializable> implements
		PerfilRepositorio {

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

}