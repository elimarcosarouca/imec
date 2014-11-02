package br.com.ss.academico.controlador;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import br.com.ss.academico.dominio.Aluno;
import br.com.ss.academico.dominio.Mensalidade;
import br.com.ss.academico.enumerated.StatusPagamento;
import br.com.ss.academico.servico.MensalidadeServico;

@ManagedBean
@SessionScoped
public class HomeControlador {

	@ManagedProperty(value = "#{mensalidadeServicoImpl}")
	private MensalidadeServico mensalidadeServico;
	
	/** Aluno usado no filtro de pesquisa. */
	private Aluno aluno;
	
	private List<Mensalidade> mensalidadesAtraso;

	private List<SelectItem> statusList;
	
	private StatusPagamento statusPagamento;

	@PostConstruct
	public void init() {
		statusList = new ArrayList<SelectItem>();
		statusList.add(new SelectItem(StatusPagamento.PENDENTE, StatusPagamento.PENDENTE.getDescricao()));
		statusList.add(new SelectItem(StatusPagamento.PAGO, StatusPagamento.PAGO.getDescricao()));
		statusPagamento = StatusPagamento.PENDENTE;
		reload();
	}


	public void reload() {
		mensalidadesAtraso = mensalidadeServico.listarMensalidadesEmAtraso(aluno, statusPagamento);
	}
	

	public MensalidadeServico getMensalidadeServico() {
		return mensalidadeServico;
	}

	public void setMensalidadeServico(MensalidadeServico mensalidadeServico) {
		this.mensalidadeServico = mensalidadeServico;
	}

	public List<Mensalidade> getMensalidadesAtraso() {
		return mensalidadesAtraso;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}


	public List<SelectItem> getStatusList() {
		return statusList;
	}


	public StatusPagamento getStatusPagamento() {
		return statusPagamento;
	}


	public void setStatusPagamento(StatusPagamento statusPagamento) {
		this.statusPagamento = statusPagamento;
	}

}