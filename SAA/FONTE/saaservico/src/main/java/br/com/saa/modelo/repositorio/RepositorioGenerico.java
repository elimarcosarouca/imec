package br.com.saa.modelo.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.saa.core.web.utils.StringUtil;

public abstract class RepositorioGenerico {

	@PersistenceContext
	protected EntityManager entityManager;

	protected boolean notEmpty(Object obj) {
		return StringUtil.notEmpty(obj);
	}

	/**
	 * Gera o código hql com as condições informadas.
	 * 
	 * <pre>
	 * Ex:
	 * * Select:
	 *  <code>select ec from EntityClass </code>
	 * * Condições:
	 *  <code>where ec.cond1 = true and ec.cond2 = 1</code>
	 * </pre>
	 * 
	 * @param select
	 *            O select da pesquisa
	 * @param stmt
	 *            Lista com as condiçoes de filtro da pesquisa (sem where e
	 *            and)
	 * @return String Hql gerado
	 */
	protected String generateHql(String select, List<String> stmt) {
		StringBuilder hql = new StringBuilder(select);
		boolean addedWhere = false;
		String where = " where ", and = " and ";
		for (String s : stmt) {
			if (addedWhere) {
				hql.append(and + s);
			} else {
				hql.append(where + s);
				addedWhere = true;
			}
		}
		return hql.toString();
	}

}
