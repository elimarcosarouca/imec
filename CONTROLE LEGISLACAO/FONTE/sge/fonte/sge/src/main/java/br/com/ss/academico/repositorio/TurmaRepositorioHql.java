package br.com.ss.academico.repositorio;

import java.util.List;

import br.com.ss.academico.dominio.Turma;

public interface TurmaRepositorioHql {

	public List<Turma> pesquisar(Turma entity);

	public List<Turma> listarTurmasVigentes();
	
}
