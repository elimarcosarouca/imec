package br.fucapi.bpms.web.repositorio;

import java.util.List;

import br.fucapi.bpms.web.dominio.FamiliaProduto;

public interface FamiliaProdutoRepositorio {

	public void save(FamiliaProduto familiaProduto);

	public List<FamiliaProduto> list();

	public FamiliaProduto getByPrimaryKey(FamiliaProduto entity);

	public FamiliaProduto getByPrimaryKey(Long id);

	public FamiliaProduto merge(FamiliaProduto entity);

	public FamiliaProduto merge(FamiliaProduto entity, boolean flush);

	public void remove(FamiliaProduto entity);

	public void remove(Long id);

	public void persist(FamiliaProduto entity);

	public void persist(FamiliaProduto entity, boolean flush);

	public void saveOrUpdate(FamiliaProduto entity);

	public void saveOrUpdate(FamiliaProduto entity, boolean flush);

	public void flush();

	public void flush(boolean flush);

	public List<FamiliaProduto> listAll();
	
	public List<FamiliaProduto> search(FamiliaProduto entity);

}
