package br.fucapi.ads.modelo.servico;

import java.util.List;

import br.fucapi.ads.modelo.dominio.GrupoLog;

public interface GrupoLogServico  {
	
	public List<GrupoLog> listarTodos();
	
	public GrupoLog salvar (GrupoLog grupoLog);

}
