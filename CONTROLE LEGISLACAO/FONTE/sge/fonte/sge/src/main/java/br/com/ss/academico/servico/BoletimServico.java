package br.com.ss.academico.servico;

import java.util.List;

import br.com.ss.academico.dominio.Boletim;
import br.com.ss.academico.dominio.Matricula;
import br.com.ss.academico.dominio.Turma;
import br.com.ss.core.seguranca.servico.IService;

public interface BoletimServico extends IService<Boletim, Long>  {

	public List<Boletim> pesquisarBoletim(Matricula matricula);
	
	public void gerarBoletim(Matricula matricula);

	public List<Boletim> listBoletimPorTurma(Turma turma);
}