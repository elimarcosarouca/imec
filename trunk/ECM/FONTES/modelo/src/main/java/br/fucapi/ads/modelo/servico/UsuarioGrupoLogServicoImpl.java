package br.fucapi.ads.modelo.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.ads.modelo.dominio.UsuarioGrupoLog;
import br.fucapi.ads.modelo.repositorio.UsuarioGrupoLogRepositorio;

@Service
public class UsuarioGrupoLogServicoImpl implements UsuarioGrupoLogServico {

	@Autowired
	UsuarioGrupoLogRepositorio repositorio;

	@Override
	public List<UsuarioGrupoLog> listarTodos() {
		return repositorio.findAll();
	}

	@Override
	@Transactional
	public UsuarioGrupoLog salvar(UsuarioGrupoLog usuarioGrupoLog) {
		return repositorio.saveAndFlush(usuarioGrupoLog);
	}

}
