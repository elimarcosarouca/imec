package br.com.saa.modelo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.saa.modelo.entidade.PerfilRotina;

@Repository
public interface PerfilRotinaRepositorio extends
		JpaRepository<PerfilRotina, Long> {

}