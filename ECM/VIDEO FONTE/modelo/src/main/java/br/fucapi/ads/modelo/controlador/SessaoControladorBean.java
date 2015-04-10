package br.fucapi.ads.modelo.controlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;

import br.fucapi.ads.modelo.dominio.Alerta;
import br.fucapi.ads.modelo.dominio.VariavelPublicarDocumento;
import br.fucapi.ads.modelo.servico.AlertaServico;
import br.fucapi.bpms.activiti.servico.ActivitiServico;
import br.fucapi.bpms.alfresco.dominio.Usuario;

@ManagedBean
@SessionScoped
public class SessaoControladorBean {

	private Usuario usuario;
	private String data;
	private Date dataAtual = new Date();
	private Integer tempoMensagem;

	private int countTarefa;

	private int countAlerta;

	@ManagedProperty(value = "#{activitiServicoImpl}")
	private ActivitiServico activitiServico;

	@ManagedProperty(value = "#{alertaServicoImpl}")
	private AlertaServico alertaServico;

	public void countTarefaPendente(String login) throws ParseException {

		Map<String, Object> filtro = new HashMap<String, Object>();
		VariavelPublicarDocumento variavel = new VariavelPublicarDocumento();

		// Soh deverah trazer as tarefas que estao com o status pendente
		this.countTarefa = activitiServico.getHistoricoTarefasPorVariaveis(
				filtro, usuario.getUserName(), variavel.getTipoSolicitacao(),
				true, null).size();
		System.out.println(this.countTarefa);

	}

	public void countAlertaPendente(String login) {

		Alerta alerta = new Alerta();
		alerta.setDataAlerta(new Date());
		alerta.setSolicitante(login);
		alerta.setConcluida(false);
		this.countAlerta = alertaServico.pesquisar(alerta).size();
		System.out.println("alerta = " + this.countAlerta);
	}

	@PostConstruct
	public void init() {
		this.usuario = (Usuario) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		this.atualizarData();
		this.tempoMensagem = 10000;
	}

	public Usuario usuarioLogado() {
		return usuario;
	}

	public String logout() {
		SecurityContextHolder.clearContext();
		FacesContext facesContext = FacesContext.getCurrentInstance();

		HttpSession session = (HttpSession) facesContext.getExternalContext()
				.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return "/login.xhtml?faces-redirect=true";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void atualizarData() {
		dataAtual = Calendar.getInstance(new Locale("pt", "BR")).getTime();
		data = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy, hh:mm")
				.format(dataAtual);
	}

	public String getData() {
		return data;
	}

	public Integer getTempoMensagem() {
		return tempoMensagem;
	}

	public void setTempoMensagem(Integer tempoMensagem) {
		this.tempoMensagem = tempoMensagem;
	}

	public ActivitiServico getActivitiServico() {
		return activitiServico;
	}

	public void setActivitiServico(ActivitiServico activitiServico) {
		this.activitiServico = activitiServico;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public AlertaServico getAlertaServico() {
		return alertaServico;
	}

	public void setAlertaServico(AlertaServico alertaServico) {
		this.alertaServico = alertaServico;
	}

	public int getCountTarefa() {
		return countTarefa;
	}

	public void setCountTarefa(int countTarefa) {
		this.countTarefa = countTarefa;
	}

	public int getCountAlerta() {
		return countAlerta;
	}

	public void setCountAlerta(int countAlerta) {
		this.countAlerta = countAlerta;
	}

}