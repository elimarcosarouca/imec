package br.fucapi.ads.modelo.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.ads.modelo.dominio.UsuarioTarefa;
import br.fucapi.ads.modelo.repositorio.UsuarioTarefaRepositorio;

@Service
public class UsuarioTarefaServicoImpl implements UsuarioTarefaServico {

	@Autowired
	UsuarioTarefaRepositorio usuarioTarefaRepositorio;

	@Override
	public List<UsuarioTarefa> listarTodos() {
		return usuarioTarefaRepositorio.findAll();
	}

	@Override
	@Transactional
	public UsuarioTarefa salvar(UsuarioTarefa usuarioTarefa) {
		return usuarioTarefaRepositorio.saveAndFlush(usuarioTarefa);
	}

	@Override
	public UsuarioTarefa alterar(UsuarioTarefa usuarioTarefa) {
		return null;
	}

	@Override
	public void remover(UsuarioTarefa usuarioTarefa) {
		usuarioTarefaRepositorio.delete(usuarioTarefa);
	}

}
