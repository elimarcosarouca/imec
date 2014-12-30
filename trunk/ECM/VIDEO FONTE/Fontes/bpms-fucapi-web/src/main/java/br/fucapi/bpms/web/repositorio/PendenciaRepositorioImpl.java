package br.fucapi.bpms.web.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.bpms.web.dominio.Pendencia;

@Component
@Transactional
public class PendenciaRepositorioImpl implements Serializable,
		PendenciaRepositorio {

	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;

	public void save(Pendencia pendencia) {
		entityManager.persist(pendencia);
	}

	@SuppressWarnings("unchecked")
	public List<Pendencia> list() {
		return entityManager.createQuery("select t from Pendencia t")
				.getResultList();
	}

	// @Override
	public Pendencia getByPrimaryKey(Pendencia entity) {
		return entityManager.find(Pendencia.class, entity.getId());
	}

	// @Override
	public Pendencia getByPrimaryKey(Long id) {
		return entityManager.find(Pendencia.class, id);
	}

	// @Override
	public Pendencia merge(Pendencia entity) {
		return merge(entity, true);
	}

	// @Override
	public Pendencia merge(Pendencia entity, boolean flush) {
		Pendencia t = entityManager.merge(entity);
		flush(flush);
		return t;
	}

	// @Override
	public void remove(Pendencia entity) {
		entityManager.remove(entity);
		flush();
	}

	// @Override
	public void remove(Long id) {
		entityManager.remove(entityManager.find(Pendencia.class, id));
		flush();
	}

	// @Override
	public void persist(Pendencia entity) {
		persist(entity, true);
	}

	// @Override
	public void persist(Pendencia entity, boolean flush) {
		entityManager.persist(entity);
		flush(flush);
	}

	public void saveOrUpdate(Pendencia entity) {
		merge(entity);
		flush(true);
	}

	// @Override
	public void saveOrUpdate(Pendencia entity, boolean flush) {
		if (!entity.getId().equals(0)) {
			persist(entity);
		} else {
			merge(entity);
		}
		flush(flush);
	}

	// @Override
	public void flush() {
		entityManager.flush();
	}

	public void flush(boolean flush) {
		if (flush) {
			flush();
		}
	}

	// @Override
	public List<Pendencia> listAll() {
		CriteriaQuery<Pendencia> criteria = entityManager.getCriteriaBuilder()
				.createQuery(Pendencia.class);
		return (List<Pendencia>) this.entityManager.createQuery(
				criteria.select(criteria.from(Pendencia.class)))
				.getResultList();
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

	public boolean notEmpty(Object obj) {
		return obj != null;
	}

	public boolean notEmpty(String str) {
		return str != null && str.length() > 0;
	}

	protected String generateHql(String select, List<String> cond,
			String orderby) {
		StringBuilder hql = new StringBuilder();
		hql.append(select);
		boolean addWhere = false;
		for (String str : cond) {
			if (!addWhere) {
				addWhere = true;
				hql.append(" where " + str);
			} else {
				hql.append(" and " + str);
			}
		}
		if (notEmpty(orderby)) {
			hql.append(orderby);
		}
		return hql.toString();
	}

	public List<Pendencia> listarPendenciaPorLogin(String login) {
		Query q = entityManager.createNativeQuery(
				"select * from siem_pendencia pend where pend.pend_login = '"
						+ login + "' and pend_status  = false order by pend_status, pend_business_key",
				Pendencia.class);

		return q.getResultList();

	}
	
	public List<Pendencia> listarTodasPendentes() {
		Query q = entityManager.createNativeQuery(
				"select * from siem_pendencia pend where pend_status  = false ",
				Pendencia.class);

		return q.getResultList();

	}

	public List<Pendencia> listarPendenciaPorBusinessKey(String businessKey) {
		Query q = entityManager.createNativeQuery(
				"select * from siem_pendencia pend where pend.pend_business_key = '"
						+ businessKey + "' order by pend_status ",
				Pendencia.class);
		List<Pendencia> list = q.getResultList();

		return list;

	}

}