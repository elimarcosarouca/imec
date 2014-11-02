package br.com.ss.core.seguranca.web.auth;

import java.util.ArrayList;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.ss.core.seguranca.dominio.Usuario;
import br.com.ss.core.seguranca.servico.UsuarioServico;
import br.com.ss.core.web.utils.CriptografiaUtil;

@Component
public class SSAuthenticationManager implements AuthenticationProvider {

	@Autowired
	private UsuarioServico usuarioServico;


	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		try {
			/* Criptografa a senha para validar a mesma na consulta. */
			String criptSenha = CriptografiaUtil.criptografar(authentication.getCredentials().toString());
			
			Usuario usuario = usuarioServico.findByLoginAndSenha( authentication.getName(), criptSenha );
			
			if (usuario == null) {
				// excecao..
				throw new BadCredentialsException("ERRO Autenticando o usuario: " + authentication.getName() );
			}

			UsernamePasswordAuthenticationToken authenticatedUser = new UsernamePasswordAuthenticationToken(
					usuario, null, new ArrayList<GrantedAuthority>() );

			SecurityContextHolder.getContext().setAuthentication( authenticatedUser );

			return authenticatedUser;

		} catch (NoResultException e) {
			e.printStackTrace();
		}

		return null;

	}

	public boolean supports(Class<?> arg0) {
		/* default - this is a supported Authentication */
		return true;
	}

}
