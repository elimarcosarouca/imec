package br.fucapi.ads.modelo.controlador;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.FacesException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.fucapi.ads.modelo.dominio.UsuarioToken;
import br.fucapi.ads.modelo.servico.UsuarioTokenServico;
import br.fucapi.bpms.alfresco.dominio.Usuario;
import br.fucapi.bpms.alfresco.servico.AlfrescoServico;

@ManagedBean
@ViewScoped
public class RecuperaSenhaControlador implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{alfrescoServicoImpl}")
	private AlfrescoServico alfrescoServico;
	
	@ManagedProperty(value = "#{usuarioTokenServicoImpl}")
	private UsuarioTokenServico usuarioTokenServico;
	
	private String token;
	
	private Usuario usuario;
	
	private String confirmaSenha;
	
	@PostConstruct
	public void init() {

		this.usuario = new Usuario();
		
		FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();
        HttpServletRequest request = (HttpServletRequest)extContext.getRequest();
		token = request.getParameter("token");
	
		// TODO AJUSTAR
		UsuarioToken usuarioToken = (UsuarioToken) usuarioTokenServico.getByPrimaryKey(new Long (token));
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(usuarioToken.getDataGeracao());
		cal.add(Calendar.HOUR, 24);
		Date futureDate = cal.getTime();
		Date dataAtual = new Date();
		
		System.out.println(usuarioToken.getUserName());
		System.out.println(this.usuario.getUserName());
		
		
		//if (usuarioToken.getUtilizado() || dataAtual.after(futureDate) || usuarioToken.getUserName().equals(this.usuario.getUserName())) {
		if (usuarioToken.getUtilizado()) {
			request.getSession().setAttribute("sucesso", false);
			
			try {
	            String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/login.xhtml"));
	
	            extContext.redirect(url);
	        } catch (IOException e) {
	            throw new FacesException(e);
	        }
		}
	}
	
	public String modificarSenha() throws RemoteException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication.isAuthenticated()) {
			SecurityContextHolder.clearContext();
		}
		
//		UsuarioToken usuarioToken2 = usuarioTokenServico.findByToken(token);
//		TODO
		UsuarioToken usuarioToken2 = null;
		if (usuarioToken2 != null && usuarioToken2.getUserName().equals(usuario.getUserName()) && !usuarioToken2.getUtilizado()) {
	
			Date dataToken = usuarioToken2.getDataGeracao();
	
			Calendar cal = Calendar.getInstance();
			cal.setTime(dataToken);
			cal.add(Calendar.HOUR, 24);
			Date futureDate = cal.getTime();
			Date dataAtual = new Date();
			
			System.out.println(dataAtual);
			System.out.println(dataToken);
	
			if (dataAtual.after(futureDate)) {
				
				FacesContext ctx = FacesContext.getCurrentInstance();
		        ExternalContext extContext = ctx.getExternalContext();
		        HttpServletRequest request = (HttpServletRequest)extContext.getRequest();
				request.getSession().setAttribute("sucesso", false);
				
				return "login.xhtml?faces-redirect=true";
				
			} else {
	
				alfrescoServico.atualizarSenha(this.usuario.getUserName(),
						this.usuario.getSenha());
				UsuarioToken usuarioToken = usuarioToken2;
				usuarioToken.setUtilizado(true);
				usuarioToken.setDataUtilizacao(dataAtual);
				usuarioTokenServico.save(usuarioToken);
				
				}
	
		} else {
			
			FacesContext ctx = FacesContext.getCurrentInstance();
	        ExternalContext extContext = ctx.getExternalContext();
	        HttpServletRequest request = (HttpServletRequest)extContext.getRequest();
			request.getSession().setAttribute("sucesso", false);
			
			return "login.xhtml?faces-redirect=true";
			
			
		}
						
//		SecurityContextHolder.clearContext();
//		FacesContext facesContext = FacesContext.getCurrentInstance();
//	
//		HttpSession session = (HttpSession) facesContext.getExternalContext()
//				.getSession(false);
//		if (session != null) {
//			session.invalidate();
//		}
		
		FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();
        HttpServletRequest request = (HttpServletRequest)extContext.getRequest();
		request.getSession().setAttribute("sucesso", true);
		
		return "login.xhtml?faces-redirect=true";
	
	}
	
	public AlfrescoServico getAlfrescoServico() {
		return alfrescoServico;
	}

	public void setAlfrescoServico(AlfrescoServico alfrescoServico) {
		this.alfrescoServico = alfrescoServico;
	}
	
	public UsuarioTokenServico getUsuarioTokenServico() {
		return usuarioTokenServico;
	}

	public void setUsuarioTokenServico(UsuarioTokenServico usuarioTokenServico) {
		this.usuarioTokenServico = usuarioTokenServico;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}
	
}
