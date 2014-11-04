package br.com.saa.modelo.repositorio.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import br.com.saa.modelo.entidade.Licenca;
import br.com.saa.modelo.repositorio.GenericRepositorioImpl;
import br.com.saa.modelo.repositorio.LicencaRepositorio;

@Repository
public class LicencaRepositorioImpl extends
		GenericRepositorioImpl<Licenca, Serializable> implements
		LicencaRepositorio {

}
