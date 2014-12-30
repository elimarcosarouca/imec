package br.fucapi.ads.modelo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.fucapi.ads.modelo.dominio.Protocolo;

public interface ProtocoloRepositorio extends JpaRepository<Protocolo, Integer> {


		@Query("select u from Protocolo u where u.ano = :ano")
		Protocolo findByAno(@Param("ano") int ano);

		@Query("select u from Protocolo u where u.ano = :ano and u.tipoProtocolo = :tipoProtocolo")
		Protocolo findByAnoAndTipoProtocolo(@Param("ano") int ano,
				@Param("tipoProtocolo") String tipoProtocolo);
		
		@Query("select u from Protocolo u where u.tipoProtocolo = :tipoProtocolo")
		Protocolo findByTipoProtocolo(@Param("tipoProtocolo") String tipoProtocolo);

}
