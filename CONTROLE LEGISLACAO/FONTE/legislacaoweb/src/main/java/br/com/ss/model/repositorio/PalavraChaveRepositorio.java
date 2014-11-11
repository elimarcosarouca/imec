package br.com.ss.model.repositorio;

import java.util.List;

import br.com.ss.model.entidade.PalavraChave;

public interface PalavraChaveRepositorio extends
		GenericRepositorio<PalavraChave, Long> {

	List<PalavraChave> pesquisar(PalavraChave abstractEntity);

}