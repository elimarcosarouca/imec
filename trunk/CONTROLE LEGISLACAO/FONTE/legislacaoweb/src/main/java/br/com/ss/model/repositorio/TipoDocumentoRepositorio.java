package br.com.ss.model.repositorio;

import java.util.List;

import br.com.ss.model.entidade.TipoDocumento;

public interface TipoDocumentoRepositorio extends
		GenericRepositorio<TipoDocumento, Long> {

	List<TipoDocumento> pesquisar(TipoDocumento abstractEntity);

}