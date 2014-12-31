package br.fucapi.ads.modelo.servico;

import java.util.List;

import br.fucapi.ads.modelo.dominio.UsuarioLog;

public interface UsuarioLogServico extends Servico<UsuarioLog, Long> {
	
	public List<UsuarioLog> pesquisar(UsuarioLog entity);

}