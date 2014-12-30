package br.fucapi.ads.modelo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fucapi.ads.modelo.dominio.UsuarioLog;

public interface UsuarioLogRepositorio extends
		JpaRepository<UsuarioLog, Integer> {

}
