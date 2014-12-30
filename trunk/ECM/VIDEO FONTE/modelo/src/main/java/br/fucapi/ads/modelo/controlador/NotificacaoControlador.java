package br.fucapi.ads.modelo.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.fucapi.ads.modelo.dominio.Notificacao;
import br.fucapi.ads.modelo.servico.NotificacaoServico;

@ManagedBean
@SessionScoped
public class NotificacaoControlador implements Serializable {

	private Notificacao entidade;

	private List<Notificacao> lista;

	@ManagedProperty(value = "#{notificacaoServicoImpl}")
	private NotificacaoServico notificacaoServico;

	@PostConstruct
	public void init() {
		this.entidade = new Notificacao();
		this.lista = new ArrayList<Notificacao>();
	}

	public Notificacao getEntidade() {
		return entidade;
	}

	public void setEntidade(Notificacao entidade) {
		this.entidade = entidade;
	}

	public List<Notificacao> getLista() {
		return lista;
	}

	public void setLista(List<Notificacao> lista) {
		this.lista = lista;
	}

	public NotificacaoServico getNotificacaoServico() {
		return notificacaoServico;
	}

	public void setNotificacaoServico(NotificacaoServico notificacaoServico) {
		this.notificacaoServico = notificacaoServico;
	}

}