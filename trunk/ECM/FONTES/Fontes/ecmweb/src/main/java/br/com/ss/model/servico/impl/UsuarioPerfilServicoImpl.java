package br.com.ss.model.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ss.model.entidade.UsuarioPerfil;
import br.com.ss.model.repositorio.GenericRepositorio;
import br.com.ss.model.repositorio.UsuarioPerfilRepositorio;
import br.com.ss.model.servico.UsuarioPerfilServico;

@Service
@Transactional
public class UsuarioPerfilServicoImpl extends
		GenericServicoImpl<UsuarioPerfil, Long> implements UsuarioPerfilServico {

	private static final long serialVersionUID = 8425684760628329523L;

	@Autowired
	private UsuarioPerfilRepositorio repositorio;

	@Override
	protected GenericRepositorio<UsuarioPerfil, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<UsuarioPerfil> pesquisar(UsuarioPerfil entity) {
		return repositorio.pesquisar(entity);
	}
}