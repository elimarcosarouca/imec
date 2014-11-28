package br.com.ss.model.servico;

import java.util.List;

import br.com.ss.model.entidade.Protocolo;

public interface ProtocoloServico extends Servico<Protocolo, Long> {
	
	List<Protocolo> pesquisar(Protocolo abstractEntity);

	Protocolo pesquisarPorAno(Protocolo abstractEntity);
	
	Protocolo gerarProtocolo();

}