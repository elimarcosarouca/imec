package br.com.saa.servico.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.saa.modelo.entidade.Licenca;
import br.com.saa.modelo.repositorio.GenericRepositorio;
import br.com.saa.modelo.repositorio.LicencaRepositorio;
import br.com.saa.servico.LicencaServico;

@Service
@Transactional
public class LicencaServicoImpl extends GenericServico<Licenca, Long> implements
		LicencaServico {

	private static final long serialVersionUID = 9126372622766341131L;

	@Autowired
	private LicencaRepositorio repositorio;

	@Override
	protected GenericRepositorio<Licenca, Long> getRepositorio() {
		return repositorio;
	}

}
