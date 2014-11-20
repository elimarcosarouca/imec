package br.com.ss.model.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ss.model.entidade.Estado;
import br.com.ss.model.repositorio.EstadoRepositorio;
import br.com.ss.model.repositorio.GenericRepositorio;
import br.com.ss.model.servico.EstadoServico;

@Service
@Transactional
public class EstadoServicoImpl extends GenericServicoImpl<Estado, Long>
		implements EstadoServico {

	private static final long serialVersionUID = -1380136082551143545L;

	@Autowired
	private EstadoRepositorio repositorio;

	@Override
	protected GenericRepositorio<Estado, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<Estado> pesquisar(Estado entity) {
		return repositorio.pesquisar(entity);
	}
}