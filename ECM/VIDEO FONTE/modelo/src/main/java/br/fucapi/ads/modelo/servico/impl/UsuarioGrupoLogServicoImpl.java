package br.fucapi.ads.modelo.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.ads.modelo.dominio.UsuarioGrupoLog;
import br.fucapi.ads.modelo.repositorio.GenericRepositorio;
import br.fucapi.ads.modelo.repositorio.UsuarioGrupoLogRepositorio;
import br.fucapi.ads.modelo.servico.UsuarioGrupoLogServico;

@Service
@Transactional
public class UsuarioGrupoLogServicoImpl extends
		GenericServico<UsuarioGrupoLog, Long> implements UsuarioGrupoLogServico {

	private static final long serialVersionUID = -1380136082551143545L;

	@Autowired
	private UsuarioGrupoLogRepositorio repositorio;

	@Override
	protected GenericRepositorio<UsuarioGrupoLog, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<UsuarioGrupoLog> pesquisar(UsuarioGrupoLog entity) {
		return repositorio.pesquisar(entity);
	}
}