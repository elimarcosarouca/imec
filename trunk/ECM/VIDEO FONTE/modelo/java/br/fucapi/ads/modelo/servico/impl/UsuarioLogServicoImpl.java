package br.fucapi.ads.modelo.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fucapi.ads.modelo.dominio.GrupoLog;
import br.fucapi.ads.modelo.dominio.UsuarioLog;
import br.fucapi.ads.modelo.repositorio.GenericRepositorio;
import br.fucapi.ads.modelo.repositorio.GrupoLogRepositorio;
import br.fucapi.ads.modelo.repositorio.UsuarioLogRepositorio;
import br.fucapi.ads.modelo.servico.GrupoLogServico;
import br.fucapi.ads.modelo.servico.UsuarioLogServico;

@Service
public class UsuarioLogServicoImpl extends GenericServico<UsuarioLog, Long>
		implements UsuarioLogServico {


	@Autowired
	private UsuarioLogRepositorio repositorio;

	@Override
	protected GenericRepositorio<UsuarioLog, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<UsuarioLog> pesquisar(UsuarioLog entity) {
		return repositorio.pesquisar(entity);
	}

}
