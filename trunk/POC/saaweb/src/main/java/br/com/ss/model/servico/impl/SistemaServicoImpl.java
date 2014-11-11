package br.com.ss.model.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ss.model.entidade.Sistema;
import br.com.ss.model.repositorio.GenericRepositorio;
import br.com.ss.model.repositorio.SistemaRepositorio;
import br.com.ss.model.servico.SistemaServico;

@Service
@Transactional
public class SistemaServicoImpl extends GenericServicoImpl<Sistema, Long>
		implements SistemaServico {

	private static final long serialVersionUID = -5614492347870085066L;

	@Autowired
	private SistemaRepositorio repositorio;

	@Override
	protected GenericRepositorio<Sistema, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<Sistema> pesquisar(Sistema entity) {
		return repositorio.pesquisar(entity);
	}
}