package br.com.saa.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.saa.modelo.entidade.Perfil;
import br.com.saa.modelo.entidade.Sistema;
import br.com.saa.modelo.repositorio.GenericRepositorio;
import br.com.saa.modelo.repositorio.PerfilRepositorio;
import br.com.saa.servico.PerfilServico;

@Service
@Transactional
public class PerfilServicoImpl extends GenericServico<Perfil, Long> implements
		PerfilServico {

	private static final long serialVersionUID = 9126372622766341131L;

	@Autowired
	private PerfilRepositorio repositorio;

	@Override
	protected GenericRepositorio<Perfil, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<Perfil> listaPerfilPorSistemaPorUsuario(int sistemaId,
			Long usuarioId) {
		return repositorio
				.listaPerfilPorSistemaPorUsuario(sistemaId, usuarioId);
	}

	@Override
	public List<Perfil> listaPerfil(Sistema sistema) {
		return repositorio.listaPerfil(sistema);
	}

	@Override
	public List<Perfil> listaPerfilNotInUsuario(Long idUsuario) {
		return repositorio.listaPerfilNotInUsuario(idUsuario);
	}

}