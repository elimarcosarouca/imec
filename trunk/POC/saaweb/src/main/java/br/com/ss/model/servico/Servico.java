package br.com.ss.model.servico;

import java.io.Serializable;
import java.util.List;

import br.com.ss.model.entidade.AbstractEntity;

public interface Servico<T, ID extends Serializable> {

	void save(T abstractEntity);

	AbstractEntity getByPrimaryKey(T entity);

	AbstractEntity getByPrimaryKey(Long id);

	AbstractEntity merge(T entity);

	AbstractEntity merge(T entity, boolean flush);

	void remove(T entity);

	void remove(Long id);

	void persist(T entity);

	void persist(T entity, boolean flush);

	void saveOrUpdate(T entity);

	void saveOrUpdate(T entity, boolean flush);

	List<T> listAll();

	public List<T> pesquisar(T entity);

}