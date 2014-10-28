package br.com.saa.modelo.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.saa.modelo.entidade.Municipio;

public interface MunicipioRepositorio extends JpaRepository<Municipio, Long> {

	public List<Municipio> findByNomeLike(String nome);

}
