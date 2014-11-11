package br.com.ss.model.servico;

import java.util.List;

import br.com.ss.model.entidade.Legislacao;

public interface LegislacaoServico extends Servico<Legislacao, Long> {

	public List<Legislacao> pesquisar(Legislacao abstractEntity);

}