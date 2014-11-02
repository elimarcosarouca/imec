package br.com.ss.academico.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ss.academico.dominio.Professor;

@Repository
public interface ProfessorRepositorio extends JpaRepository<Professor, Long> {

	@Query("select u from Professor u where u.nome like :nome")
	public List<Professor> findByNomeLike(@Param("nome") String nome);

}