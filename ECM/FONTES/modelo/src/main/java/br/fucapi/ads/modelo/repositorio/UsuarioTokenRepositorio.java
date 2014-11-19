package br.fucapi.ads.modelo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fucapi.ads.modelo.dominio.UsuarioToken;

public interface UsuarioTokenRepositorio extends
		JpaRepository<UsuarioToken, Long> {

	public UsuarioToken findByToken(String token);
}
