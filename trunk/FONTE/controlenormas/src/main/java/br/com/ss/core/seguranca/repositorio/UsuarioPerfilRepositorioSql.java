package br.com.ss.core.seguranca.repositorio;

import java.util.List;

import br.com.ss.core.seguranca.dominio.Perfil;

public interface UsuarioPerfilRepositorioSql {

	public List<Perfil> listaPerfilNotInUsuario(Long idUsuario);

}