package br.com.saa.servico.impl;

import java.io.Serializable;
import java.util.List;

import br.com.saa.modelo.entidade.AbstractEntity;
import br.com.saa.modelo.repositorio.GenericRepositorio;
import br.com.saa.servico.Servico;

public abstract class GenericServico<T extends AbstractEntity, ID extends Serializable> implements
		Servico<T, ID>, Serializable {

	private static final long serialVersionUID = 8852196823485825472L;

	protected abstract GenericRepositorio<T, Long> getDao();

	@Override
	public void save(AbstractEntity abstractEntity) {
		getDao().save(abstractEntity);

	}

	@Override
	public AbstractEntity getByPrimaryKey(AbstractEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractEntity getByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractEntity merge(AbstractEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractEntity merge(AbstractEntity entity, boolean flush) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(AbstractEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void persist(AbstractEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void persist(AbstractEntity entity, boolean flush) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveOrUpdate(AbstractEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveOrUpdate(AbstractEntity entity, boolean flush) {
		// TODO Auto-generated method stub

	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public void flush(boolean flush) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<AbstractEntity> listAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
