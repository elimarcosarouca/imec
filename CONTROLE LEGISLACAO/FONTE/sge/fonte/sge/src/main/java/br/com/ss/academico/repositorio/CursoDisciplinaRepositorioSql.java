package br.com.ss.academico.repositorio;

import java.util.List;

import br.com.ss.academico.dominio.Disciplina;

public interface CursoDisciplinaRepositorioSql {

	public List<Disciplina> listaDisciplinaNotInCurso(Long idCurso);

}