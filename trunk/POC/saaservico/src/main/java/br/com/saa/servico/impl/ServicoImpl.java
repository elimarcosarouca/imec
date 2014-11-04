package br.com.saa.servico.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.saa.modelo.entidade.AbstractEntity;
import br.com.saa.modelo.repositorio.GenericRepositorio;
import br.com.saa.servico.Servico;

@Service
public class ServicoImpl<T extends AbstractEntity, ID extends Serializable>
		implements Servico<T, ID> {

	@Autowired
	private GenericRepositorio<AbstractEntity, Serializable> dao;

	@Override
	public void save(AbstractEntity abstractEntity) {
		dao.save(abstractEntity);
	}

	@Override
	public AbstractEntity getByPrimaryKey(AbstractEntity entity) {
		return dao.getByPrimaryKey(entity);
	}

	@Override
	public AbstractEntity getByPrimaryKey(Long id) {
		return dao.getByPrimaryKey(id);
	}

	@Override
	public AbstractEntity merge(AbstractEntity entity) {
		return dao.merge(entity);
	}

	@Override
	public AbstractEntity merge(AbstractEntity entity, boolean flush) {
		// TODO Auto-generated method stub
		return dao.merge(entity, flush);
	}

	@Override
	public void remove(AbstractEntity entity) {
		dao.remove(entity);
	}

	@Override
	public void remove(Long id) {
		dao.remove(id);
	}

	@Override
	public void persist(AbstractEntity entity) {
		dao.persist(entity);
	}

	@Override
	public void persist(AbstractEntity entity, boolean flush) {
		dao.persist(entity, flush);
	}

	@Override
	public void saveOrUpdate(AbstractEntity entity) {
		dao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdate(AbstractEntity entity, boolean flush) {
		dao.saveOrUpdate(entity, flush);
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
		return dao.listAll();
	}

}