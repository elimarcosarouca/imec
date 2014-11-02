package br.com.ss.academico.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ss.academico.dominio.Mensalidade;

@Repository
public interface MensalidadeRepositorio extends JpaRepository<Mensalidade, Long> {

	@Query("select mens from Mensalidade mens where mens.matricula.idMatricula = :idMatricula")
	public List<Mensalidade> loadMensalidades(@Param("idMatricula") Long idMatricula);

}