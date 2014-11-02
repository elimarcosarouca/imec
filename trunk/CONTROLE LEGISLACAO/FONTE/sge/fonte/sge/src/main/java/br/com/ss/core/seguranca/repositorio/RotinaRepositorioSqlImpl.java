package br.com.ss.core.seguranca.repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ss.core.seguranca.dominio.Rotina;

@SuppressWarnings("unchecked")
@Repository
public class RotinaRepositorioSqlImpl extends RepositorioGenerico implements RotinaRepositorioSql {

	@Override
	public List<Rotina> listaRotinasPorPerfil(Long id) {
		return entityManager
				.createNativeQuery(
						"SELECT i.* FROM saa_rotina i, saa_perfil_rotina b where i.id_rotina = b.id_rotina and id_perfil = " + id,
						Rotina.class).getResultList();

	}

	@Override
	public List<Rotina> pesquisar(Rotina entity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();
		
		sb.append(" select rot from Rotina rot ");
		
		if ( notEmpty(entity.getSistema()) ) {
			condictions.add(" rot.sistema = :sistema ");
		}
		if ( notEmpty(entity.getNome()) ) {
			condictions.add(" rot.nome like :nome ");
		}
		String orderBy = " order by rot.nome ";
		
		Query query = entityManager.createQuery(generateHql(sb.toString(), condictions) + orderBy);
		if ( notEmpty(entity.getSistema()) ) {
			query.setParameter("sistema", entity.getSistema());
		}
		if ( notEmpty(entity.getNome())) {
			query.setParameter("nome", "%" + entity.getNome() + "%");
		}
		return query.getResultList();
	}
	
	
}