package br.fucapi.bpms.web.repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.bpms.web.dominio.TipoDesenho;
import br.fucapi.bpms.web.dominio.TipoDocumento;

@Repository
@Transactional
public class TipoDesenhoRepositorioImpl implements TipoDesenhoRepositorio {

	@PersistenceContext
	private EntityManager entityManager;

	public void save(TipoDesenho tipoDesenho) {
		entityManager.persist(tipoDesenho);
		entityManager.flush();
	}

	@SuppressWarnings("unchecked")
	public List<TipoDesenho> list() {
		return entityManager.createQuery("select t from TipoDesenho t order by t.nome")
				.getResultList();
	}

	// @Override
	public TipoDesenho getByPrimaryKey(TipoDesenho entity) {
		return entityManager.find(TipoDesenho.class, entity.getId());
	}

	// @Override
	public TipoDesenho getByPrimaryKey(Long id) {
		return entityManager.find(TipoDesenho.class, id);
	}

	// @Override
	public TipoDesenho merge(TipoDesenho entity) {
		return merge(entity, true);
	}

	// @Override
	public TipoDesenho merge(TipoDesenho entity, boolean flush) {
		TipoDesenho t = entityManager.merge(entity);
		flush(flush);
		return t;
	}

	// @Override
	public void remove(TipoDesenho entity) {
		entityManager.remove(entity);
		flush();
	}

	// @Override
	public void remove(Long id) {
		entityManager.remove(entityManager.find(TipoDesenho.class, id));
		flush();
	}

	// @Override
	public void persist(TipoDesenho entity) {
		persist(entity, true);
	}

	public void persist(TipoDesenho entity, boolean flush) {
		entityManager.persist(entity);
		flush(flush);
	}

	public void saveOrUpdate(TipoDesenho entity) {
		saveOrUpdate(entity, true);
	}

	// @Override
	public void saveOrUpdate(TipoDesenho entity, boolean flush) {
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
	public List<TipoDesenho> listAll() {
		CriteriaQuery<TipoDesenho> criteria = entityManager
				.getCriteriaBuilder().createQuery(TipoDesenho.class);
		return (List<TipoDesenho>) this.entityManager.createQuery(
				criteria.select(criteria.from(TipoDesenho.class)))
				.getResultList();
	}
	
	public List<TipoDesenho> search(TipoDesenho entity) {
		StringBuilder s = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		s.append(" select p from TipoDesenho p ");

		if (notEmpty(entity.getNome()))
			condictions.add(" p.nome like :nome ");

		String orderBy = " order by p.nome ";

		Query q = this.entityManager.createQuery(generateHql(s.toString(),
				condictions) + orderBy);

		if (notEmpty(entity.getNome()))
			q.setParameter("nome", entity.getNome().trim());


		return q.getResultList();

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