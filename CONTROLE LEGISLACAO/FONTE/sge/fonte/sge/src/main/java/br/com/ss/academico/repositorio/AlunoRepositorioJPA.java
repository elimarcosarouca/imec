package br.com.ss.academico.repositorio;

import java.util.List;

import br.com.ss.academico.dominio.Aluno;

public interface AlunoRepositorioJPA {

	List<Aluno> findByEntity(Aluno aluno);

}