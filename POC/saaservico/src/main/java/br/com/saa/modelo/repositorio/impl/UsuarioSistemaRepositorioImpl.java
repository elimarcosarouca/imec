package br.com.saa.modelo.repositorio.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import br.com.saa.modelo.entidade.UsuarioSistema;
import br.com.saa.modelo.repositorio.UsuarioSistemaRepositorio;

@Repository
public class UsuarioSistemaRepositorioImpl extends
		GenericRepositorioImpl<UsuarioSistema, Serializable> implements
		UsuarioSistemaRepositorio {

}
