package br.com.saa.modelo.repositorio;

import java.io.Serializable;
import java.util.List;

import br.com.saa.modelo.entidade.Perfil;

public interface PerfilRepositorio extends
		GenericRepositorio<Perfil, Serializable> {

	public List<Perfil> listaPerfilPorSistemaPorUsuario(int sistemaId,
			Long usuarioId);

	public List<Perfil> listaPerfil(Perfil perfil);

}