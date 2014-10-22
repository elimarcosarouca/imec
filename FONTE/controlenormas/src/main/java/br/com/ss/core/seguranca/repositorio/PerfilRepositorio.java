package br.com.ss.core.seguranca.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ss.core.seguranca.dominio.Perfil;
import br.com.ss.core.seguranca.dominio.Sistema;

@Repository
public interface PerfilRepositorio extends JpaRepository<Perfil, Long> {

	@Query("select r from Perfil r where r.nome like :nome")
	public List<Perfil> findByNomeLike(@Param("nome") String nome);

	@Query("select r from Perfil r where r.sistema = :sistema")
	public List<Perfil> findBySistema(@Param("sistema") Sistema sistema);

	@Query("select u from Rotina u where u.sistema = :sistema and u.nome like :nome")
	public List<Perfil> findBySistemaByNomeLike(
			@Param("sistema") Sistema sistema, @Param("nome") String nome);

}