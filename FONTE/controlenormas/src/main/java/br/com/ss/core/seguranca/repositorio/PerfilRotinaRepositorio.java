package br.com.ss.core.seguranca.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ss.core.seguranca.dominio.PerfilRotina;

@Repository
public interface PerfilRotinaRepositorio extends JpaRepository<PerfilRotina, Long> {

}