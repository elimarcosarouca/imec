package br.fucapi.ads.modelo.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.ads.modelo.dominio.NomenclaturaDocumento;
import br.fucapi.ads.modelo.repositorio.GenericRepositorio;
import br.fucapi.ads.modelo.repositorio.NomenclaturaDocumentoRepositorio;
import br.fucapi.ads.modelo.servico.NomenclaturaDocumentoServico;

@Service
@Transactional
public class NomenclaturaDocumentoServicoImpl extends
		GenericServico<NomenclaturaDocumento, Long> implements
		NomenclaturaDocumentoServico {

	private static final long serialVersionUID = -1380136082551143545L;

	@Autowired
	private NomenclaturaDocumentoRepositorio repositorio;

	@Override
	protected GenericRepositorio<NomenclaturaDocumento, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<NomenclaturaDocumento> pesquisar(NomenclaturaDocumento entity) {
		return repositorio.pesquisar(entity);
	}

	@Override
	public NomenclaturaDocumento pegarSequencial(
			NomenclaturaDocumento abstractEntity) {
		
		return repositorio.pegarSequencial(abstractEntity);
		
	}
}