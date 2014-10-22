package br.com.ss.core.seguranca.repositorio;

import java.util.List;

import br.com.ss.core.seguranca.dominio.Rotina;

public interface PerfilRotinaRepositorioSql {

	public List<Rotina> listaRotinaNotInPerfil(Long idPerfil);

}