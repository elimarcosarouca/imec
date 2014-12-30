package br.fucapi.ads.modelo.servico;

import java.util.List;

import br.fucapi.ads.modelo.dominio.UsuarioTarefa;

public interface UsuarioTarefaServico {

	public List<UsuarioTarefa> listarTodos();

	public UsuarioTarefa salvar(UsuarioTarefa usuarioTarefa);

	public UsuarioTarefa alterar(UsuarioTarefa usuarioTarefa);

	public void remover(UsuarioTarefa usuarioTarefa);
	

}
