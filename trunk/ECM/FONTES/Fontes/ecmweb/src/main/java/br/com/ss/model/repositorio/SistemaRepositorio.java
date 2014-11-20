package br.com.ss.model.repositorio;

import java.util.List;

import br.com.ss.model.entidade.Sistema;

public interface SistemaRepositorio extends GenericRepositorio<Sistema, Long> {

	List<Sistema> pesquisar(Sistema abstractEntity);

}