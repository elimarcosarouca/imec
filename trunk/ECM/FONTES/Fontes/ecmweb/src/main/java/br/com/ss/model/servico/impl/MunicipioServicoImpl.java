package br.com.ss.model.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ss.model.entidade.Municipio;
import br.com.ss.model.repositorio.GenericRepositorio;
import br.com.ss.model.repositorio.MunicipioRepositorio;
import br.com.ss.model.servico.MunicipioServico;

@Service
@Transactional
public class MunicipioServicoImpl extends GenericServicoImpl<Municipio, Long>
		implements MunicipioServico {

	private static final long serialVersionUID = -1380136082551143545L;

	@Autowired
	private MunicipioRepositorio repositorio;

	@Override
	protected GenericRepositorio<Municipio, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<Municipio> pesquisar(Municipio entity) {
		return repositorio.pesquisar(entity);
	}
}