package br.fucapi.bpms.web.controle;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.alfresco.repo.webservice.administration.AdministrationFault;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;

import br.fucapi.bpms.alfresco.dominio.GrupoAlfresco;
import br.fucapi.bpms.alfresco.dominio.Usuario;
import br.fucapi.bpms.alfresco.servico.AlfrescoServico;
import br.fucapi.bpms.web.utils.BpmsAuthenticationManager;

@ManagedBean(name = "loginControle")
@SessionScoped
public class LoginControle implements Serializable {

	private static final long serialVersionUID = -1843003170750414947L;

	private static final Logger logger = LoggerFactory
			.getLogger(LoginControle.class);

	private Usuario usuario;

	private boolean administrador = false;

	@ManagedProperty(value = "#{alfrescoServicoImpl}")
	private AlfrescoServico alfrescoServico;

	@ManagedProperty(value = "#{bpmsAuthenticationManager}")
	private BpmsAuthenticationManager authenticationManager;

	@PostConstruct
	public void init() {
		this.usuario = new Usuario();
	}

	@SuppressWarnings("unchecked")
	public String logar() {

		try {
			this.usuario = alfrescoServico.autenticarUsuario(
					this.usuario.getUserName(), this.usuario.getSenha());

			authenticationManager.setUsuario(usuario);
			Authentication request = new UsernamePasswordAuthenticationToken(
					usuario.getUserName(), usuario.getSenha());
			Authentication result = authenticationManager.authenticate(request);
			SecurityContextHolder.getContext().setAuthentication(result);

			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("usuarioLogado", this.usuario);

		} catch (HttpClientErrorException e) {

			System.out.println("Usuário e senha inválidos");
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Usuário ou senha invalido.",
							"Usuário ou senha invalido. "));
			return "/login.xhtml";

		} catch (DocumentException e) {

			e.printStackTrace();
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Ocorreu um erro. Favor entre em contato com o suporte",
									" "));
		}
		// verificar se o usuario é do grupo REVISOR_APROVADOR
		verificarGrupoRevisorAprovador(this.usuario.getUserName());

		return "/home.jsf?facesRedirect=true";
	}

	public String logoff() {

		try {
			FacesContext context = FacesContext.getCurrentInstance();
			this.usuario = (Usuario) context.getExternalContext()
					.getSessionMap().get("usuarioLogado");

			if (usuario != null)
				alfrescoServico.logout(this.usuario.getTicket().trim());

			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext()
					.getSession(false);
			session.invalidate();

			context.getExternalContext().getSessionMap()
					.remove("usuarioLogado");

			context.getExternalContext().getSessionMap()
					.remove("grupoRevisorAprovador");

		} catch (Exception e) {
			return "/pages/usuario/login.xhtml";
		}
		return "/pages/usuario/login.xhtml";
	}

	@SuppressWarnings("unchecked")
	public void verificarGrupoRevisorAprovador(String userName) {

		try {
			List<GrupoAlfresco> list = alfrescoServico
					.listarGruposPorUsuario(userName);
			for (GrupoAlfresco grupoAlfresco : list) {
				if (grupoAlfresco.getDisplayName().equals("REVISOR_APROVADOR")) {
					FacesContext.getCurrentInstance().getExternalContext()
							.getSessionMap()
							.put("grupoRevisorAprovador", grupoAlfresco);
					this.administrador = true;
					break;
				}

			}
		} catch (AdministrationFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public AlfrescoServico getAlfrescoServico() {
		return alfrescoServico;
	}

	public void setAlfrescoServico(AlfrescoServico alfrescoServico) {
		this.alfrescoServico = alfrescoServico;
	}

	public boolean isAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}

	public BpmsAuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(
			BpmsAuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
}