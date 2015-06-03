package br.fucapi.ads.modelo.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.ads.modelo.dominio.Alerta;
import br.fucapi.ads.modelo.repositorio.GenericRepositorio;
import br.fucapi.ads.modelo.repositorio.AlertaRepositorio;
import br.fucapi.ads.modelo.servico.AlertaServico;

@Service
@Transactional
public class AlertaServicoImpl extends GenericServico<Alerta, Long> implements
		AlertaServico {

	private static final long serialVersionUID = -1380136082551143545L;

	@Autowired
	private AlertaRepositorio repositorio;

	@Override
	protected GenericRepositorio<Alerta, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<Alerta> pesquisar(Alerta entity) {
		return repositorio.pesquisar(entity);
	}

	@Override
	public Alerta pesquisarProcessInstanceId(String processInstanceId) {
		return repositorio.pesquisarProcessInstanceId(processInstanceId);
	}
}