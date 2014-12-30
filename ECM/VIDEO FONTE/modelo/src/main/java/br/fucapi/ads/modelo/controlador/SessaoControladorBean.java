package br.fucapi.ads.modelo.controlador;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;

import br.fucapi.bpms.alfresco.dominio.Usuario;

@ManagedBean
@SessionScoped
public class SessaoControladorBean {

	private Usuario usuario;
	private String data;
	private Date dataAtual = new Date();
	private Integer tempoMensagem;
	
	@PostConstruct
	public void init() {
		this.usuario = (Usuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
	
	public void atualizarData(){
		dataAtual = Calendar.getInstance(new Locale("pt","BR")).getTime();
		data = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy, hh:mm").format(dataAtual);
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
}
