package br.fucapi.ads.modelo.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.ads.modelo.dominio.TipoDocumento;
import br.fucapi.ads.modelo.repositorio.GenericRepositorio;
import br.fucapi.ads.modelo.repositorio.TipoDocumentoRepositorio;
import br.fucapi.ads.modelo.servico.TipoDocumentoServico;

@Service
@Transactional
public class TipoDocumentoServicoImpl extends
		GenericServico<TipoDocumento, Long> implements TipoDocumentoServico {

	private static final long serialVersionUID = -1380136082551143545L;

	@Autowired
	private TipoDocumentoRepositorio repositorio;

	@Override
	protected GenericRepositorio<TipoDocumento, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<TipoDocumento> pesquisar(TipoDocumento entity) {
		return repositorio.pesquisar(entity);
	}
}