package br.fucapi.ads.modelo.repositorio;

import java.util.List;

import br.fucapi.ads.modelo.dominio.Colaborador;

public interface ColaboradorRepositorio extends
		GenericRepositorio<Colaborador, Long> {

	List<Colaborador> pesquisar(Colaborador abstractEntity);
}