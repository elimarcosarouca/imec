package br.com.ss.model.repositorio;

import java.util.List;

import br.com.ss.model.entidade.Licenca;

public interface LicencaRepositorio extends GenericRepositorio<Licenca, Long> {

	List<Licenca> pesquisar(Licenca abstractEntity);

}