package br.fucapi.bpms.web.repositorio;

import java.util.List;

import br.fucapi.bpms.web.dominio.TipoDocumento;

public interface TipoDocumentoRepositorio {

	public void save(TipoDocumento tipoDocumento);

	public List<TipoDocumento> list();

	public TipoDocumento getByPrimaryKey(TipoDocumento entity);

	public TipoDocumento getByPrimaryKey(Long id);

	public TipoDocumento merge(TipoDocumento entity);

	public TipoDocumento merge(TipoDocumento entity, boolean flush);

	public void remove(TipoDocumento entity);

	public void remove(Long id);

	public void persist(TipoDocumento entity);

	public void persist(TipoDocumento entity, boolean flush);

	public void saveOrUpdate(TipoDocumento entity);

	public void saveOrUpdate(TipoDocumento entity, boolean flush);

	public void flush();

	public void flush(boolean flush);

	public List<TipoDocumento> listAll();
	
	public List<TipoDocumento> search(TipoDocumento entity);

}
