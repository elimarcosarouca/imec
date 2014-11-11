package br.com.ss.model.repositorio;

import java.util.List;

import br.com.ss.model.entidade.Empresa;

public interface EmpresaRepositorio extends GenericRepositorio<Empresa, Long> {

	List<Empresa> pesquisar(Empresa abstractEntity);
}