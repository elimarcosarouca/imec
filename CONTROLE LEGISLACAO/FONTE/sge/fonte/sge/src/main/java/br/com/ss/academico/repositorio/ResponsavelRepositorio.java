package br.com.ss.academico.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ss.academico.dominio.Responsavel;

@Repository
public interface ResponsavelRepositorio extends
		JpaRepository<Responsavel, Long> {

	@Query("select res from Responsavel res where res.nome like '%:nome%'")
	public List<Responsavel> findByNomeLike(@Param("nome") String nome);

}