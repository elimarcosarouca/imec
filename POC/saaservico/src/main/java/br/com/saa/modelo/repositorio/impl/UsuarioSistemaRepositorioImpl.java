package br.com.saa.modelo.repositorio.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.saa.modelo.entidade.UsuarioSistema;
import br.com.saa.modelo.repositorio.UsuarioSistemaRepositorio;

@Repository
@Transactional
public class UsuarioSistemaRepositorioImpl extends
		GenericRepositorioImpl<UsuarioSistema, Serializable> implements
		UsuarioSistemaRepositorio {

}
