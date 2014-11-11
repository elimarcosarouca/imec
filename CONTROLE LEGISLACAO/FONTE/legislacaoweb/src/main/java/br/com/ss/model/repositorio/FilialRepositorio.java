package br.com.ss.model.repositorio;

import java.util.List;

import br.com.ss.model.entidade.Filial;

public interface FilialRepositorio extends GenericRepositorio<Filial, Long> {

	List<Filial> pesquisar(Filial abstractEntity);

}