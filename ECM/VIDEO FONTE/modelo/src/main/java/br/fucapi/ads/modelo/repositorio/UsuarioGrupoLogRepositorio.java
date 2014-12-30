package br.fucapi.ads.modelo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fucapi.ads.modelo.dominio.UsuarioGrupoLog;

public interface UsuarioGrupoLogRepositorio extends
		JpaRepository<UsuarioGrupoLog, Integer> {

}
