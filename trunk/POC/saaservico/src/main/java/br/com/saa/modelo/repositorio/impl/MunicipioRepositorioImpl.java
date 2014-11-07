package br.com.saa.modelo.repositorio.impl;

import org.springframework.stereotype.Repository;

import br.com.saa.modelo.entidade.Municipio;
import br.com.saa.modelo.repositorio.MunicipioRepositorio;

@Repository
public class MunicipioRepositorioImpl extends
		GenericRepositorioImpl<Municipio, Long> implements MunicipioRepositorio {

}
