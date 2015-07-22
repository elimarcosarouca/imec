package br.fucapi.ads.modelo.controlador;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.component.growl.Growl;


@ManagedBean
@ViewScoped
public class LoginControlador implements Serializable {

	private static final long serialVersionUID = -8179037067799616207L;

	private Growl msg;

	@ManagedProperty(value = "#{loginStatus}")
	private LoginStatus loginStatus;

	@PostConstruct
	public void init() {
		if (loginStatus.isShowMsgErro()) {
			this.testeMensagem();
		}

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession();
		Boolean token_utilizado = (Boolean) session.getAttribute("sucesso");

		if (token_utilizado != null) {
			session.removeAttribute("sucesso");

			if (!token_utilizado) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_WARN, "Esse link não é válido.",
						""));
			} else {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO,
						"Operação realizada com sucesso!", ""));

			}
		}
	}

	public void testeMensagem() {
		if (loginStatus.isShowMsgErro()) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO,
					"Login ou senha estão incorretos", ""));
		}
	}

	public Growl getMsg() {
		return msg;
	}

	public void setMsg(Growl msg) {
		this.msg = msg;
	}

	public LoginStatus getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(LoginStatus loginStatus) {
		this.loginStatus = loginStatus;
	}
}
