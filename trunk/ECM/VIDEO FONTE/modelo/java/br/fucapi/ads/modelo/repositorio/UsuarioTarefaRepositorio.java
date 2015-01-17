package br.fucapi.ads.modelo.repositorio;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.ads.modelo.dominio.UsuarioTarefa;

@Repository
@Transactional
public interface UsuarioTarefaRepositorio extends
		GenericRepositorio<UsuarioTarefa, Long> {
	
	public List<UsuarioTarefa> pesquisar(UsuarioTarefa abstractEntity);

}