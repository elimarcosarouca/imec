package br.fucapi.bpms.web.repositorio;

import java.util.List;

import br.fucapi.bpms.web.dominio.MetadadoDesenho;

public interface MetadadoDesenhoRepositorio {

	public void save(MetadadoDesenho familiaProduto);

	public List<MetadadoDesenho> list();

	public MetadadoDesenho getByPrimaryKey(MetadadoDesenho entity);

	public MetadadoDesenho getByPrimaryKey(Long id);

	public MetadadoDesenho merge(MetadadoDesenho entity);

	public MetadadoDesenho merge(MetadadoDesenho entity, boolean flush);

	public void remove(MetadadoDesenho entity);

	public void remove(Long id);

	public void persist(MetadadoDesenho entity);

	public void persist(MetadadoDesenho entity, boolean flush);

	public void saveOrUpdate(MetadadoDesenho entity);

	public void saveOrUpdate(MetadadoDesenho entity, boolean flush);

	public void flush();

	public void flush(boolean flush);

	public List<MetadadoDesenho> listAll();
	
	public List<MetadadoDesenho> pesquisar(MetadadoDesenho entity, Boolean valido, Boolean transitorio, Boolean invalido);
	
	public List<MetadadoDesenho> pesquisarDesenhoAnterior(MetadadoDesenho entity);

}
