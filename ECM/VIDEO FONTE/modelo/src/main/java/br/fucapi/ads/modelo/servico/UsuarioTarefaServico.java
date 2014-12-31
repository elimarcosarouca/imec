package br.fucapi.ads.modelo.servico;

import java.util.List;

import br.fucapi.ads.modelo.dominio.UsuarioTarefa;

public interface UsuarioTarefaServico extends Servico<UsuarioTarefa, Long> {
	
	public List<UsuarioTarefa> pesquisar(UsuarioTarefa abstractEntity);

}