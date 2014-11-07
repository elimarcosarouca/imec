package br.com.saa.servico.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.saa.modelo.entidade.UsuarioPerfil;
import br.com.saa.modelo.repositorio.GenericRepositorio;
import br.com.saa.modelo.repositorio.UsuarioPerfilRepositorio;
import br.com.saa.servico.UsuarioPerfilServico;

@Service
@Transactional
public class UsuarioPerfilServicoImpl extends
		GenericServico<UsuarioPerfil, Long> implements UsuarioPerfilServico {

	private static final long serialVersionUID = 9126372622766341131L;

	@Autowired
	private UsuarioPerfilRepositorio repositorio;

	@Override
	protected GenericRepositorio<UsuarioPerfil, Long> getDao() {
		return repositorio;
	}

}