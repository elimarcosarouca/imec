package br.com.saa.modelo.repositorio.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import br.com.saa.modelo.entidade.UsuarioPerfil;
import br.com.saa.modelo.repositorio.GenericRepositorioImpl;
import br.com.saa.modelo.repositorio.UsuarioPerfilRepositorio;

@Repository
public class UsuarioPerfilRepositorioImpl extends
		GenericRepositorioImpl<UsuarioPerfil, Serializable> implements
		UsuarioPerfilRepositorio {

}
