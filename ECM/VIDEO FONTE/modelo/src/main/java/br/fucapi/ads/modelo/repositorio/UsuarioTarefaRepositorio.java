package br.fucapi.ads.modelo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.ads.modelo.dominio.UsuarioTarefa;

@Repository
@Transactional
public interface UsuarioTarefaRepositorio extends
		JpaRepository<UsuarioTarefa, Integer> {

}
