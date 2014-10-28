package br.com.saa.servico;

import java.util.List;

import br.com.saa.modelo.entidade.Empresa;

public interface EmpresaServico extends IService<Empresa, Long> {

	public List<Empresa> findByNomeLike(String nome);
	
	public Empresa findOne(Long primaryKey);

}