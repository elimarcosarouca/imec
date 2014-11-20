package br.com.ss.model.repositorio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ss.model.entidade.Usuario;
import br.com.ss.model.entidade.UsuarioPerfil;
import br.com.ss.model.repositorio.UsuarioPerfilRepositorio;

@Repository
@SuppressWarnings("unchecked")
public class UsuarioPerfilRepositorioImpl extends
		GenericRepositorioImpl<UsuarioPerfil, Long> implements
		UsuarioPerfilRepositorio {

	@Override
	public List<UsuarioPerfil> findByUsuario(Usuario usuario) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select usuper from UsuarioPerfil usuper ");

		if (notEmpty(usuario)) {
			condictions.add(" rot.usuario = :usuario ");
		}

		String orderBy = " order by rot.perfil.nome ";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);

		if (notEmpty(usuario)) {
			query.setParameter("usuario", usuario);
		}

		return query.getResultList();
	}

	@Override
	public List<UsuarioPerfil> pesquisar(UsuarioPerfil abstractEntity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from UsuarioPerfil est ");
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