package br.com.ss.model.repositorio;

import java.util.List;

import br.com.ss.model.entidade.Estado;

public interface EstadoRepositorio extends GenericRepositorio<Estado, Long> {

	List<Estado> pesquisar(Estado abstractEntity);

}