package br.com.ss.academico.repositorio;

import java.util.List;

import br.com.ss.academico.dominio.Boletim;
import br.com.ss.academico.dominio.Turma;

public interface BoletimRepositorioJPA {

	public List<Boletim> listaBoletimPorTurma(Turma turma);
	
}
