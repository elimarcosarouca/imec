package br.com.ss.model.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ss.model.entidade.Licenca;
import br.com.ss.model.repositorio.GenericRepositorio;
import br.com.ss.model.repositorio.LicencaRepositorio;
import br.com.ss.model.servico.LicencaServico;

@Service
@Transactional
public class LicencaServicoImpl extends GenericServicoImpl<Licenca, Long>
		implements LicencaServico {

	private static final long serialVersionUID = -3466651042452229159L;

	@Autowired
	private LicencaRepositorio repositorio;

	@Override
	protected GenericRepositorio<Licenca, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<Licenca> pesquisar(Licenca entity) {
		return repositorio.pesquisar(entity);
	}
}