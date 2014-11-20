package br.com.ss.model.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ss.model.entidade.Unidade;
import br.com.ss.model.repositorio.GenericRepositorio;
import br.com.ss.model.repositorio.UnidadeRepositorio;
import br.com.ss.model.servico.UnidadeServico;

@Service
@Transactional
public class UnidadeServicoImpl extends GenericServicoImpl<Unidade, Long>
		implements UnidadeServico {

	private static final long serialVersionUID = -1380136082551143545L;

	@Autowired
	private UnidadeRepositorio repositorio;

	@Override
	protected GenericRepositorio<Unidade, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<Unidade> pesquisar(Unidade entity) {
		return repositorio.pesquisar(entity);
	}
}