package br.com.ss.academico.repositorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ss.academico.dominio.Turma;
import br.com.ss.core.seguranca.repositorio.RepositorioGenerico;
import br.com.ss.core.web.utils.DateUtil;

@SuppressWarnings("unchecked")
@Repository
public class TurmaRepositorioHqlImpl extends RepositorioGenerico implements TurmaRepositorioHql{
	
	@Override
	public List<Turma> pesquisar(Turma entity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();
		
		sb.append(" select t from Turma t ");
		
		if ( notEmpty(entity.getTurno()) ) {
			condictions.add(" t.turno = :turno ");
		}
		if ( notEmpty(entity.getAno() ) && entity.getAno() > 0 ) {
			condictions.add(" t.ano = :ano ");
		}
		if ( notEmpty(entity.getCurso()) ) {
			condictions.add(" t.curso = :curso ");
		}
		String orderBy = " order by t.ano ";
		
		Query query = entityManager.createQuery(generateHql(sb.toString(), condictions) + orderBy);
		if ( notEmpty(entity.getTurno()) ) {
			query.setParameter("turno", entity.getTurno());
		}
		if ( notEmpty(entity.getAno()) && entity.getAno() > 0) {
			query.setParameter("ano", entity.getAno());
		}
		if ( notEmpty(entity.getCurso()) ) {
			query.setParameter("curso", entity.getCurso());
		}
		return query.getResultList();
	}

	@Override
	public List<Turma> listarTurmasVigentes() {
		StringBuilder sb = new StringBuilder();
		sb.append(" select t from Turma t ");
		sb.append(" where t.ano >= :ano ");
		sb.append(" order by t.ano, t.curso.nome ");
		
		Query query = entityManager.createQuery( sb.toString() );
		query.setParameter("ano", DateUtil.getAno(new Date()));
		
		return query.getResultList();
	}

}