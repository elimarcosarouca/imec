package br.com.saa.modelo.repositorio.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.saa.modelo.entidade.Sistema;
import br.com.saa.modelo.repositorio.SistemaRepositorio;

@Repository
@Transactional
public class SistemaRepositorioImpl extends
		GenericRepositorioImpl<Sistema, Serializable> implements
		SistemaRepositorio {

}
