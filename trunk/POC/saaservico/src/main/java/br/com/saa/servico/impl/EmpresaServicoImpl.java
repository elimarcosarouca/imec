package br.com.saa.servico.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.saa.modelo.entidade.Empresa;
import br.com.saa.modelo.repositorio.EmpresaRepositorio;
import br.com.saa.modelo.repositorio.GenericRepositorio;
import br.com.saa.servico.EmpresaServico;

@Service
@Transactional
public class EmpresaServicoImpl extends GenericServico<Empresa, Long> implements
		EmpresaServico {

	private static final long serialVersionUID = 9126372622766341131L;

	@Autowired
	private EmpresaRepositorio repositorio;

	@Override
	protected GenericRepositorio<Empresa, Long> getRepositorio() {
		return repositorio;
	}

}
