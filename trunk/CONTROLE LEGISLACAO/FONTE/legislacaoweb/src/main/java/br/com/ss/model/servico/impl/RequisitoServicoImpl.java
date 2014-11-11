package br.com.ss.model.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ss.model.entidade.Requisito;
import br.com.ss.model.repositorio.GenericRepositorio;
import br.com.ss.model.repositorio.RequisitoRepositorio;
import br.com.ss.model.servico.RequisitoServico;

@Service
@Transactional
public class RequisitoServicoImpl extends GenericServico<Requisito, Long> implements
		RequisitoServico {

	private static final long serialVersionUID = 9126372622766341131L;

	@Autowired
	private RequisitoRepositorio repositorio;

	@Override
	protected GenericRepositorio<Requisito, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<Requisito> pesquisar(Requisito abstractEntity) {
		return this.repositorio.pesquisar(abstractEntity);
	}

}