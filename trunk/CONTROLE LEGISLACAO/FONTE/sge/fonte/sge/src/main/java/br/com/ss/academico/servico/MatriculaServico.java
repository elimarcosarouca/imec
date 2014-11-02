package br.com.ss.academico.servico;

import java.util.Date;
import java.util.List;

import br.com.ss.academico.dominio.Aluno;
import br.com.ss.academico.dominio.Matricula;
import br.com.ss.academico.dominio.Observacao;
import br.com.ss.academico.dominio.Turma;
import br.com.ss.core.seguranca.servico.IService;

public interface MatriculaServico extends IService<Matricula, Long> {

	public List<Matricula> findByAluno(Aluno aluno);

	public Long countVagasDisponiveis(Turma turma);

	public Matricula loadMatriculaMensalidades(Matricula matricula);

	public List<Observacao> loadObservacoes(Matricula entidade);
	
	public List<Matricula> listarAniversariantes(Date dataInicial,
			Date dataFinal);
	
}