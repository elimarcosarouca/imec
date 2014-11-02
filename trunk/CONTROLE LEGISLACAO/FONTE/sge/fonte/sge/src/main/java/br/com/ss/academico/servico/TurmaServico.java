package br.com.ss.academico.servico;

import java.util.List;

import br.com.ss.academico.dominio.Matricula;
import br.com.ss.academico.dominio.Turma;
import br.com.ss.core.seguranca.servico.IService;

public interface TurmaServico extends IService<Turma, Long>  {

	public List<Turma> findByAno(Integer ano);

	public Turma findByMatricula(Matricula matricula);

	List<Turma> listarTurmasVigentes();

}