package br.com.ss.model.servico;

import java.util.List;

import br.com.ss.model.entidade.Requisito;

public interface RequisitoServico extends Servico<Requisito, Long> {

	public List<Requisito> pesquisar(Requisito abstractEntity);

}