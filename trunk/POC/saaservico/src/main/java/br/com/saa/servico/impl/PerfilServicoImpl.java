package br.com.saa.servico.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.saa.modelo.entidade.Perfil;
import br.com.saa.modelo.repositorio.GenericRepositorio;
import br.com.saa.modelo.repositorio.PerfilRepositorio;
import br.com.saa.servico.PerfilServico;

@Service
@Transactional
public class PerfilServicoImpl extends GenericServico<Perfil, Long> implements
		PerfilServico {

	private static final long serialVersionUID = 9126372622766341131L;

	@Autowired
	private PerfilRepositorio repositorio;

	@Override
	protected GenericRepositorio<Perfil, Long> getDao() {
		return repositorio;
	}

}
