package br.com.saa.servico;

import java.util.List;

import br.com.saa.modelo.entidade.Perfil;
import br.com.saa.modelo.entidade.Usuario;
import br.com.saa.modelo.entidade.UsuarioPerfil;

public interface UsuarioPerfilServico {

	public List<UsuarioPerfil> listarTodos();

	public UsuarioPerfil salvar(UsuarioPerfil usuarioPerfil);

	public void remover(UsuarioPerfil usuarioPerfil);

	public List<Perfil> listaPerfilNotInUsuario(Long idUsuario);

	public List<UsuarioPerfil> findByUsuario(Usuario usuario);
}
