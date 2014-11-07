package br.com.saa.modelo.repositorio.impl;

import org.springframework.stereotype.Repository;

import br.com.saa.modelo.entidade.UsuarioSistema;
import br.com.saa.modelo.repositorio.UsuarioSistemaRepositorio;

@Repository
public class UsuarioSistemaRepositorioImpl extends
		GenericRepositorioImpl<UsuarioSistema, Long> implements
		UsuarioSistemaRepositorio {

}
