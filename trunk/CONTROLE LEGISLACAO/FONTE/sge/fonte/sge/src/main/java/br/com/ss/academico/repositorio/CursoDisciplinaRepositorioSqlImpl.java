package br.com.ss.academico.repositorio;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.ss.academico.dominio.Disciplina;
import br.com.ss.core.seguranca.repositorio.RepositorioGenerico;

@Repository
public class CursoDisciplinaRepositorioSqlImpl extends RepositorioGenerico implements CursoDisciplinaRepositorioSql {

	@SuppressWarnings("unchecked")
	@Override
	public List<Disciplina> listaDisciplinaNotInCurso(Long idCurso) {
		return entityManager.createNativeQuery(
				"select d.* from acad_disciplina d "
						+ "where d.id_disciplina not in ( "
						+ "select id_disciplina from acad_curso_disciplina cd "
						+ "where cd.id_curso = " + idCurso + ")",
				Disciplina.class).getResultList();

	}
}