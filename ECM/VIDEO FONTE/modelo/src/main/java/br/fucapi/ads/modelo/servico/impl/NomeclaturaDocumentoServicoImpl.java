package br.fucapi.ads.modelo.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.ads.modelo.dominio.NomeclaturaDocumento;
import br.fucapi.ads.modelo.repositorio.GenericRepositorio;
import br.fucapi.ads.modelo.repositorio.NomeclaturaDocumentoRepositorio;
import br.fucapi.ads.modelo.servico.NomeclaturaDocumentoServico;

@Service
@Transactional
public class NomeclaturaDocumentoServicoImpl extends
		GenericServico<NomeclaturaDocumento, Long> implements
		NomeclaturaDocumentoServico {

	private static final long serialVersionUID = -1380136082551143545L;

	@Autowired
	private NomeclaturaDocumentoRepositorio repositorio;

	@Override
	protected GenericRepositorio<NomeclaturaDocumento, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<NomeclaturaDocumento> pesquisar(NomeclaturaDocumento entity) {
		return repositorio.pesquisar(entity);
	}

	@Override
	public NomeclaturaDocumento pegarSequencial(
			NomeclaturaDocumento abstractEntity) {
		
		return repositorio.pegarSequencial(abstractEntity);
		
	}
}