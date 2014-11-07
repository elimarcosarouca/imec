package br.com.saa.servico;

import java.util.List;

import br.com.saa.modelo.entidade.Perfil;
import br.com.saa.modelo.entidade.Sistema;

public interface PerfilServico extends Servico<Perfil, Long> {

	public List<Perfil> listaPerfilPorSistemaPorUsuario(int sistemaId,
			Long usuarioId);
	
	public List<Perfil> listaPerfilNotInUsuario(Long idUsuario);

	public List<Perfil> listaPerfil(Sistema sistema);

}