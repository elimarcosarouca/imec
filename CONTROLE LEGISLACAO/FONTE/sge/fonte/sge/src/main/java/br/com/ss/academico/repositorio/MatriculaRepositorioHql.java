package br.com.ss.academico.repositorio;

import java.util.Date;
import java.util.List;

import br.com.ss.academico.dominio.Matricula;
import br.com.ss.academico.dominio.Observacao;

public interface MatriculaRepositorioHql {

	public List<Matricula> pesquisar(Matricula matricula);

	public List<Observacao> loadObservacoes(Matricula matricula);
	
	public List<Matricula> listarAniversariantes(Date dataInicial, Date dataFinal);
	
}
