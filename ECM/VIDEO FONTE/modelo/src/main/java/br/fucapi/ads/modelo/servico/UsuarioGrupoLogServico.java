package br.fucapi.ads.modelo.servico;

import java.util.List;

import br.fucapi.ads.modelo.dominio.UsuarioGrupoLog;

public interface UsuarioGrupoLogServico {

	public List<UsuarioGrupoLog> listarTodos();

	public UsuarioGrupoLog salvar(UsuarioGrupoLog usuarioGrupoLog);

}
