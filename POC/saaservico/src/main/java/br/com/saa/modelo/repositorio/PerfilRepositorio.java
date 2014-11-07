package br.com.saa.modelo.repositorio;

import java.util.List;

import br.com.saa.modelo.entidade.Perfil;
import br.com.saa.modelo.entidade.Sistema;

public interface PerfilRepositorio extends GenericRepositorio<Perfil, Long> {

	public List<Perfil> listaPerfilPorSistemaPorUsuario(int sistemaId,
			Long usuarioId);

	public List<Perfil> listaPerfil(Sistema sistema);

}