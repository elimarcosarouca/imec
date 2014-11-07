package br.com.saa.modelo.repositorio.impl;

import org.springframework.stereotype.Repository;

import br.com.saa.modelo.entidade.Sistema;
import br.com.saa.modelo.repositorio.SistemaRepositorio;

@Repository
public class SistemaRepositorioImpl extends
		GenericRepositorioImpl<Sistema, Long> implements SistemaRepositorio {

}
