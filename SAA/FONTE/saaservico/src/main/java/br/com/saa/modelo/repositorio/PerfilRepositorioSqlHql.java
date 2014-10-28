package br.com.saa.modelo.repositorio;

import java.util.List;

import br.com.saa.modelo.entidade.Perfil;

public interface PerfilRepositorioSqlHql {

	public List<Perfil> listaPerfilPorSistemaPorUsuario(int sistemaId, Long usuarioId);

	public List<Perfil> listaPerfil(Perfil perfil);
	
}