package br.com.ss.model.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ss.model.entidade.TipoDocumento;
import br.com.ss.model.repositorio.GenericRepositorio;
import br.com.ss.model.repositorio.TipoDocumentoRepositorio;
import br.com.ss.model.servico.TipoDocumentoServico;

@Service
@Transactional
public class TipoDocumentoServicoImpl extends
		GenericServicoImpl<TipoDocumento, Long> implements TipoDocumentoServico {

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