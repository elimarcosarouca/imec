package br.com.ss.academico.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ss.academico.dominio.Configuracao;

@Repository
public interface ConfiguracaoRepositorio extends JpaRepository<Configuracao, Long> {
	

}