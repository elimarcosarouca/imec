package br.fucapi.ads.modelo.repositorio;

import java.util.List;

import br.fucapi.ads.modelo.dominio.Protocolo;

public interface ProtocoloRepositorio extends
		GenericRepositorio<Protocolo, Long> {

	List<Protocolo> pesquisar(Protocolo abstractEntity);

	Protocolo pesquisarPorAno(Protocolo abstractEntity);
}