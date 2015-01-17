package br.fucapi.ads.modelo.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fucapi.ads.modelo.dominio.UsuarioLog;
import br.fucapi.ads.modelo.dominio.UsuarioTarefa;
import br.fucapi.ads.modelo.repositorio.GenericRepositorio;
import br.fucapi.ads.modelo.repositorio.UsuarioLogRepositorio;
import br.fucapi.ads.modelo.repositorio.UsuarioTarefaRepositorio;
import br.fucapi.ads.modelo.servico.UsuarioLogServico;
import br.fucapi.ads.modelo.servico.UsuarioTarefaServico;

@Service
public class UsuarioTarefaServicoImpl extends GenericServico<UsuarioTarefa, Long>
		implements UsuarioTarefaServico {

	@Autowired
	private UsuarioTarefaRepositorio repositorio;

	@Override
	protected GenericRepositorio<UsuarioTarefa, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<UsuarioTarefa> pesquisar(UsuarioTarefa entity) {
		return repositorio.pesquisar(entity);
	}

}