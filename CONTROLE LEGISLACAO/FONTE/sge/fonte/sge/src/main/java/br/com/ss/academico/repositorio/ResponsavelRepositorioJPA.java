package br.com.ss.academico.repositorio;

import java.util.List;

import br.com.ss.academico.dominio.Responsavel;

public interface ResponsavelRepositorioJPA {

	List<Responsavel> findByNomeLike(String nome);

	Responsavel findByNome(String nome);

	List<Responsavel> pesquisar(Responsavel entity, String nomeAluno);

}