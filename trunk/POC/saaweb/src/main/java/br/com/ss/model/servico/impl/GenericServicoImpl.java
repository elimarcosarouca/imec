package br.com.ss.model.servico.impl;

import java.io.Serializable;
import java.util.List;

import br.com.ss.model.entidade.AbstractEntity;
import br.com.ss.model.repositorio.GenericRepositorio;
import br.com.ss.model.servico.Servico;

public abstract class GenericServicoImpl<T extends AbstractEntity, ID extends Serializable>
		implements Servico<T, ID>, Serializable {

	private static final long serialVersionUID = 8852196823485825472L;

	protected abstract GenericRepositorio<T, Long> getDao();

	@Override
	public void save(T abstractEntity) {
		getDao().save(abstractEntity);
	}

	@Override
	public T getByPrimaryKey(T entity) {
		return null;
	}

	@Override
	public T getByPrimaryKey(Long id) {
		return getDao().getByPrimaryKey(id);
	}

	@Override
	public T merge(T entity) {
		return getDao().merge(entity);
	}

	@Override
	public T merge(T entity, boolean flush) {
		return getDao().merge(entity, flush);
	}

	@Override
	public void remove(T entity) {
		getDao().remove(entity);

	}

	@Override
	public void remove(Long id) {
		getDao().remove(id);
	}

	@Override
	public void persist(T entity) {
		getDao().persist(entity);
	}

	@Override
	public void persist(T entity, boolean flush) {
		getDao().persist(entity, flush);
	}

	@Override
	public void saveOrUpdate(T entity) {
		getDao().saveOrUpdate(entity);

	}

	@Override
	public void saveOrUpdate(T entity, boolean flush) {
		getDao().saveOrUpdate(entity, flush);

	}

	@Override
	public List<T> listAll() {
		return getDao().listAll();
	}
}