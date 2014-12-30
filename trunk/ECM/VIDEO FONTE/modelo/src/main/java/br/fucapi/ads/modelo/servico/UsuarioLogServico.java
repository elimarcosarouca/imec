package br.fucapi.ads.modelo.servico;

import java.util.List;

import br.fucapi.ads.modelo.dominio.UsuarioLog;

public interface UsuarioLogServico {

	public List<UsuarioLog> listarTodos();

	public UsuarioLog salvar(UsuarioLog usuarioLog);

}
