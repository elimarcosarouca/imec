package br.com.ss.academico.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ss.academico.dominio.Boletim;
import br.com.ss.academico.dominio.Matricula;

@Repository
public interface BoletimRepositorio extends JpaRepository<Boletim, Long> {

	@Query(" select bole from Boletim bole join bole.matricula mat " 
	+ " where mat = :matricula ")
	public List<Boletim> pesquisarBoletim(
			@Param("matricula") Matricula matricula);

}