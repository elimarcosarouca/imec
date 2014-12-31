package br.fucapi.ads.modelo.servico.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fucapi.ads.modelo.dominio.VariaveisTarefa;
import br.fucapi.ads.modelo.repositorio.GenericRepositorio;
import br.fucapi.ads.modelo.repositorio.VariaveisTarefaRepositorio;
import br.fucapi.ads.modelo.servico.VariaveisTarefaServico;

@Service
public class VariaveisTarefaServicoImpl extends
		GenericServico<VariaveisTarefa, Long> implements VariaveisTarefaServico {

	private static final long serialVersionUID = 8153328523227160866L;

	@Autowired
	private VariaveisTarefaRepositorio repositorio;

	@Override
	protected GenericRepositorio<VariaveisTarefa, Long> getDao() {
		return repositorio;
	}

}