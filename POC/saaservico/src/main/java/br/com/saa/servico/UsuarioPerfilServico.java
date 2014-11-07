package br.com.saa.servico;

import java.util.Set;

import br.com.saa.modelo.entidade.Usuario;
import br.com.saa.modelo.entidade.UsuarioPerfil;

public interface UsuarioPerfilServico extends Servico<UsuarioPerfil, Long> {

	public Set<UsuarioPerfil> findByUsuario(Usuario usuario);

}
