package br.fucapi.bpms.web.repositorio;

import java.util.List;

import br.fucapi.bpms.web.dominio.Protocolo;

public interface ProtocoloRepositorio {
	
	public void save(Protocolo protocolo);

	public List<Protocolo> list();

	public Protocolo getByPrimaryKey(Protocolo entity);

	public Protocolo getByPrimaryKey(Long id);

	public Protocolo merge(Protocolo entity);

	public Protocolo merge(Protocolo entity, boolean flush);;

	public void remove(Protocolo entity);

	public void remove(Long id);

	public void persist(Protocolo entity);;

	public void persist(Protocolo entity, boolean flush);

	public void saveOrUpdate(Protocolo entity);

	public void saveOrUpdate(Protocolo entity, boolean flush);;

	public void flush();

	public void flush(boolean flush);

	public List<Protocolo> listAll();

	public Protocolo gerarProtocolo();

}