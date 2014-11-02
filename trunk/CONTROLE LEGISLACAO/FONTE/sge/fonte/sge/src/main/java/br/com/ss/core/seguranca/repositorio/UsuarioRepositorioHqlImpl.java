package br.com.ss.core.seguranca.repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ss.core.seguranca.dominio.Usuario;

@SuppressWarnings("unchecked")
@Repository
public class UsuarioRepositorioHqlImpl extends RepositorioGenerico implements UsuarioRepositorioHql {

	@Override
	public List<Usuario> pesquisar(Usuario entity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();
		
		sb.append(" select ent from Usuario ent ");

		// nao deve exibir o usuario master
		condictions.add(" lower( ent.login ) <> 'master' ");
		
		if ( notEmpty(entity.getNome())) {
			condictions.add(" lower( ent.nome ) like :nome ");
		}
		if ( notEmpty(entity.getLogin())) {
			condictions.add(" lower( ent.login ) like :login ");
		}
		if ( notEmpty(entity.getStatus()) ) {
			condictions.add(" ent.status = :status ");
		}
		if ( notEmpty(entity.getTipoUsuario()) ) {
			condictions.add(" ent.tipoUsuario = :tipoUsuario ");
		}
		String orderBy = " order by ent.nome ";
		
		Query query = entityManager.createQuery(generateHql(sb.toString(), condictions) + orderBy);
		if ( notEmpty(entity.getNome())) {
			query.setParameter("nome", "%" + entity.getNome() + "%");
		}
		if ( notEmpty(entity.getLogin())) {
			query.setParameter("login", "%" + entity.getLogin() + "%");
		}
		if ( notEmpty(entity.getStatus()) ) {
			query.setParameter("status", entity.getStatus());
		}
		if ( notEmpty(entity.getTipoUsuario()) ) {
			query.setParameter("tipoUsuario", entity.getTipoUsuario());
		}
		return query.getResultList();
	}
	
	
}