package br.fucapi.ads.modelo.servico;

import br.fucapi.ads.modelo.dominio.VariaveisTarefa;

public interface VariaveisTarefaServico {

	public VariaveisTarefa salvar(VariaveisTarefa variaveisTarefa);

	public VariaveisTarefa findById(Long id);
}