package br.com.saa.modelo.repositorio.impl;

import org.springframework.stereotype.Repository;

import br.com.saa.modelo.entidade.Estado;
import br.com.saa.modelo.repositorio.EstadoRepositorio;

@Repository
public class EstadoRepositorioImpl extends
		GenericRepositorioImpl<Estado, Long> implements
		EstadoRepositorio {

}
