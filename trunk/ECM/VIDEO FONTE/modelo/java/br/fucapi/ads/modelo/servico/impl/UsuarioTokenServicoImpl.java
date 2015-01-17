package br.fucapi.ads.modelo.servico.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fucapi.ads.modelo.dominio.UsuarioToken;
import br.fucapi.ads.modelo.repositorio.GenericRepositorio;
import br.fucapi.ads.modelo.repositorio.UsuarioTokenRepositorio;
import br.fucapi.ads.modelo.servico.UsuarioTokenServico;

@Service
public class UsuarioTokenServicoImpl extends GenericServico<UsuarioToken, Long>
		implements UsuarioTokenServico {

	private static final long serialVersionUID = -6907481449741505991L;

	@Autowired
	private UsuarioTokenRepositorio repositorio;

	@Override
	protected GenericRepositorio<UsuarioToken, Long> getDao() {
		return repositorio;
	}

}