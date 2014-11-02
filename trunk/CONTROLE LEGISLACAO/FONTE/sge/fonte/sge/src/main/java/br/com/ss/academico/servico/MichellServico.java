package br.com.ss.academico.servico;

import java.util.List;

import br.com.ss.academico.dominio.Michell;
import br.com.ss.core.seguranca.servico.IService;

public interface MichellServico extends IService<Michell, Long> {

	public List<Michell> findByNomeLike(String nome);

}