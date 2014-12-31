package br.fucapi.ads.modelo.repositorio.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.fucapi.ads.modelo.dominio.TipoDocumento;
import br.fucapi.ads.modelo.repositorio.TipoDocumentoRepositorio;

@Repository
@SuppressWarnings("unchecked")
public class TipoDocumentoRepositorioImpl extends
		GenericRepositorioImpl<TipoDocumento, Long> implements
		TipoDocumentoRepositorio {

	@Override
	public List<TipoDocumento> pesquisar(TipoDocumento abstractEntity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select est from TipoDocumento est ");

		if (notEmpty(abstractEntity.getNome())) {
			condictions.add(" lower( est.nome ) like :nome ");
		}

		String orderBy = " order by est.nome";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);
		if (notEmpty(abstractEntity.getNome())) {
			query.setParameter("nome", "%"
					+ abstractEntity.getNome().trim().toLowerCase() + "%");
		}

		return query.getResultList();
	}
}