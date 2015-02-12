package br.fucapi.ads.modelo.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.ads.modelo.dominio.Categoria;
import br.fucapi.ads.modelo.repositorio.GenericRepositorio;
import br.fucapi.ads.modelo.repositorio.CategoriaRepositorio;
import br.fucapi.ads.modelo.servico.CategoriaServico;

@Service
@Transactional
public class CategoriaServicoImpl extends
		GenericServico<Categoria, Long> implements CategoriaServico {

	private static final long serialVersionUID = -1380136082551143545L;

	@Autowired
	private CategoriaRepositorio repositorio;

	@Override
	protected GenericRepositorio<Categoria, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<Categoria> pesquisar(Categoria entity) {
		return repositorio.pesquisar(entity);
	}
}