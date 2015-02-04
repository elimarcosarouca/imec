package br.fucapi.ads.modelo.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.ads.modelo.dominio.Funcionario;
import br.fucapi.ads.modelo.repositorio.FuncionarioRepositorio;
import br.fucapi.ads.modelo.repositorio.GenericRepositorio;
import br.fucapi.ads.modelo.servico.FuncionarioServico;

@Service
@Transactional
public class FuncionarioServicoImpl extends GenericServico<Funcionario, Long>
		implements FuncionarioServico {

	private static final long serialVersionUID = -1380136082551143545L;

	@Autowired
	private FuncionarioRepositorio repositorio;

	@Override
	protected GenericRepositorio<Funcionario, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<Funcionario> pesquisar(Funcionario entity) {
		return repositorio.pesquisar(entity);
	}

}