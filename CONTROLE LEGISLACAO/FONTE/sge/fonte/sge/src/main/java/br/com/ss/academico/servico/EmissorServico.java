package br.com.ss.academico.servico;

import java.util.List;

import br.com.ss.academico.dominio.Emissor;
import br.com.ss.core.seguranca.servico.IService;

public interface EmissorServico extends IService<Emissor, Long> {

	public List<Emissor> findByNomeLike(String nome);
	
}