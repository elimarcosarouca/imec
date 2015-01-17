package br.fucapi.ads.modelo.repositorio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.fucapi.ads.modelo.dominio.Notificacao;
import br.fucapi.ads.modelo.repositorio.NotificacaoRepositorio;

@Repository
@SuppressWarnings("unchecked")
public class NotificacaoRepositorioImpl extends
		GenericRepositorioImpl<Notificacao, Long> implements
		NotificacaoRepositorio {

	@Override
	public List<Notificacao> pesquisar(Notificacao abstractEntity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from Notificacao est ");

		if (notEmpty(abstractEntity.getLogin())) {
			condictions.add(" est.login  :login ");
		}

		String orderBy = " order by est.login";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);
		if (notEmpty(abstractEntity.getLogin())) {
			query.setParameter("login",abstractEntity.getLogin());
		}

		return query.getResultList();
	}

	@Override
	public List<Notificacao> findByDataLeituraIsNullAndLogin(String login) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from Notificacao est ");

		if (notEmpty(login)) {
			condictions.add(" est.login = :login ");
		}
		
		condictions.add(" est.data is null ");

		String orderBy = " order by est.login";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);
		if (notEmpty(login)) {
			query.setParameter("login",
					login.trim().toLowerCase() );
		}

		return query.getResultList();
	}

}