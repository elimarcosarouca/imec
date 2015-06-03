package br.fucapi.ads.modelo.servico;

import br.fucapi.ads.modelo.dominio.Alerta;

public interface AlertaServico extends Servico<Alerta, Long> {
	
	public Alerta pesquisarProcessInstanceId(String processInstanceId);

}