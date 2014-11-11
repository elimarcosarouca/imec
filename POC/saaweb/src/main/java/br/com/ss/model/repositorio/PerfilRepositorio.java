package br.com.ss.model.repositorio;

import java.util.List;

import br.com.ss.model.entidade.Perfil;
import br.com.ss.model.entidade.Sistema;

public interface PerfilRepositorio extends GenericRepositorio<Perfil, Long> {

	List<Perfil> listaPerfilPorSistemaPorUsuario(int sistemaId, Long usuarioId);

	List<Perfil> listaPerfil(Sistema sistema);

	List<Perfil> pesquisar(Perfil abstractEntity);

}