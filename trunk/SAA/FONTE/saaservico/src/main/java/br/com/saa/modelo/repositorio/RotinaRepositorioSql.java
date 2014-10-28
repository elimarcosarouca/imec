package br.com.saa.modelo.repositorio;

import java.util.List;

import br.com.saa.modelo.entidade.Rotina;

public interface RotinaRepositorioSql {

	public List<Rotina> listaRotinasPorPerfil(Long id);

	public List<Rotina> pesquisar(Rotina entity);

}