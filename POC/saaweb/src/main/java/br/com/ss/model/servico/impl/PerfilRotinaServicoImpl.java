package br.com.ss.model.servico.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ss.model.entidade.PerfilRotina;
import br.com.ss.model.repositorio.GenericRepositorio;
import br.com.ss.model.repositorio.PerfilRotinaRepositorio;
import br.com.ss.model.servico.PerfilRotinaServico;

@Service
@Transactional
public class PerfilRotinaServicoImpl extends
		GenericServicoImpl<PerfilRotina, Long> implements PerfilRotinaServico {

	private static final long serialVersionUID = -1380136082551143545L;

	@Autowired
	private PerfilRotinaRepositorio repositorio;

	@Override
	protected GenericRepositorio<PerfilRotina, Long> getDao() {
		return repositorio;
	}

	@Override
	public List<PerfilRotina> pesquisar(PerfilRotina entity) {
		return repositorio.pesquisar(entity);
	}
}