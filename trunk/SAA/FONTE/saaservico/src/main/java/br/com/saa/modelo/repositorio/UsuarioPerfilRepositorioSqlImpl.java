package br.com.saa.modelo.repositorio;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.saa.modelo.entidade.Perfil;

@Repository
public class UsuarioPerfilRepositorioSqlImpl extends RepositorioGenerico
		implements UsuarioPerfilRepositorioSql {

	@SuppressWarnings("unchecked")
	@Override
	public List<Perfil> listaPerfilNotInUsuario(Long idUsuario) {
		return entityManager.createNativeQuery(
				"select p.* from saa_perfil p " + "where p.id_perfil not in ( "
						+ "select id_perfil from saa_usuario_perfil up "
						+ "where up.id_usuario = " + idUsuario + ")",
				Perfil.class).getResultList();

	}
}