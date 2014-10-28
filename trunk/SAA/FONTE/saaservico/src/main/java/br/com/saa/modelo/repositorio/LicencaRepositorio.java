package br.com.saa.modelo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.saa.modelo.entidade.Licenca;

public interface LicencaRepositorio extends JpaRepository<Licenca, Long> {

}