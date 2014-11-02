package br.com.ss.core.seguranca.repositorio;

import java.util.List;

import br.com.ss.core.seguranca.dominio.Perfil;

public interface PerfilRepositorioSqlHql {

	public List<Perfil> listaPerfilPorSistemaPorUsuario(int sistemaId, Long usuarioId);

	public List<Perfil> listaPerfil(Perfil perfil);
	
}