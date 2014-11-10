package br.com.ss.model.repositorio;

import java.util.List;

import br.com.ss.model.entidade.Emissor;

public interface EmissorRepositorio extends GenericRepositorio<Emissor, Long> {

	List<Emissor> pesquisar(Emissor abstractEntity);

}