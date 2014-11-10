package br.com.ss.model.servico;

import java.util.List;

import br.com.ss.model.entidade.Estado;

public interface EstadoServico extends Servico<Estado, Long> {
	
	public List<Estado> pesquisar(Estado abstractEntity);

}