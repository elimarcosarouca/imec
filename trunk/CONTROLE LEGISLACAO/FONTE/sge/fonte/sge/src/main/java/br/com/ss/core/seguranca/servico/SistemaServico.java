package br.com.ss.core.seguranca.servico;

import java.util.List;

import br.com.ss.core.seguranca.dominio.Sistema;

public interface SistemaServico extends IService<Sistema, Long> {

	public List<Sistema> findByNomeLike(String nome);

	public Sistema findByCodigo(String codigo);

}