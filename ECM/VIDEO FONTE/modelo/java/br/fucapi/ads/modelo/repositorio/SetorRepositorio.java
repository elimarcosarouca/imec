package br.fucapi.ads.modelo.repositorio;

import java.util.List;

import br.fucapi.ads.modelo.dominio.Setor;

public interface SetorRepositorio extends GenericRepositorio<Setor, Long> {

	List<Setor> pesquisar(Setor abstractEntity);
}