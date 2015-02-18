package br.fucapi.ads.modelo.repositorio;

import java.util.List;

import br.fucapi.ads.modelo.dominio.NomeclaturaDocumento;

public interface NomeclaturaDocumentoRepositorio extends
		GenericRepositorio<NomeclaturaDocumento, Long> {

	List<NomeclaturaDocumento> pesquisar(NomeclaturaDocumento abstractEntity);

	NomeclaturaDocumento pegarSequencial(NomeclaturaDocumento abstractEntity);

}