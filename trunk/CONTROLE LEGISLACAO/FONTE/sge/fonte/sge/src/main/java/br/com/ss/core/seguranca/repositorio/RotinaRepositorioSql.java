package br.com.ss.core.seguranca.repositorio;

import java.util.List;

import br.com.ss.core.seguranca.dominio.Rotina;

public interface RotinaRepositorioSql {

	public List<Rotina> listaRotinasPorPerfil(Long id);

	public List<Rotina> pesquisar(Rotina entity);

}
