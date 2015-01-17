package br.fucapi.ads.modelo.servico;

import java.util.List;

import br.fucapi.ads.modelo.dominio.UsuarioGrupoLog;

public interface UsuarioGrupoLogServico extends Servico<UsuarioGrupoLog, Long> {

	List<UsuarioGrupoLog> pesquisar(UsuarioGrupoLog abstractEntity);

}