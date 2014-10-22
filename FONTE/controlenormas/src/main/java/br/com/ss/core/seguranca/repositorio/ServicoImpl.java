package br.com.ss.core.seguranca.repositorio;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ss.core.seguranca.servico.IService;


public abstract class ServicoImpl<T, ID extends Serializable> implements IService<T, ID>, Serializable {

	private static final long serialVersionUID = -3960905686839773585L;

	protected abstract JpaRepository<T, Long> getRepository();

	@Override
	public List<T> listarTodos() {
		return getRepository().findAll();
	}

	@Override
	public T salvar(T entity) {
		return getRepository().save(entity);
	}

	@Override
	public void remover(T entity) {
		getRepository().delete(entity);
	}

	@Override
	public T findByPrimaryKey(Long id) {
		return getRepository().findOne(id);
	}
	
}
