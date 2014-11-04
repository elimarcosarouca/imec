package br.com.saa.modelo.repositorio;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.saa.modelo.entidade.Rotina;

@Repository
public class PerfilRotinaRepositorioSqlImpl extends RepositorioGenerico
		implements PerfilRotinaRepositorioSql {

	@SuppressWarnings("unchecked")
	@Override
	public List<Rotina> listaRotinaNotInPerfil(Long idPerfil) {
		return entityManager.createNativeQuery(
				"select r.* from saa_rotina r " + "where r.id_rotina not in ( "
						+ "select id_rotina from saa_perfil_rotina pr "
						+ "where pr.id_perfil = " + idPerfil + ")",
				Rotina.class).getResultList();

	}
}