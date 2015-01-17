package br.fucapi.ads.modelo.servico.impl;

import java.io.Serializable;
import java.util.List;

import br.fucapi.ads.modelo.dominio.AbstractEntity;
import br.fucapi.ads.modelo.repositorio.GenericRepositorio;
import br.fucapi.ads.modelo.servico.Servico;


public abstract class GenericServico<T extends AbstractEntity, ID extends Serializable>
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

	@Override
	public List<T> pesquisar(T entity) {
		return this.getDao().listAll();
	}

}