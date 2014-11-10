package br.com.ss.model.servico;

import java.util.List;

import br.com.ss.model.entidade.Emissor;

public interface EmissorServico extends Servico<Emissor, Long> {

	public List<Emissor> pesquisar(Emissor abstractEntity);

}