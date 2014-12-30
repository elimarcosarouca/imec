package br.fucapi.bpms.web.repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.bpms.web.dominio.FamiliaProduto;
import br.fucapi.bpms.web.dominio.Origem;

@Repository("familiaProdutoRepositorioImpl")
@Transactional
public class FamiliaProdutoRepositorioImpl implements FamiliaProdutoRepositorio {

	@PersistenceContext
	private EntityManager entityManager;

	public void save(FamiliaProduto familiaProduto) {
		entityManager.persist(familiaProduto);
		entityManager.flush();
	}

	@SuppressWarnings("unchecked")
	public List<FamiliaProduto> list() {
		return entityManager.createQuery("select t from FamiliaProduto t order by t.nome")
				.getResultList();
	}

	// @Override
	public FamiliaProduto getByPrimaryKey(FamiliaProduto entity) {
		return entityManager.find(FamiliaProduto.class, entity.getId());
	}

	// @Override
	public FamiliaProduto getByPrimaryKey(Long id) {
		return entityManager.find(FamiliaProduto.class, id);
	}

	// @Override
	public FamiliaProduto merge(FamiliaProduto entity) {
		return merge(entity, true);
	}

	// @Override
	public FamiliaProduto merge(FamiliaProduto entity, boolean flush) {
		FamiliaProduto t = entityManager.merge(entity);
		flush(flush);
		return t;
	}

	// @Override
	public void remove(FamiliaProduto entity) {
		entityManager.remove(entity);
		flush();
	}

	// @Override
	public void remove(Long id) {
		entityManager.remove(entityManager.find(FamiliaProduto.class, id));
		flush();
	}

	// @Override
	public void persist(FamiliaProduto entity) {
		persist(entity, true);
	}

	public void persist(FamiliaProduto entity, boolean flush) {
		entityManager.persist(entity);
		flush(flush);
	}

	public void saveOrUpdate(FamiliaProduto entity) {
		saveOrUpdate(entity, true);
	}

	// @Override
	public void saveOrUpdate(FamiliaProduto entity, boolean flush) {
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
	public List<FamiliaProduto> listAll() {
		CriteriaQuery<FamiliaProduto> criteria = entityManager
				.getCriteriaBuilder().createQuery(FamiliaProduto.class);
		return (List<FamiliaProduto>) this.entityManager.createQuery(
				criteria.select(criteria.from(FamiliaProduto.class)))
				.getResultList();
	}
	
	public List<FamiliaProduto> search(FamiliaProduto entity) {
		StringBuilder s = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		s.append(" select p from FamiliaProduto p ");

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
