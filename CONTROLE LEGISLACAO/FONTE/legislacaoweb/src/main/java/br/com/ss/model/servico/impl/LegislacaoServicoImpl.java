package br.com.ss.model.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ss.model.entidade.Emissor;
import br.com.ss.model.repositorio.EmissorRepositorio;
import br.com.ss.model.repositorio.GenericRepositorio;
import br.com.ss.model.servico.EmissorServico;

@Service
@Transactional
public class LegislacaoServicoImpl extends GenericServico<Emissor, Long> implements
		EmissorServico {

	private static final long serialVersionUID = 9126372622766341131L;

	@Autowired
	private EmissorRepositorio repositorio;

	@Override
	protected GenericRepositorio<Emissor, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<Emissor> pesquisar(Emissor abstractEntity) {
		return this.repositorio.pesquisar(abstractEntity);
	}

}