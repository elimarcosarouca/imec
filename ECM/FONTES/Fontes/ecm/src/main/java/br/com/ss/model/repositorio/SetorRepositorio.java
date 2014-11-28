package br.com.ss.model.repositorio;

import java.util.List;

import br.com.ss.model.entidade.Setor;

public interface SetorRepositorio extends GenericRepositorio<Setor, Long> {

	List<Setor> pesquisar(Setor abstractEntity);
}