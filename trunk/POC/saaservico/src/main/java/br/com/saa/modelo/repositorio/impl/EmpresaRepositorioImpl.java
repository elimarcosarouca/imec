package br.com.saa.modelo.repositorio.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import br.com.saa.modelo.entidade.Empresa;
import br.com.saa.modelo.repositorio.EmpresaRepositorio;

@Repository
public class EmpresaRepositorioImpl extends
		GenericRepositorioImpl<Empresa, Serializable> implements
		EmpresaRepositorio {

}
