package br.com.saa.modelo.repositorio.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.saa.modelo.entidade.Empresa;
import br.com.saa.modelo.repositorio.EmpresaRepositorio;

@Repository
@Transactional
public class EmpresaRepositorioImpl extends
		GenericRepositorioImpl<Empresa, Serializable> implements
		EmpresaRepositorio {

}
