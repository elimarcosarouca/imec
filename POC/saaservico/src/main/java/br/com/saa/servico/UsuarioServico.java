package br.com.saa.servico;

import java.util.List;

import br.com.saa.modelo.entidade.Usuario;
import br.com.saa.modelo.entidade.UsuarioPerfil;

public interface UsuarioServico extends Servico<Usuario, Long> {

	public List<UsuarioPerfil> findByUsuario(Usuario usuario);

}