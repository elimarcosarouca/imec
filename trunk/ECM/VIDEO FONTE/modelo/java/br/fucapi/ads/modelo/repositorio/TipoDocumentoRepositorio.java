package br.fucapi.ads.modelo.repositorio;

import java.util.List;

import br.fucapi.ads.modelo.dominio.TipoDocumento;

public interface TipoDocumentoRepositorio extends
		GenericRepositorio<TipoDocumento, Long> {

	List<TipoDocumento> pesquisar(TipoDocumento abstractEntity);

}