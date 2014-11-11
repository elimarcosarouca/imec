package br.com.ss.model.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ss.model.entidade.Filial;
import br.com.ss.model.repositorio.FilialRepositorio;
import br.com.ss.model.repositorio.GenericRepositorio;
import br.com.ss.model.servico.FilialServico;

@Service
@Transactional
public class FilialServicoImpl extends GenericServico<Filial, Long> implements
		FilialServico {

	private static final long serialVersionUID = 9126372622766341131L;

	@Autowired
	private FilialRepositorio repositorio;

	@Override
	protected GenericRepositorio<Filial, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<Filial> pesquisar(Filial abstractEntity) {
		return this.repositorio.pesquisar(abstractEntity);
	}

}