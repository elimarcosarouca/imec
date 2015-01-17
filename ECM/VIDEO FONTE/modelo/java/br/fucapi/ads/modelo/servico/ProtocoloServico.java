package br.fucapi.ads.modelo.servico;

import java.util.List;

import br.fucapi.ads.modelo.dominio.Protocolo;

public interface ProtocoloServico extends Servico<Protocolo, Long> {

	List<Protocolo> pesquisar(Protocolo abstractEntity);

	Protocolo pesquisarPorAno(Protocolo abstractEntity);

	Protocolo gerarProtocolo();

}