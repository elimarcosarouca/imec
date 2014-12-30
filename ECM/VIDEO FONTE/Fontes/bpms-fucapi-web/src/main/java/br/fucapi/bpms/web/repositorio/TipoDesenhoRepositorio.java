package br.fucapi.bpms.web.repositorio;

import java.util.List;

import br.fucapi.bpms.web.dominio.TipoDesenho;
import br.fucapi.bpms.web.dominio.TipoDocumento;

public interface TipoDesenhoRepositorio {

	public void save(TipoDesenho tipoDocumento);

	public List<TipoDesenho> list();

	public TipoDesenho getByPrimaryKey(TipoDesenho entity);

	public TipoDesenho getByPrimaryKey(Long id);

	public TipoDesenho merge(TipoDesenho entity);

	public TipoDesenho merge(TipoDesenho entity, boolean flush);

	public void remove(TipoDesenho entity);

	public void remove(Long id);

	public void persist(TipoDesenho entity);

	public void persist(TipoDesenho entity, boolean flush);

	public void saveOrUpdate(TipoDesenho entity);

	public void saveOrUpdate(TipoDesenho entity, boolean flush);

	public void flush();

	public void flush(boolean flush);

	public List<TipoDesenho> listAll();
	
	public List<TipoDesenho> search(TipoDesenho entity);

}
