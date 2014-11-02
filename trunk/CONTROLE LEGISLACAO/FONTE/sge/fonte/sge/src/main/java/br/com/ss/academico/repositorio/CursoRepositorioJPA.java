package br.com.ss.academico.repositorio;

import java.util.List;

import br.com.ss.academico.dominio.Curso;

public interface CursoRepositorioJPA {

	List<Curso> findByNomeLike(String nome);

}