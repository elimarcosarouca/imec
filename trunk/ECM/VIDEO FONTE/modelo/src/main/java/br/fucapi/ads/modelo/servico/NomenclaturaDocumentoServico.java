package br.fucapi.ads.modelo.servico;

import br.fucapi.ads.modelo.dominio.NomenclaturaDocumento;

public interface NomenclaturaDocumentoServico extends
		Servico<NomenclaturaDocumento, Long> {

	NomenclaturaDocumento pegarSequencial(NomenclaturaDocumento abstractEntity);

}