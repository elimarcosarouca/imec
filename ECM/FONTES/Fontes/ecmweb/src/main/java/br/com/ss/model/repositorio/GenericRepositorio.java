package br.com.ss.model.repositorio;

import java.io.Serializable;
import java.util.List;

public interface GenericRepositorio<T, ID extends Serializable> {

	public void save(T abstractEntity);

	// public AbstractEntity getByPrimaryKey(AbstractEntity entity);

	T getByPrimaryKey(Long id);

	T merge(T entity);

	T merge(T entity, boolean flush);

	void remove(T entity);

	void remove(Long id);

	void persist(T entity);

	void persist(T entity, boolean flush);

	void saveOrUpdate(T entity);

	void saveOrUpdate(T entity, boolean flush);

	void flush();

	void flush(boolean flush);

	List<T> listAll();
	
}