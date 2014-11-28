package br.com.ss.model.repositorio.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.ss.model.entidade.AbstractEntity;
import br.com.ss.model.repositorio.GenericRepositorio;

@SuppressWarnings("unchecked")
@Repository
@Transactional
public abstract class GenericRepositorioImpl<T extends AbstractEntity, ID extends Serializable>
		implements GenericRepositorio<T, ID> {

	@PersistenceContext
	protected EntityManager entityManager;

	@SuppressWarnings("rawtypes")
	protected Class persistentClass;

	public GenericRepositorioImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override
	public void save(T abstractEntity) {
		entityManager.persist(abstractEntity);
		entityManager.flush();
	}

	/*
	 * @Override public AbstractEntity getByPrimaryKey(AbstractEntity
	 * abstractEntity) { return entityManager.find(persistentClass,
	 * abstractEntity.getId()); }
	 */

	@Override
	public T getByPrimaryKey(Long id) {
		return entityManager.find(getPersistentClass(), id);
	}

	@Override
	public T merge(T abstractEntity) {
		return merge(abstractEntity, true);
	}

	@Override
	public T merge(T abstractEntity, boolean flush) {
		T t = entityManager.merge(abstractEntity);
		flush(flush);
		return t;
	}

	@Override
	public void remove(T abstractEntity) {
		entityManager.remove(entityManager.getReference(persistentClass,
				abstractEntity.getId()));
		flush();
	}

	@Override
	public void remove(Long id) {
		entityManager.remove(entityManager.find(persistentClass, id));
		flush();
	}

	@Override
	public void persist(T abstractEntity) {
		persist(abstractEntity, true);
	}

	@Override
	public void persist(T abstractEntity, boolean flush) {
		entityManager.persist(abstractEntity);
		flush(flush);
	}

	@Override
	public void saveOrUpdate(T abstractEntity) {
		saveOrUpdate(abstractEntity, true);
	}

	@Override
	public void saveOrUpdate(T abstractEntity, boolean flush) {
		if (abstractEntity.getId() == null) {
			persist(abstractEntity);
		} else {
			merge(abstractEntity);
		}
		flush(flush);
	}

	@Override
	public void flush() {
		entityManager.flush();
	}

	public void flush(boolean flush) {
		if (flush) {
			flush();
		}
	}

	@Override
	public List<T> listAll() {
		CriteriaQuery<T> criteria = entityManager.getCriteriaBuilder()
				.createQuery(persistentClass);
		return (List<T>) this.entityManager.createQuery(
				criteria.select(criteria.from(persistentClass)))
				.getResultList();
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

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

}