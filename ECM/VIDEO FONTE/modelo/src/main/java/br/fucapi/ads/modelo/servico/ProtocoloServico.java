package br.fucapi.ads.modelo.servico;

import java.util.List;

import org.springframework.data.repository.query.Param;

import br.fucapi.ads.modelo.dominio.Protocolo;

public interface ProtocoloServico {

	public List<Protocolo> listarTodos();

	public Protocolo salvar(Protocolo protocolo);

	public void remover(Protocolo protocolo);

	public Protocolo gerarProtocolo();

	public Protocolo gerarProtocolo(String tipoProtocolo);

	public Protocolo findOne(Integer id);

	public Protocolo findByAno(int ano);

	public Protocolo findByTipoProtocolo(
			@Param("tipoProtocolo") String tipoProtocolo);

}
