package br.fucapi.ads.modelo.repositorio;

import java.util.List;

import br.fucapi.ads.modelo.dominio.UsuarioLog;

public interface UsuarioLogRepositorio extends
		GenericRepositorio<UsuarioLog, Long> {
	
	public List<UsuarioLog> pesquisar(UsuarioLog entity);

}