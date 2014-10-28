package br.com.saa.servico;

import java.util.List;

import br.com.saa.modelo.entidade.Perfil;

public interface PerfilServico extends IService<Perfil, Long> {

	public List<Perfil> findByNomeLike(String nome);

	public List<Perfil> listaPerfilPorSistemaPorUsuario(int sistemaId,
			Long usuarioId);

}