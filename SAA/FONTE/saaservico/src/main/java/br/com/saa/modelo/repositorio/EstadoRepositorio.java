package br.com.saa.modelo.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.saa.modelo.entidade.Estado;

@Repository
public interface EstadoRepositorio extends JpaRepository<Estado, Long> {

	public List<Estado> findByNomeLike(String nome);

}