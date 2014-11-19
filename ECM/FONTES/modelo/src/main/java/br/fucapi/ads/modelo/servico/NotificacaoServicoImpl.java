package br.fucapi.ads.modelo.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fucapi.ads.modelo.dominio.Notificacao;
import br.fucapi.ads.modelo.repositorio.NotificacaoRepositorio;

@Service
public class NotificacaoServicoImpl implements NotificacaoServico {

	@Autowired
	NotificacaoRepositorio notificacaoRepositorio;

	@Override
	public List<Notificacao> listarTodos() {
		return notificacaoRepositorio.findAll();
	}

	@Override
	public Notificacao salvar(Notificacao notificacao) {
		return notificacaoRepositorio.saveAndFlush(notificacao);
	}

	@Override
	public void remover(Notificacao notificacao) {
		notificacaoRepositorio.delete(notificacao);
	}

	@Override
	public List<Notificacao> findByDataLeituraIsNullAndLogin(String login) {
		return notificacaoRepositorio.findByDataLeituraIsNullAndLogin(login);
	}

	@Override
	public List<Notificacao> findByLogin(String login) {
		return notificacaoRepositorio.findByLogin(login);
	}

	@Override
	public void teste(String protocolo) {
		System.out.println("teste");
		
	}
	
}
