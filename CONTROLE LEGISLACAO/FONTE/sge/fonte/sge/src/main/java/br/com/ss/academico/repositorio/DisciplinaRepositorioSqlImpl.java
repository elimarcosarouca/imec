package br.com.ss.academico.repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ss.academico.dominio.Disciplina;
import br.com.ss.core.seguranca.repositorio.RepositorioGenerico;

@Repository
public class DisciplinaRepositorioSqlImpl extends RepositorioGenerico implements DisciplinaRepositorioSql {

	@SuppressWarnings("unchecked")
	@Override
	public List<Disciplina> listaDisciplinaPorCurso(Long idCurso) {
		
		String sql = "SELECT d.* FROM acad_disciplina d, acad_curso_disciplina b "
				+ " where d.id_disciplina = b.id_disciplina and b.id_curso = " + idCurso;
		
		return entityManager.createNativeQuery(sql, Disciplina.class).getResultList();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Disciplina> pesquisar(Disciplina entity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();
		sb.append(" select disc from Disciplina disc ");
		if ( notEmpty(entity.getNome())) {
			condictions.add(" lower( disc.nome ) like :nome ");
		}
		String orderBy = " order by disc.nome ";
		Query query = entityManager.createQuery(generateHql(sb.toString(), condictions) + orderBy);
		if ( notEmpty(entity.getNome())) {
			query.setParameter("nome", "%" + entity.getNome() + "%");
		}
		return query.getResultList();
	}

}