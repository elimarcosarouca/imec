package br.com.ss.academico.servico;

import java.util.List;

import br.com.ss.academico.dominio.Disciplina;
import br.com.ss.core.seguranca.servico.IService;

public interface DisciplinaServico extends IService<Disciplina, Long> {

	public List<Disciplina> findByNomeLike(String nome);
	
	public List<Disciplina> listaDisciplinaPorCurso(Long idCurso);

}