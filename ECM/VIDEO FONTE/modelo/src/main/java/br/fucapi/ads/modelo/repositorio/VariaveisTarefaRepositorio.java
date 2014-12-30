package br.fucapi.ads.modelo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.fucapi.ads.modelo.dominio.VariaveisTarefa;

public interface VariaveisTarefaRepositorio extends
		JpaRepository<VariaveisTarefa, Long> {

	@Query("select u from VariaveisTarefa u where u.id = :id")
	public VariaveisTarefa findById(@Param("id") Long id);

}
