package br.com.ss.model.servico;

import java.util.List;

import br.com.ss.model.entidade.TipoDocumento;

public interface TipoDocumentoServico extends Servico<TipoDocumento, Long> {

	public List<TipoDocumento> pesquisar(TipoDocumento abstractEntity);

}