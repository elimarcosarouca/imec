package br.com.ss.model.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ss.model.entidade.Setor;
import br.com.ss.model.repositorio.GenericRepositorio;
import br.com.ss.model.repositorio.SetorRepositorio;
import br.com.ss.model.servico.SetorServico;

@Service
@Transactional
public class SetorServicoImpl extends GenericServico<Setor, Long> implements
		SetorServico {

	private static final long serialVersionUID = -1380136082551143545L;

	@Autowired
	private SetorRepositorio repositorio;

	@Override
	protected GenericRepositorio<Setor, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<Setor> pesquisar(Setor entity) {
		return repositorio.pesquisar(entity);
	}
}