package br.fucapi.bpms.web.repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.bpms.web.dominio.Origem;
import br.fucapi.bpms.web.dominio.TipoDocumento;

@Repository
@Transactional
public class OrigemRepositorioImpl implements OrigemRepositorio {

	@PersistenceContext
	private EntityManager entityManager;

	public void save(Origem origem) {
		entityManager.persist(origem);
		entityManager.flush();
	}

	@SuppressWarnings("unchecked")
	public List<Origem> list() {
		return entityManager.createQuery("select t from Origem t order by t.nome")
				.getResultList();
	}

	// @Override
	public Origem getByPrimaryKey(Origem entity) {
		return entityManager.find(Origem.class, entity.getId());
	}

	// @Override
	public Origem getByPrimaryKey(Long id) {
		return entityManager.find(Origem.class, id);
	}

	// @Override
	public Origem merge(Origem entity) {
		return merge(entity, true);
	}

	// @Override
	public Origem merge(Origem entity, boolean flush) {
		Origem t = entityManager.merge(entity);
		flush(flush);
		return t;
	}

	// @Override
	public void remove(Origem entity) {
		entityManager.remove(entity);
		flush();
	}

	// @Override
	public void remove(Long id) {
		entityManager.remove(entityManager.find(Origem.class, id));
		flush();
	}

	// @Override
	public void persist(Origem entity) {
		persist(entity, true);
	}

	public void persist(Origem entity, boolean flush) {
		entityManager.persist(entity);
		flush(flush);
	}

	public void saveOrUpdate(Origem entity) {
		saveOrUpdate(entity, true);
	}

	// @Override
	public void saveOrUpdate(Origem entity, boolean flush) {
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
	public List<Origem> listAll() {
		CriteriaQuery<Origem> criteria = entityManager.getCriteriaBuilder()
				.createQuery(Origem.class);
		return (List<Origem>) this.entityManager.createQuery(
				criteria.select(criteria.from(Origem.class))).getResultList();
	}
	
	public List<Origem> search(Origem entity) {
		StringBuilder s = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		s.append(" select p from Origem p ");

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
