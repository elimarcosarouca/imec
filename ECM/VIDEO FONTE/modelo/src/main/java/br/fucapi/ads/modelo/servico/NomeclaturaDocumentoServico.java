package br.fucapi.ads.modelo.servico;

import br.fucapi.ads.modelo.dominio.NomeclaturaDocumento;

public interface NomeclaturaDocumentoServico extends
		Servico<NomeclaturaDocumento, Long> {

	NomeclaturaDocumento pegarSequencial(NomeclaturaDocumento abstractEntity);

}