package br.com.ss.academico.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ss.academico.dominio.Curso;

@Repository
public interface CursoRepositorio extends JpaRepository<Curso, Long> {

	@Query("select u from Curso u where u.nome like :nome")
	public List<Curso> findByNomeLike(@Param("nome") String nome);

}