package br.com.ss.model.repositorio;

import java.util.List;

import br.com.ss.model.entidade.Legislacao;

public interface LegislacaoRepositorio extends
		GenericRepositorio<Legislacao, Long> {

	List<Legislacao> pesquisar(Legislacao abstractEntity);

}