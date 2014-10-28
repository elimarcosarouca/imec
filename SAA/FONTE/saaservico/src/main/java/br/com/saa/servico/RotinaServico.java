package br.com.saa.servico;

import java.util.List;

import br.com.saa.modelo.entidade.Rotina;

public interface RotinaServico extends IService<Rotina, Long> {

	public List<Rotina> listaRotinasPorPerfil(Long id);

	public List<Rotina> findByNomeLike(String nome);

}