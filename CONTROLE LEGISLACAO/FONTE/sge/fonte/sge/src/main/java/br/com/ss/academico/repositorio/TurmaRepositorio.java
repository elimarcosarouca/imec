package br.com.ss.academico.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ss.academico.dominio.Matricula;
import br.com.ss.academico.dominio.Turma;

@Repository
public interface TurmaRepositorio extends JpaRepository<Turma, Long> {
	
	@Query("select r from Turma r where r.ano = :ano")
	public List<Turma> findByAno(@Param("ano") Integer ano);
	
	@Query("select r from Turma r where r.matriculas = :matricula")
	public Turma findByMatricula(@Param("matricula") Matricula matricula);

}