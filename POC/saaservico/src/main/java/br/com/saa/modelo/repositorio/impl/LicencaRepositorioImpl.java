package br.com.saa.modelo.repositorio.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.saa.modelo.entidade.Licenca;
import br.com.saa.modelo.repositorio.LicencaRepositorio;

@Repository
@Transactional
public class LicencaRepositorioImpl extends
		GenericRepositorioImpl<Licenca, Serializable> implements
		LicencaRepositorio {

}
