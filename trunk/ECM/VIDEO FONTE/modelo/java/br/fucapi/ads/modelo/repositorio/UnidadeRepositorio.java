package br.fucapi.ads.modelo.repositorio;

import java.util.List;

import br.fucapi.ads.modelo.dominio.Unidade;

public interface UnidadeRepositorio extends GenericRepositorio<Unidade, Long> {

	List<Unidade> pesquisar(Unidade abstractEntity);
}