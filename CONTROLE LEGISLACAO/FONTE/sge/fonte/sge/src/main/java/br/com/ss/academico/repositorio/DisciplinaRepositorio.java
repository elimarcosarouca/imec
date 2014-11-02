package br.com.ss.academico.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ss.academico.dominio.Disciplina;

@Repository
public interface DisciplinaRepositorio extends
		JpaRepository<Disciplina, Long> {

	@Query("select u from Disciplina u where u.nome like :nome")
	public List<Disciplina> findByNomeLike(@Param("nome") String nome);
	
}