package br.fucapi.bpms.web.repositorio;

import java.util.List;

import br.fucapi.bpms.web.dominio.Origem;
import br.fucapi.bpms.web.dominio.TipoDesenho;

public interface OrigemRepositorio {

	public void save(Origem familiaProduto);

	public List<Origem> list();

	public Origem getByPrimaryKey(Origem entity);

	public Origem getByPrimaryKey(Long id);

	public Origem merge(Origem entity);

	public Origem merge(Origem entity, boolean flush);

	public void remove(Origem entity);

	public void remove(Long id);

	public void persist(Origem entity);

	public void persist(Origem entity, boolean flush);

	public void saveOrUpdate(Origem entity);

	public void saveOrUpdate(Origem entity, boolean flush);

	public void flush();

	public void flush(boolean flush);

	public List<Origem> listAll();
	
	public List<Origem> search(Origem entity);

}
