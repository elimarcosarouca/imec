package br.fucapi.bpms.web.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import br.fucapi.bpms.alfresco.dominio.GrupoAlfresco;
import br.fucapi.bpms.alfresco.dominio.Usuario;
import br.fucapi.bpms.alfresco.servico.AlfrescoServico;

@Component
public class BpmsAuthenticationManager implements AuthenticationManager {

	@Autowired
	private AlfrescoServico alfrescoServico;
	
	private Usuario usuario;
	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		
		Authentication auth = null;
//		Usuario usuario = alfrescoServico.autenticarUsuario(authentication.getName(), authentication.getCredentials().toString());
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		for (GrupoAlfresco grupo : usuario.getGroups()) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + grupo.getDisplayName());
			auths.add(grantedAuthority);
		}
		auth = new UsernamePasswordAuthenticationToken(usuario.getUserName(), usuario.getSenha(), auths);
		return auth;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
