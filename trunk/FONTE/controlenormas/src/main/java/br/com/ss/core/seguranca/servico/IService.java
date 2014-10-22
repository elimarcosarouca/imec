package br.com.ss.core.seguranca.servico;

import java.io.Serializable;
import java.util.List;

public interface IService<T, ID extends Serializable> {

	public List<T> listarTodos();

	public T findByPrimaryKey(Long id);
	
	public List<T> pesquisar(T entity);
	
	public T salvar(T entity);

	public void remover(T entity);
	
}
