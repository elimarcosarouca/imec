package br.com.ss.academico.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ss.academico.dominio.Michell;

@Repository
public interface MichellRepositorio extends JpaRepository<Michell, Long> {

	public List<Michell> findByNomeLike(String nome);

}