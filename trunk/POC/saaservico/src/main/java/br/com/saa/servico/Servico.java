package br.com.saa.servico;

import java.io.Serializable;
import java.util.List;

import br.com.saa.modelo.entidade.AbstractEntity;

public interface Servico<T, ID extends Serializable> {

	public void save(AbstractEntity abstractEntity);

	public AbstractEntity getByPrimaryKey(AbstractEntity entity);

	public AbstractEntity getByPrimaryKey(Long id);

	public AbstractEntity merge(AbstractEntity entity);

	public AbstractEntity merge(AbstractEntity entity, boolean flush);

	public void remove(AbstractEntity entity);

	public void remove(Long id);

	public void persist(AbstractEntity entity);

	public void persist(AbstractEntity entity, boolean flush);

	public void saveOrUpdate(AbstractEntity entity);

	public void saveOrUpdate(AbstractEntity entity, boolean flush);

	public void flush();

	public void flush(boolean flush);

	public List<AbstractEntity> listAll();

}