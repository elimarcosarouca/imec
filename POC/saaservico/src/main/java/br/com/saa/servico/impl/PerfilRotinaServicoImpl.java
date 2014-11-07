package br.com.saa.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.saa.modelo.entidade.PerfilRotina;
import br.com.saa.modelo.entidade.Rotina;
import br.com.saa.modelo.repositorio.GenericRepositorio;
import br.com.saa.modelo.repositorio.PerfilRotinaRepositorio;
import br.com.saa.servico.PerfilRotinaServico;

@Service
@Transactional
public class PerfilRotinaServicoImpl extends GenericServico<PerfilRotina, Long> implements
		PerfilRotinaServico {

	private static final long serialVersionUID = 9126372622766341131L;

	@Autowired
	private PerfilRotinaRepositorio repositorio;

	@Override
	protected GenericRepositorio<PerfilRotina, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<Rotina> listaRotinaNotInPerfil(Long idPerfil) {
		return repositorio.listaRotinaNotInPerfil(idPerfil);
	}

}