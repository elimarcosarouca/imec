package br.fucapi.bpms.web.repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.bpms.web.dominio.MetadadoDesenho;
import br.fucapi.bpms.web.dominio.Situacao;

@Repository("metadadoDesenhoRepositorioImpl")
@Transactional
public class MetadadoDesenhoRepositorioImpl implements
		MetadadoDesenhoRepositorio {

	@PersistenceContext
	private EntityManager entityManager;

	public void save(MetadadoDesenho metadadoDesenho) {
		entityManager.persist(metadadoDesenho);
		entityManager.flush();
	}

	@SuppressWarnings("unchecked")
	public List<MetadadoDesenho> list() {
		return entityManager.createQuery(
				"select t from MetadadoDesenho t order by t.businessKey")
				.getResultList();
	}

	public MetadadoDesenho getByPrimaryKey(MetadadoDesenho entity) {
		return entityManager.find(MetadadoDesenho.class, entity.getId());
	}

	public MetadadoDesenho getByPrimaryKey(Long id) {
		return entityManager.find(MetadadoDesenho.class, id);
	}

	public MetadadoDesenho merge(MetadadoDesenho entity) {
		return merge(entity, true);
	}

	public MetadadoDesenho merge(MetadadoDesenho entity, boolean flush) {
		MetadadoDesenho t = entityManager.merge(entity);
		flush(flush);
		return t;
	}

	public void remove(MetadadoDesenho entity) {
		entityManager.remove(entity);
		flush();
	}

	public void remove(Long id) {
		entityManager.remove(entityManager.find(MetadadoDesenho.class, id));
		flush();
	}

	public void persist(MetadadoDesenho entity) {
		persist(entity, true);
	}

	public void persist(MetadadoDesenho entity, boolean flush) {
		entityManager.persist(entity);
		flush(flush);
	}

	public void saveOrUpdate(MetadadoDesenho entity) {
		saveOrUpdate(entity, true);
	}

	public void saveOrUpdate(MetadadoDesenho entity, boolean flush) {
		if (entity.getId() == null) {
			persist(entity);
		} else {
			merge(entity);
		}
		flush(flush);
	}

	public void flush() {
		entityManager.flush();
	}

	public void flush(boolean flush) {
		if (flush) {
			flush();
		}
	}

	public List<MetadadoDesenho> listAll() {
		CriteriaQuery<MetadadoDesenho> criteria = entityManager
				.getCriteriaBuilder().createQuery(MetadadoDesenho.class);
		return (List<MetadadoDesenho>) this.entityManager.createQuery(
				criteria.select(criteria.from(MetadadoDesenho.class)))
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<MetadadoDesenho> pesquisar(MetadadoDesenho entity,
			Boolean valido, Boolean transitorio, Boolean invalido) {
		StringBuilder query = new StringBuilder(
				"select * from siem_metadado_desenho where meta_dado_ori_id = "
						+ entity.getOrigem().getId());

		if (entity.getGrupo() != null && !entity.getGrupo().equals("")) {
			query.append(" and meta_dese_grupo like '" + entity.getGrupo()
					+ "'");
		}

		if (entity.getComplemento() != null
				&& !entity.getComplemento().equals("")) {
			query.append(" and meta_dese_complemento like '"
					+ entity.getComplemento() + "'");
		}

		if (!((valido && invalido && transitorio) || (valido.equals(false)
				&& invalido.equals(false) && transitorio.equals(false)))) {
			StringBuilder strAux = new StringBuilder(
					" and meta_dese_situacao_versao_atual in (");

			if (valido && invalido) {
				strAux.append("'" + Situacao.VALIDO + "', '"
						+ Situacao.INVALIDO + "'");
			} else if (valido && transitorio) {
				strAux.append("'" + Situacao.VALIDO + "', '"
						+ Situacao.TRANSITORIO + "'");
			} else if (invalido && transitorio) {
				strAux.append("'" + Situacao.INVALIDO + "', '"
						+ Situacao.TRANSITORIO + "'");
			} else if (valido) {
				strAux.append("'" + Situacao.VALIDO + "'");
			} else if (invalido) {
				strAux.append("'" + Situacao.INVALIDO + "'");
			} else {
				strAux.append("'" + Situacao.TRANSITORIO + "'");
			}

			query.append(strAux.append(")"));
		}

		System.out.println(query.toString());
		Query q = this.entityManager.createNativeQuery(query.toString(),
				MetadadoDesenho.class);

		return q.getResultList();

	}

	public List<MetadadoDesenho> pesquisarDesenhoAnterior(MetadadoDesenho entity) {
		StringBuilder s = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		s.append(" select p from MetadadoDesenho p ");
		if (notEmpty(entity.getGrupo()))
			condictions.add(" lower(p.grupo) like :grupo ");

		if (notEmpty(entity.getComplemento()))
			condictions.add(" lower(p.complemento) like :complemento ");

		String orderBy = " order by p.businessKey ";

		Query q = this.entityManager.createQuery(generateHql(s.toString(),
				condictions) + orderBy);

		if (notEmpty(entity.getGrupo()))
			q.setParameter("grupo", entity.getGrupo().trim().toLowerCase());

		if (notEmpty(entity.getComplemento()))
			q.setParameter("complemento", entity.getComplemento().trim()
					.toLowerCase());

		List<MetadadoDesenho> list = q.getResultList();

		return list;

	}

	public boolean notEmpty(Object obj) {
		return obj != null;
	}

	public boolean notEmpty(String str) {
		return str != null && str.length() > 0;
	}

	public static String montarSituacao(boolean valido, boolean transitorio,
			boolean invalido) {
		String sql = "";

		if (valido)
			sql = "VALIDO";
		else if (transitorio)
			sql = "TRANSITORIO";
		else if (invalido)
			sql = "INVALIDO";

		return sql;
	}

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
