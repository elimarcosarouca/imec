package br.com.saa.modelo.repositorio;

import java.io.Serializable;
import java.util.List;

import br.com.saa.modelo.entidade.Perfil;
import br.com.saa.modelo.entidade.Sistema;

public interface PerfilRepositorio extends
		GenericRepositorio<Perfil, Serializable> {

	public List<Perfil> listaPerfilPorSistemaPorUsuario(int sistemaId,
			Long usuarioId);

	public List<Perfil> listaPerfil(Sistema sistema);

}