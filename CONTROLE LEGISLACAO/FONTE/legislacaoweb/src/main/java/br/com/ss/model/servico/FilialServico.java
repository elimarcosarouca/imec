package br.com.ss.model.servico;

import java.util.List;

import br.com.ss.model.entidade.Filial;

public interface FilialServico extends Servico<Filial, Long> {

	public List<Filial> pesquisar(Filial abstractEntity);

}