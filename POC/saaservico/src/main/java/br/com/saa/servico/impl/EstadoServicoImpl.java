package br.com.saa.servico.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.saa.modelo.entidade.Estado;
import br.com.saa.modelo.repositorio.EstadoRepositorio;
import br.com.saa.modelo.repositorio.GenericRepositorio;
import br.com.saa.servico.EstadoServico;

@Service
@Transactional
public class EstadoServicoImpl extends GenericServico<Estado, Long> implements
		EstadoServico {
	
	private static final long serialVersionUID = 9126372622766341131L;
	
	@Autowired
	private EstadoRepositorio repositorio;

	@Override
	protected GenericRepositorio<Estado, Long> getRepositorio() {
		return repositorio;
	}


}
