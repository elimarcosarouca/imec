package br.com.saa.modelo.repositorio.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import br.com.saa.modelo.entidade.Usuario;
import br.com.saa.modelo.repositorio.UsuarioRepositorio;

@Repository
public class UsuarioRepositorioImpl extends
		GenericRepositorioImpl<Usuario, Serializable> implements
		UsuarioRepositorio {

}
