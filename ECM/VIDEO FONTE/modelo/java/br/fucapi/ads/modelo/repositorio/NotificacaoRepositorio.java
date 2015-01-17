package br.fucapi.ads.modelo.repositorio;

import java.util.List;

import br.fucapi.ads.modelo.dominio.Notificacao;

public interface NotificacaoRepositorio extends
		GenericRepositorio<Notificacao, Long> {
	
	List<Notificacao> pesquisar(Notificacao abstractEntity);

	List<Notificacao> findByDataLeituraIsNullAndLogin(String login);

}