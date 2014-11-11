package br.com.ss.model.repositorio;

import java.util.List;

import br.com.ss.model.entidade.Requisito;

public interface RequisitoRepositorio extends
		GenericRepositorio<Requisito, Long> {

	List<Requisito> pesquisar(Requisito abstractEntity);

}