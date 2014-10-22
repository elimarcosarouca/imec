package br.com.ss.controlenormas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ss.controlenormas.dominio.TipoDocumento;

@Repository
public interface TipoDocumentoRepositorio extends
		JpaRepository<TipoDocumento, Long> {
}