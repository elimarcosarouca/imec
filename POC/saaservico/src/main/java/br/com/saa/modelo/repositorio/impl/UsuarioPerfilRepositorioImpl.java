package br.com.saa.modelo.repositorio.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.saa.modelo.entidade.Usuario;
import br.com.saa.modelo.entidade.UsuarioPerfil;
import br.com.saa.modelo.repositorio.UsuarioPerfilRepositorio;

@Repository
@SuppressWarnings("unchecked")
public class UsuarioPerfilRepositorioImpl extends
		GenericRepositorioImpl<UsuarioPerfil, Long> implements
		UsuarioPerfilRepositorio {

	@Override
	public Set<UsuarioPerfil> findByUsuario(Usuario usuario) {
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

		return (Set<UsuarioPerfil>) query.getResultList();
	}

}
