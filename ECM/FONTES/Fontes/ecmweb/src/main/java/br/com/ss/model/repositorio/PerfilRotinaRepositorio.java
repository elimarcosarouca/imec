package br.com.ss.model.repositorio;

import java.util.List;

import br.com.ss.model.entidade.PerfilRotina;
import br.com.ss.model.entidade.Rotina;

public interface PerfilRotinaRepositorio extends
		GenericRepositorio<PerfilRotina, Long> {

	List<Rotina> listaRotinaNotInPerfil(Long idPerfil);

	List<PerfilRotina> pesquisar(PerfilRotina abstractEntity);

}