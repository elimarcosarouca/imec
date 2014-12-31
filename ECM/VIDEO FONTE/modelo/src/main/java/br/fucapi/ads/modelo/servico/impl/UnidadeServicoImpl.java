package br.fucapi.ads.modelo.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.ads.modelo.dominio.Unidade;
import br.fucapi.ads.modelo.repositorio.GenericRepositorio;
import br.fucapi.ads.modelo.repositorio.UnidadeRepositorio;
import br.fucapi.ads.modelo.servico.UnidadeServico;

@Service
@Transactional
public class UnidadeServicoImpl extends GenericServico<Unidade, Long> implements
		UnidadeServico {

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