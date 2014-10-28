package br.com.saa.modelo.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.saa.modelo.entidade.Empresa;

@Repository
public interface EmpresaRepositorio extends JpaRepository<Empresa, Long> {

	@Query("select u from Empresa u where u.nomeFantasia like :nomeFantasia")
	public List<Empresa> findByNomeLike(@Param("nomeFantasia") String nomeFantasia);

}