package br.com.ss.academico.servico;

import java.util.List;

import br.com.ss.academico.dominio.Professor;
import br.com.ss.core.seguranca.servico.IService;

public interface ProfessorServico extends IService<Professor, Long>  {

	public List<Professor> listarTodos();

	public Professor salvar(Professor professor);

	public void remover(Professor professor);

	public List<Professor> findByNomeLike(String nome);

}