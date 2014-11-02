package br.com.ss.academico.servico;

import java.util.List;

import br.com.ss.academico.dominio.Aluno;
import br.com.ss.academico.dominio.Turma;
import br.com.ss.core.seguranca.servico.IService;

public interface AlunoServico extends IService<Aluno, Long> {

	public List<Aluno> findByTurma(Turma turma);

}