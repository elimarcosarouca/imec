package br.com.ss.model.servico;

import java.util.List;

import br.com.ss.model.entidade.Licenca;

public interface LicencaServico extends Servico<Licenca, Long> {

	public List<Licenca> pesquisar(Licenca abstractEntity);

}