package br.com.ss.model.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ss.model.entidade.UsuarioSistema;
import br.com.ss.model.repositorio.GenericRepositorio;
import br.com.ss.model.repositorio.UsuarioSistemaRepositorio;
import br.com.ss.model.servico.UsuarioSistemaServico;

@Service
@Transactional
public class UsuarioSistemaServicoImpl extends
		GenericServicoImpl<UsuarioSistema, Long> implements
		UsuarioSistemaServico {

	private static final long serialVersionUID = -1380136082551143545L;

	@Autowired
	private UsuarioSistemaRepositorio repositorio;

	@Override
	protected GenericRepositorio<UsuarioSistema, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<UsuarioSistema> pesquisar(UsuarioSistema entity) {
		return repositorio.pesquisar(entity);
	}
}