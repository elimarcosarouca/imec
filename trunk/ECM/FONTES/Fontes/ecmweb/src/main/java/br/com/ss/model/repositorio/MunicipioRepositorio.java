package br.com.ss.model.repositorio;

import java.util.List;

import br.com.ss.model.entidade.Municipio;

public interface MunicipioRepositorio extends
		GenericRepositorio<Municipio, Long> {

	List<Municipio> pesquisar(Municipio abstractEntity);

}