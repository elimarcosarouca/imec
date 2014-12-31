package br.fucapi.ads.modelo.repositorio;

import java.util.List;

import br.fucapi.ads.modelo.dominio.GrupoLog;

public interface GrupoLogRepositorio extends GenericRepositorio<GrupoLog, Long> {
	List<GrupoLog> pesquisar(GrupoLog abstractEntity);

}
