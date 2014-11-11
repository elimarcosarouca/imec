package br.com.ss.model.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ss.model.entidade.Usuario;
import br.com.ss.model.repositorio.GenericRepositorio;
import br.com.ss.model.repositorio.UsuarioRepositorio;
import br.com.ss.model.servico.UsuarioServico;

@Service
@Transactional
public class UsuarioServicoImpl extends GenericServicoImpl<Usuario, Long>
		implements UsuarioServico {

	private static final long serialVersionUID = -5614492347870085066L;

	@Autowired
	private UsuarioRepositorio repositorio;

	@Override
	protected GenericRepositorio<Usuario, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<Usuario> pesquisar(Usuario entity) {
		return repositorio.pesquisar(entity);
	}
}