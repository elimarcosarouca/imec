package br.com.ss.model.repositorio;

import java.util.List;

import br.com.ss.model.entidade.Protocolo;

public interface ProtocoloRepositorio extends
		GenericRepositorio<Protocolo, Long> {

	List<Protocolo> pesquisar(Protocolo abstractEntity);

	Protocolo pesquisarPorAno(Protocolo abstractEntity);
}