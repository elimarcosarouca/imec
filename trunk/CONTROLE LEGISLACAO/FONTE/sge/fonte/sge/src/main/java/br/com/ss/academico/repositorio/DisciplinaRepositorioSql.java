package br.com.ss.academico.repositorio;

import java.util.List;

import br.com.ss.academico.dominio.Disciplina;

public interface DisciplinaRepositorioSql {

	public List<Disciplina> listaDisciplinaPorCurso(Long idCurso);

	public List<Disciplina> pesquisar(Disciplina entity);

}