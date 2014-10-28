package br.com.saa.modelo.repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.saa.modelo.entidade.Usuario;

@SuppressWarnings("unchecked")
@Repository
public class UsuarioRepositorioHqlImpl extends RepositorioGenerico implements
		UsuarioRepositorioHql {

	@Override
	public List<Usuario> pesquisar(Usuario entity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select ent from Usuario ent ");

		// nao deve exibir o usuario master
		condictions.add(" lower( ent.login ) <> 'master' ");

		if (notEmpty(entity.getNomeRazaoSocial())) {
			condictions
					.add(" lower( ent.nomeRazaoSocial ) like :nomeRazaoSocial ");
		}
		if (notEmpty(entity.getCpfCnpj())) {
			condictions.add(" lower( ent.cpfCnpj ) like :cpfCnpj ");
		}
		if (notEmpty(entity.getStatus())) {
			condictions.add(" ent.status = :status ");
		}

		String orderBy = " order by ent.nome ";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);
		if (notEmpty(entity.getNomeRazaoSocial())) {
			query.setParameter("nomeRazaoSocial",
					"%" + entity.getNomeRazaoSocial() + "%");
		}
		if (notEmpty(entity.getCpfCnpj())) {
			query.setParameter("cpfCnpj", "%" + entity.getCpfCnpj() + "%");
		}
		if (notEmpty(entity.getStatus())) {
			query.setParameter("status", entity.getStatus());
		}

		return query.getResultList();
	}

}