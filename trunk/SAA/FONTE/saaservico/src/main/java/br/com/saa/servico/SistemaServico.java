package br.com.saa.servico;

import java.util.List;

import br.com.saa.modelo.entidade.Sistema;

public interface SistemaServico extends IService<Sistema, Long> {

	public List<Sistema> findByNomeLike(String nome);

	public Sistema findByCodigo(String codigo);

}