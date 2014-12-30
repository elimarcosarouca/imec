package br.fucapi.bpms.web.repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.bpms.web.dominio.TipoDocumento;

@Repository
@Transactional
public class TipoDocumentoRepositorioImpl implements TipoDocumentoRepositorio {

	@PersistenceContext
	private EntityManager entityManager;

	public void save(TipoDocumento tipoDocumento) {
		entityManager.persist(tipoDocumento);
		entityManager.flush();
	}

	@SuppressWarnings("unchecked")
	public List<TipoDocumento> list() {
		return entityManager.createQuery("select t from TipoDocumento t order by t.nome")
				.getResultList();
	}

	// @Override
	public TipoDocumento getByPrimaryKey(TipoDocumento entity) {
		return entityManager.find(TipoDocumento.class, entity.getId());
	}

	// @Override
	public TipoDocumento getByPrimaryKey(Long id) {
		return entityManager.find(TipoDocumento.class, id);
	}

	// @Override
	public TipoDocumento merge(TipoDocumento entity) {
		return merge(entity, true);
	}

	// @Override
	public TipoDocumento merge(TipoDocumento entity, boolean flush) {
		TipoDocumento t = entityManager.merge(entity);
		flush(flush);
		return t;
	}

	// @Override
	public void remove(TipoDocumento entity) {
		entityManager.remove(entity);
		flush();
	}

	// @Override
	public void remove(Long id) {
		entityManager.remove(entityManager.find(TipoDocumento.class, id));
		flush();
	}

	// @Override
	public void persist(TipoDocumento entity) {
		persist(entity, true);
	}

	public void persist(TipoDocumento entity, boolean flush) {
		entityManager.persist(entity);
		flush(flush);
	}

	public void saveOrUpdate(TipoDocumento entity) {
		saveOrUpdate(entity, true);
	}

	// @Override
	public void saveOrUpdate(TipoDocumento entity, boolean flush) {
		if (entity.getId() == null) {
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
	public List<TipoDocumento> listAll() {
		CriteriaQuery<TipoDocumento> criteria = entityManager
				.getCriteriaBuilder().createQuery(TipoDocumento.class);
		return (List<TipoDocumento>) this.entityManager.createQuery(
				criteria.select(criteria.from(TipoDocumento.class)))
				.getResultList();
	}
	
	public List<TipoDocumento> search(TipoDocumento entity) {
		StringBuilder s = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		s.append(" select p from TipoDocumento p ");

		if (notEmpty(entity.getNome()))
			condictions.add(" p.nome like :nome ");

		String orderBy = " order by p.nome ";

		Query q = this.entityManager.createQuery(generateHql(s.toString(),
				condictions) + orderBy);

		if (notEmpty(entity.getNome()))
			q.setParameter("nome", entity.getNome().trim());

		List<TipoDocumento> list = q.getResultList();

		return list;

	}

	public boolean notEmpty(Object obj) {
		return obj != null;
	}

	public boolean notEmpty(String str) {
		return str != null && str.length() > 0;
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