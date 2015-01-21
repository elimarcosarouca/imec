package br.fucapi.ads.modelo.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.ads.modelo.dominio.Setor;
import br.fucapi.ads.modelo.dominio.Unidade;
import br.fucapi.ads.modelo.repositorio.GenericRepositorio;
import br.fucapi.ads.modelo.repositorio.SetorRepositorio;
import br.fucapi.ads.modelo.servico.SetorServico;

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
	
//	public List<Setor> pesquisarByUnidade(Unidade entity) {
//		return reposotorio.pesquisarByUnidade(entity);
//	}
}