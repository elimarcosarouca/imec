package br.com.saa.servico.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.saa.modelo.entidade.Usuario;
import br.com.saa.modelo.repositorio.GenericRepositorio;
import br.com.saa.modelo.repositorio.UsuarioRepositorio;
import br.com.saa.servico.UsuarioServico;

@Service
@Transactional
public class UsuarioServicoImpl extends GenericServico<Usuario, Long> implements
		UsuarioServico {

	private static final long serialVersionUID = 9126372622766341131L;

	@Autowired
	private UsuarioRepositorio repositorio;

	@Override
	protected GenericRepositorio<Usuario, Long> getDao() {
		return repositorio;
	}

}