package br.com.ss.core.seguranca.servico;

import java.util.List;

import br.com.ss.academico.dominio.Empresa;

public interface EmpresaServico extends IService<Empresa, Long> {

	public List<Empresa> findByNomeLike(String nome);
	
	public Empresa findOne(Long primaryKey);

}