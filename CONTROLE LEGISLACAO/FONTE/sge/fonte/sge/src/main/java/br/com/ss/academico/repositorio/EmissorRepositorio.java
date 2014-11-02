package br.com.ss.academico.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ss.academico.dominio.Emissor;
import br.com.ss.academico.dominio.Michell;

@Repository
public interface EmissorRepositorio extends JpaRepository<Emissor, Long> {

	public List<Emissor> findByNomeLike(String nome);

}