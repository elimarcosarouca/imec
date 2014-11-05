package br.com.saa.modelo.repositorio.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.saa.modelo.entidade.Municipio;
import br.com.saa.modelo.repositorio.MunicipioRepositorio;

@Repository
@Transactional
public class MunicipioRepositorioImpl extends
		GenericRepositorioImpl<Municipio, Serializable> implements
		MunicipioRepositorio {

}
