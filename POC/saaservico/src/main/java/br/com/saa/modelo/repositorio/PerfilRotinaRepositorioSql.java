package br.com.saa.modelo.repositorio;

import java.util.List;

import br.com.saa.modelo.entidade.Rotina;

public interface PerfilRotinaRepositorioSql {

	public List<Rotina> listaRotinaNotInPerfil(Long idPerfil);

}