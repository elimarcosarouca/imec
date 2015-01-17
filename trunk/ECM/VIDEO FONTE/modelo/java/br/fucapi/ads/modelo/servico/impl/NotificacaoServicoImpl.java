package br.fucapi.ads.modelo.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fucapi.ads.modelo.dominio.Notificacao;
import br.fucapi.ads.modelo.repositorio.GenericRepositorio;
import br.fucapi.ads.modelo.repositorio.NotificacaoRepositorio;
import br.fucapi.ads.modelo.servico.NotificacaoServico;

@Service
public class NotificacaoServicoImpl extends GenericServico<Notificacao, Long>
		implements NotificacaoServico {

	private static final long serialVersionUID = -1380136082551143545L;

	@Autowired
	private NotificacaoRepositorio repositorio;

	@Override
	protected GenericRepositorio<Notificacao, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<Notificacao> pesquisar(Notificacao entity) {
		return repositorio.pesquisar(entity);
	}

}