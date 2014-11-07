package br.com.saa.modelo.repositorio;

import java.util.List;

import br.com.saa.modelo.entidade.PerfilRotina;
import br.com.saa.modelo.entidade.Rotina;

public interface PerfilRotinaRepositorio extends
		GenericRepositorio<PerfilRotina, Long> {

	public List<Rotina> listaRotinaNotInPerfil(Long idPerfil);

}