package br.com.ss.model.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ss.model.entidade.Empresa;
import br.com.ss.model.repositorio.EmpresaRepositorio;
import br.com.ss.model.repositorio.GenericRepositorio;
import br.com.ss.model.servico.EmpresaServico;

@Service
@Transactional
public class EmpresaServicoImpl extends GenericServicoImpl<Empresa, Long>
		implements EmpresaServico {

	private static final long serialVersionUID = -1380136082551143545L;

	@Autowired
	private EmpresaRepositorio repositorio;

	@Override
	protected GenericRepositorio<Empresa, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<Empresa> pesquisar(Empresa entity) {
		return repositorio.pesquisar(entity);
	}
}