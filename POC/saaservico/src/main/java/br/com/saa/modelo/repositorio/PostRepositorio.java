package br.com.saa.modelo.repositorio;

import java.util.List;

import br.com.saa.modelo.entidade.Post;

public interface PostRepositorio {

	public void save(Post post);

	public List<Post> list();

	public Post getByPrimaryKey(Post entity);

	public Post getByPrimaryKey(Long id);

	public Post merge(Post entity);

	public Post merge(Post entity, boolean flush);

	public void remove(Post entity);

	public void remove(Long id);

	public void persist(Post entity);

	public void persist(Post entity, boolean flush);

	public void saveOrUpdate(Post entity);

	public void saveOrUpdate(Post entity, boolean flush);

	public void flush();

	public void flush(boolean flush);

	public List<Post> listAll();

	public List<Post> search(Post entity);

}
