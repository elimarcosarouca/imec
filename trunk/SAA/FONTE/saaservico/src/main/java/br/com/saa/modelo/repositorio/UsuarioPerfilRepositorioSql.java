package br.com.saa.modelo.repositorio;

import java.util.List;

import br.com.saa.modelo.entidade.Perfil;

public interface UsuarioPerfilRepositorioSql {

	public List<Perfil> listaPerfilNotInUsuario(Long idUsuario);

}