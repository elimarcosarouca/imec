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
	protected GenericRepositorio<Rotina, Long> getDao() {
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
	public List<Rotina> findBySistemaByNomeLike(Sistema sistema) {
		return repositorio.findBySistemaByNomeLike(sistema);
	}
}