package br.fucapi.ads.modelo.servico;

import br.fucapi.ads.modelo.dominio.UsuarioToken;

public interface UsuarioTokenServico {

	public UsuarioToken findByToken(String token);

	public UsuarioToken salvar(UsuarioToken UsuarioToken);
}
