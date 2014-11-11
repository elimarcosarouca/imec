package br.com.ss.model.servico;

import java.util.List;

import br.com.ss.model.entidade.PalavraChave;

public interface PalavraChaveServico extends Servico<PalavraChave, Long> {

	public List<PalavraChave> pesquisar(PalavraChave abstractEntity);

}