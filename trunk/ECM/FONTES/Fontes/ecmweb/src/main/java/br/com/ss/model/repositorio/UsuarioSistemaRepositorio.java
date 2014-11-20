package br.com.ss.model.repositorio;

import java.util.List;

import br.com.ss.model.entidade.UsuarioSistema;

public interface UsuarioSistemaRepositorio extends
		GenericRepositorio<UsuarioSistema, Long> {

	List<UsuarioSistema> pesquisar(UsuarioSistema abstractEntity);

}