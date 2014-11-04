package br.com.saa.modelo.repositorio.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.saa.modelo.entidade.PerfilRotina;
import br.com.saa.modelo.entidade.Rotina;
import br.com.saa.modelo.repositorio.PerfilRotinaRepositorio;

@Repository
@SuppressWarnings("unchecked")
public class PerfilRotinaRepositorioImpl extends
		GenericRepositorioImpl<PerfilRotina, Serializable> implements
		PerfilRotinaRepositorio {

	@Override
	public List<Rotina> listaRotinaNotInPerfil(Long idPerfil) {
		return entityManager.createNativeQuery(
				"select r.* from saa_rotina r " + "where r.id_rotina not in ( "
						+ "select id_rotina from saa_perfil_rotina pr "
						+ "where pr.id_perfil = " + idPerfil + ")",
				Rotina.class).getResultList();

	}

}
