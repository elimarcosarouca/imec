package br.com.saa.servico;

import java.util.List;

import br.com.saa.modelo.entidade.Estado;

public interface EstadoServico extends IService<Estado, Long> {

	public List<Estado> findByNomeLike(String nome);

	public Estado findOne(Long primaryKey);

}