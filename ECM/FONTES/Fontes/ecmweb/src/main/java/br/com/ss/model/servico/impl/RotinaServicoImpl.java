package br.com.ss.model.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ss.model.entidade.Rotina;
import br.com.ss.model.repositorio.GenericRepositorio;
import br.com.ss.model.repositorio.RotinaRepositorio;
import br.com.ss.model.servico.RotinaServico;

@Service
@Transactional
public class RotinaServicoImpl extends GenericServicoImpl<Rotina, Long>
		implements RotinaServico {

	private static final long serialVersionUID = -1380136082551143545L;

	@Autowired
	private RotinaRepositorio repositorio;

	@Override
	protected GenericRepositorio<Rotina, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<Rotina> pesquisar(Rotina entity) {
		return repositorio.pesquisar(entity);
	}
}