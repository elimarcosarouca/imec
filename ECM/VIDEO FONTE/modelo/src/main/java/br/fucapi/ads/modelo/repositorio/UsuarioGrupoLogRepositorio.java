package br.fucapi.ads.modelo.repositorio;

import java.util.List;

import br.fucapi.ads.modelo.dominio.UsuarioGrupoLog;

public interface UsuarioGrupoLogRepositorio extends
		GenericRepositorio<UsuarioGrupoLog, Long> {

	List<UsuarioGrupoLog> pesquisar(UsuarioGrupoLog abstractEntity);

}