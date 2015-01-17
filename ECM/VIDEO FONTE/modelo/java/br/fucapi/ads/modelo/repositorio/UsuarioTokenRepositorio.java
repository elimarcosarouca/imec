package br.fucapi.ads.modelo.repositorio;

import br.fucapi.ads.modelo.dominio.UsuarioToken;

public interface UsuarioTokenRepositorio extends
		GenericRepositorio<UsuarioToken, Long> {

	public UsuarioToken pesquisarPorToken(String token);
	
}