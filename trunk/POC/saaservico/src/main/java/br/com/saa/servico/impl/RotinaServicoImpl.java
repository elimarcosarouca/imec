package br.com.saa.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.saa.modelo.entidade.Perfil;
import br.com.saa.modelo.entidade.Rotina;
import br.com.saa.modelo.entidade.Sistema;
import br.com.saa.modelo.repositorio.GenericRepositorio;
import br.com.saa.modelo.repositorio.RotinaRepositorio;
import br.com.saa.servico.RotinaServico;

@Service
@Transactional
public class RotinaServicoImpl extends GenericServico<Rotina, Long> implements
		RotinaServico {

	private static final long serialVersionUID = 9126372622766341131L;

	@Autowired
	private RotinaRepositorio repositorio;

	@Override
	protected GenericRepositorio<Rotina, Long> getRepositorio() {
		return repositorio;
	}

	@Override
	public List<Rotina> findByPerfil(Perfil perfil) {
		return repositorio.findByPerfil(perfil);
	}

	@Override
	public List<Rotina> findBySistema(Sistema sistema) {
		return repositorio.findBySistema(sistema);
	}

	@Override
	public List<Rotina> listaRotinasPorPerfil(Long id) {
		return repositorio.listaRotinasPorPerfil(id);
	}

	@Override
	public List<Rotina> pesquisar(Rotina rotina) {
		return repositorio.pesquisar(rotina);
	}
}