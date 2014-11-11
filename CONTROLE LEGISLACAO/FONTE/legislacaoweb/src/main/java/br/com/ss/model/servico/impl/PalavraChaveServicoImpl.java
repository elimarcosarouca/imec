package br.com.ss.model.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ss.model.entidade.PalavraChave;
import br.com.ss.model.repositorio.GenericRepositorio;
import br.com.ss.model.repositorio.PalavraChaveRepositorio;
import br.com.ss.model.servico.PalavraChaveServico;

@Service
@Transactional
public class PalavraChaveServicoImpl extends GenericServico<PalavraChave, Long> implements
		PalavraChaveServico {

	private static final long serialVersionUID = 9126372622766341131L;

	@Autowired
	private PalavraChaveRepositorio repositorio;

	@Override
	protected GenericRepositorio<PalavraChave, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<PalavraChave> pesquisar(PalavraChave abstractEntity) {
		return this.repositorio.pesquisar(abstractEntity);
	}

}