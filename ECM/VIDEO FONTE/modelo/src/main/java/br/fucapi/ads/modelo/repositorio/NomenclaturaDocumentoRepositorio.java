package br.fucapi.ads.modelo.repositorio;

import java.util.List;

import br.fucapi.ads.modelo.dominio.NomenclaturaDocumento;

public interface NomenclaturaDocumentoRepositorio extends
		GenericRepositorio<NomenclaturaDocumento, Long> {

	List<NomenclaturaDocumento> pesquisar(NomenclaturaDocumento abstractEntity);

	NomenclaturaDocumento pegarSequencial(NomenclaturaDocumento abstractEntity);

}