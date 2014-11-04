package br.com.saa.modelo.repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.saa.modelo.entidade.Post;

@Repository
@Transactional
public class PostRepositorioImpl implements PostRepositorio {

	@PersistenceContext
	private EntityManager entityManager;

	public void save(Post post) {
		entityManager.persist(post);
		entityManager.flush();
	}

	@SuppressWarnings("unchecked")
	public List<Post> list() {
		return entityManager.createQuery(
				"select t from Post t order by t.title").getResultList();
	}

	// @Override
	public Post getByPrimaryKey(Post entity) {
		return entityManager.find(Post.class, entity.getPostId());
	}

	// @Override
	public Post getByPrimaryKey(Long id) {
		return entityManager.find(Post.class, id);
	}

	// @Override
	public Post merge(Post entity) {
		return merge(entity, true);
	}

	// @Override
	public Post merge(Post entity, boolean flush) {
		Post t = entityManager.merge(entity);
		flush(flush);
		return t;
	}

	// @Override
	public void remove(Post entity) {
		entityManager.remove(entityManager.getReference(Post.class,
				entity.getPostId()));
		flush();
	}

	// @Override
	public void remove(Long id) {
		entityManager.remove(entityManager.find(Post.class, id));
		flush();
	}

	// @Override
	public void persist(Post entity) {
		persist(entity, true);
	}

	public void persist(Post entity, boolean flush) {
		entityManager.persist(entity);
		flush(flush);
	}

	public void saveOrUpdate(Post entity) {
		saveOrUpdate(entity, true);
	}

	// @Override
	public void saveOrUpdate(Post entity, boolean flush) {
		if (entity.getPostId() == null) {
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
	public List<Post> listAll() {
		CriteriaQuery<Post> criteria = entityManager.getCriteriaBuilder()
				.createQuery(Post.class);
		return (List<Post>) this.entityManager.createQuery(
				criteria.select(criteria.from(Post.class))).getResultList();
	}

	public List<Post> search(Post entity) {
		StringBuilder s = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		s.append(" select p from Post p ");

		if (notEmpty(entity.getTitle()))
			condictions.add(" p.title like :title ");

		String orderBy = " order by p.title ";

		Query q = this.entityManager.createQuery(generateHql(s.toString(),
				condictions) + orderBy);

		if (notEmpty(entity.getTitle()))
			q.setParameter("nome", "%" + entity.getTitle().trim() + "%");

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
