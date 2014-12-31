package br.fucapi.ads.modelo.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.ads.modelo.dominio.PostoCopia;
import br.fucapi.ads.modelo.repositorio.GenericRepositorio;
import br.fucapi.ads.modelo.repositorio.PostoCopiaRepositorio;
import br.fucapi.ads.modelo.servico.PostoCopiaServico;

@Service
@Transactional
public class PostoCopiaServicoImpl extends GenericServico<PostoCopia, Long>
		implements PostoCopiaServico {

	private static final long serialVersionUID = -1380136082551143545L;

	@Autowired
	private PostoCopiaRepositorio repositorio;

	@Override
	protected GenericRepositorio<PostoCopia, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<PostoCopia> pesquisar(PostoCopia entity) {
		return repositorio.pesquisar(entity);
	}
}