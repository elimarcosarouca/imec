package br.fucapi.ads.modelo.controlador;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;

import br.fucapi.bpms.alfresco.dominio.GrupoAlfresco;
import br.fucapi.bpms.alfresco.dominio.Usuario;
import br.fucapi.bpms.alfresco.servico.AlfrescoServico;

public class AlfrescoAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private AlfrescoServico alfrescoServico;

	@Value("${alfresco.grupo.adm}")
	private String alfrescoGrupoAdmin;
	
	@Autowired
	private LoginStatus loginStatus;
	
	@PostConstruct
	private void init() {		

	}
	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {

		Usuario usuario =  null;
		UsernamePasswordAuthenticationToken authenticatedUser = null;
		try {
			usuario = alfrescoServico.autenticarUsuario(authentication.getPrincipal()
					.toString(), authentication.getCredentials().toString());
			
			// verificar se o usuario é adminstrador
			// Solucao temporaria para resolver problema de json que define se o usuario e administrador
			
			for (GrupoAlfresco grupoAlfresco : usuario.getGroups()) {
				if (grupoAlfresco.getDisplayName().equals(alfrescoGrupoAdmin)) {
					usuario.getCapabilities().setAdmin(true);
					break;
				}
			}

			List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			
			SimpleGrantedAuthority  simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_ALFRESCO_ADMINISTRATORS");
			grantedAuthorities.add(simpleGrantedAuthority);
			
			authenticatedUser = new UsernamePasswordAuthenticationToken(
	                usuario, null, grantedAuthorities);
			
	        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
	        loginStatus.setShowMsgErro(false);

		} catch (HttpClientErrorException e) {
						
			loginStatus.setShowMsgErro(true);			
			e.printStackTrace();
						
		} catch (DocumentException e) {
			e.printStackTrace();
			loginStatus.setShowMsgErro(true);
		}

		return authenticatedUser;
	}
	

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	public AlfrescoServico getAlfrescoServico() {
		return alfrescoServico;
	}

	public void setAlfrescoServico(AlfrescoServico alfrescoServico) {
		this.alfrescoServico = alfrescoServico;
	}

	public String getAlfrescoGrupoAdmin() {
		return alfrescoGrupoAdmin;
	}

	public void setAlfrescoGrupoAdmin(String alfrescoGrupoAdmin) {
		this.alfrescoGrupoAdmin = alfrescoGrupoAdmin;
	}

	public LoginStatus getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(LoginStatus loginStatus) {
		this.loginStatus = loginStatus;
	}

}
