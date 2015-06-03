package br.fucapi.ads.modelo.repositorio;

import java.util.List;

import br.fucapi.ads.modelo.dominio.Alerta;

public interface AlertaRepositorio extends GenericRepositorio<Alerta, Long> {

	List<Alerta> pesquisar(Alerta abstractEntity);
	
	Alerta pesquisarProcessInstanceId(String processInstanceId);

}