package br.com.ss.model.repositorio;

import java.util.List;

import br.com.ss.model.entidade.Unidade;

public interface UnidadeRepositorio extends GenericRepositorio<Unidade, Long> {

	List<Unidade> pesquisar(Unidade abstractEntity);
}