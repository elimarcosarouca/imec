package br.com.ss.academico.servico;

import java.util.List;

import br.com.ss.academico.dominio.Curso;
import br.com.ss.academico.dominio.CursoDisciplina;
import br.com.ss.academico.dominio.Disciplina;

public interface CursoDisciplinaServico {

	public List<CursoDisciplina> listarTodos();

	public CursoDisciplina salvar(CursoDisciplina cursoDisciplina);

	public void remover(CursoDisciplina cursoDisciplina);

	public List<Disciplina> listaDisciplinaNotInCurso(Long idCurso);

	public List<CursoDisciplina> findByCurso(Curso curso);
	
}
