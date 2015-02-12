package br.fucapi.ads.modelo.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.ads.modelo.dominio.Colaborador;
import br.fucapi.ads.modelo.repositorio.ColaboradorRepositorio;
import br.fucapi.ads.modelo.repositorio.GenericRepositorio;
import br.fucapi.ads.modelo.servico.ColaboradorServico;

@Service
@Transactional
public class ColaboradorServicoImpl extends GenericServico<Colaborador, Long>
		implements ColaboradorServico {

	private static final long serialVersionUID = -1380136082551143545L;

	@Autowired
	private ColaboradorRepositorio repositorio;

	@Override
	protected GenericRepositorio<Colaborador, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<Colaborador> pesquisar(Colaborador entity) {
		return repositorio.pesquisar(entity);
	}

}