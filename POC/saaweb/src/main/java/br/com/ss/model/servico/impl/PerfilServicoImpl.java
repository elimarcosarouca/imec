package br.com.ss.model.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ss.model.entidade.Perfil;
import br.com.ss.model.repositorio.GenericRepositorio;
import br.com.ss.model.repositorio.PerfilRepositorio;
import br.com.ss.model.servico.PerfilServico;

@Service
@Transactional
public class PerfilServicoImpl extends GenericServicoImpl<Perfil, Long>
		implements PerfilServico {

	private static final long serialVersionUID = -4850219477696978445L;

	@Autowired
	private PerfilRepositorio repositorio;

	@Override
	protected GenericRepositorio<Perfil, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<Perfil> pesquisar(Perfil entity) {
		return repositorio.pesquisar(entity);
	}
}