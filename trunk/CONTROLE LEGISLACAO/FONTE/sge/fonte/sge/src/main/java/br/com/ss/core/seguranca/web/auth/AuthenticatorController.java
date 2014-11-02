package br.com.ss.core.seguranca.web.auth;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.ss.core.seguranca.dominio.Perfil;
import br.com.ss.core.seguranca.dominio.Rotina;
import br.com.ss.core.seguranca.dominio.Usuario;
import br.com.ss.core.seguranca.servico.PerfilServico;
import br.com.ss.core.seguranca.servico.RotinaServico;
import br.com.ss.core.seguranca.servico.SistemaServico;
import br.com.ss.core.web.utils.FacesUtils;
import br.com.ss.core.web.utils.MessageUtils;
import br.fpf.components.web.menu.ItemMenu;
import br.fpf.components.web.menu.Menu;

import com.sun.faces.context.flash.ELFlash;

@Component
@ManagedBean
@RequestScoped
public class AuthenticatorController {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@ManagedProperty(value="#{authenticationManager}")
	private AuthenticationManager authenticationManager = null;

	@Autowired
	private SistemaServico sistemaServico;

	@Autowired
	private PerfilServico perfilServico;

	@Autowired
	private RotinaServico rotinaServico;

	private String username;

	private String password;
	
	private static final String HOME = "home";
	
	private static final long ID_EMPRESA = 1;
	
	private boolean loggedIn;
	

	public String autenticar() {
		String page = HOME;
		try {
			
			logger.info("# Autenticando o usuario: " + username );
			
            Authentication request = new UsernamePasswordAuthenticationToken(username, password);
            Authentication result = authenticationManager.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(result);
			
            // adiciona o usuario logado na sessao
            Usuario usuarioLogado = (Usuario) result.getPrincipal();
            
            carregarPerfisUsuario(usuarioLogado);
            
            FacesUtils.getRequest().getSession().setAttribute("usuarioLogado", usuarioLogado);
            
            /*
             * se quiser add msg no messages, usar o codigo abaixo (add o <h:messages /> na pg):
             * Prolonga a msg utilizando a classe: br.com.ss.core.web.controlador.listener.MultiPageMessagesSupport 
            FacesUtils.addMessage("Bem Vindo " + usuarioLogado.getNome(), null, FacesMessage.SEVERITY_INFO);
            */
            // msg no flash scope
            ELFlash.getFlash().put("msgBemVindo", "Bem Vindo " + usuarioLogado.getNome() + "!");
            
            loggedIn = true;
            
        } catch (AuthenticationException exc) {
        	logger.error("# ERRO Autenticando o usuario: " + username, exc );
            FacesUtils.addMessage("Usuario ou senha inválido!", null, FacesMessage.SEVERITY_WARN);
            page = null;
        }
		
        return page;
        
	}

	
	/**
	 * Carrega os perfis do usuario logado.
	 * @param usuario
	 */
	private void carregarPerfisUsuario(Usuario usuario) {
		String redirec = "?faces-redirect=true";
		String idSistema = MessageUtils.getMessageResourceString( MessageUtils.ID_SISTEMA );
		
		List<Perfil> perfis = perfilServico.listaPerfilPorSistemaPorUsuario( new Integer(idSistema), usuario.getId() );
		
		List<Menu> menuList = new ArrayList<Menu>();
		
		for ( Perfil per : perfis ) {
			Menu menu = new Menu(per.getNome(), null );
			List<Rotina> rotinas = rotinaServico.listaRotinasPorPerfil(per.getId());
			
			for ( Rotina rot : rotinas ) {
				ItemMenu item = new ItemMenu(null, rot.getNome(), rot.getAcao() + redirec);
				menu.getItemMenus().add(item);
			}
			
			menuList.add(menu);
			
		}
		// armazena menu na sessao do usuario
		FacesUtils.getRequest().getSession().setAttribute("menuList", menuList);
	}
	
	
	public void logout() {
		 SecurityContextHolder.clearContext();
		 FacesUtils.getRequest().getSession().invalidate();
		 loggedIn = false;
	}
	

	/* ------------ Gets/Sets ---------------*/
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	public SistemaServico getSistemaServico() {
		return sistemaServico;
	}
	public void setSistemaServico(SistemaServico sistemaServico) {
		this.sistemaServico = sistemaServico;
	}
	public PerfilServico getPerfilServico() {
		return perfilServico;
	}
	public void setPerfilServico(PerfilServico perfilServico) {
		this.perfilServico = perfilServico;
	}
	public RotinaServico getRotinaServico() {
		return rotinaServico;
	}
	public void setRotinaServico(RotinaServico rotinaServico) {
		this.rotinaServico = rotinaServico;
	}
	public boolean isLoggedIn() {
		return loggedIn;
	}
}
