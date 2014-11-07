package br.com.saa.modelo.repositorio;

import java.util.List;

import br.com.saa.modelo.entidade.Perfil;
import br.com.saa.modelo.entidade.Rotina;
import br.com.saa.modelo.entidade.Sistema;

public interface RotinaRepositorio extends GenericRepositorio<Rotina, Long> {

	public List<Rotina> findByPerfil(Perfil perfil);

	public List<Rotina> findBySistema(Sistema sistema);

	public List<Rotina> listaRotinasPorPerfil(Long id);

	public List<Rotina> pesquisar(Rotina entity);

}