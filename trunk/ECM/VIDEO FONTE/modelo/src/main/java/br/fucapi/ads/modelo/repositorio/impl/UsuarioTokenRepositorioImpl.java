package br.fucapi.ads.modelo.repositorio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.fucapi.ads.modelo.dominio.UsuarioToken;
import br.fucapi.ads.modelo.repositorio.UsuarioTokenRepositorio;

@Repository
public class UsuarioTokenRepositorioImpl extends
		GenericRepositorioImpl<UsuarioToken, Long> implements
		UsuarioTokenRepositorio {

	@Override
	public UsuarioToken pesquisarPorToken(String token) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from UsuarioToken est ");

		if (notEmpty(token)) {
			condictions.add(" est.token = :token ");
		}

		String orderBy = " order by est.token";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);

		if (notEmpty(token)) {
			query.setParameter("token", token);
		}

		return (UsuarioToken) query.getSingleResult();
	}
}