package br.fucapi.ads.modelo.servico;

import java.util.List;

import br.fucapi.ads.modelo.dominio.Notificacao;

public interface NotificacaoServico {

	public List<Notificacao> listarTodos();

	public Notificacao salvar(Notificacao protocolo);

	public void remover(Notificacao protocolo);

	public List<Notificacao> findByDataLeituraIsNullAndLogin(String login);

	public List<Notificacao> findByLogin(String login);

	public void teste(String protocolo);

}
