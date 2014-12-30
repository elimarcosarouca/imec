package br.fucapi.bpms.web.repositorio;

import java.util.List;

import br.fucapi.bpms.web.dominio.Pendencia;

public interface PendenciaRepositorio {

	public void save(Pendencia familiaProduto);

	public List<Pendencia> list();

	public Pendencia getByPrimaryKey(Pendencia entity);

	public Pendencia getByPrimaryKey(Long id);

	public Pendencia merge(Pendencia entity);

	public Pendencia merge(Pendencia entity, boolean flush);

	public void remove(Pendencia entity);

	public void remove(Long id);

	public void persist(Pendencia entity);

	public void persist(Pendencia entity, boolean flush);

	public void saveOrUpdate(Pendencia entity);

	public void saveOrUpdate(Pendencia entity, boolean flush);

	public void flush();

	public void flush(boolean flush);

	public List<Pendencia> listAll();

	public List<Pendencia> listarPendenciaPorLogin(String login);

	public List<Pendencia> listarPendenciaPorBusinessKey(String businessKey);
	
	public List<Pendencia> listarTodasPendentes();

}
