package br.com.saa.modelo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.saa.modelo.entidade.UsuarioSistema;

public interface UsuarioSistemaRepositorio extends
		JpaRepository<UsuarioSistema, Long> {

}
