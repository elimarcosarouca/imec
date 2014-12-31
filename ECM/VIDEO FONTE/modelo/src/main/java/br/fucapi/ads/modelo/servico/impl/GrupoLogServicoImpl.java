package br.fucapi.ads.modelo.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fucapi.ads.modelo.dominio.GrupoLog;
import br.fucapi.ads.modelo.repositorio.GenericRepositorio;
import br.fucapi.ads.modelo.repositorio.GrupoLogRepositorio;
import br.fucapi.ads.modelo.servico.GrupoLogServico;

@Service
public class GrupoLogServicoImpl extends GenericServico<GrupoLog, Long>
		implements GrupoLogServico {

	private static final long serialVersionUID = -1380136082551143545L;

	@Autowired
	private GrupoLogRepositorio repositorio;

	@Override
	protected GenericRepositorio<GrupoLog, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<GrupoLog> pesquisar(GrupoLog entity) {
		return repositorio.pesquisar(entity);
	}

}
