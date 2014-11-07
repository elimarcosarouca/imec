package br.com.saa.servico.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.saa.modelo.entidade.Municipio;
import br.com.saa.modelo.repositorio.GenericRepositorio;
import br.com.saa.modelo.repositorio.MunicipioRepositorio;
import br.com.saa.servico.MunicipioServico;

@Service
@Transactional
public class MunicipioServicoImpl extends GenericServico<Municipio, Long>
		implements MunicipioServico {

	private static final long serialVersionUID = 9126372622766341131L;

	@Autowired
	private MunicipioRepositorio repositorio;

	@Override
	protected GenericRepositorio<Municipio, Long> getRepositorio() {
		return repositorio;
	}

}
