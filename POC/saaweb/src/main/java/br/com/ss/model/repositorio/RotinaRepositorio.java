package br.com.ss.model.repositorio;

import java.util.List;

import br.com.ss.model.entidade.Perfil;
import br.com.ss.model.entidade.Rotina;
import br.com.ss.model.entidade.Sistema;

public interface RotinaRepositorio extends GenericRepositorio<Rotina, Long> {

	List<Rotina> findByPerfil(Perfil perfil);

	List<Rotina> findBySistema(Sistema sistema);

	List<Rotina> findBySistemaByNomeLike(Sistema sistema);

	List<Rotina> pesquisar(Rotina abstractEntity);

}