package br.fucapi.ads.modelo.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.ads.modelo.dominio.VariaveisTarefa;
import br.fucapi.ads.modelo.repositorio.VariaveisTarefaRepositorio;

@Service
public class VariaveisTarefaServicoImpl implements VariaveisTarefaServico {

	@Autowired
	private VariaveisTarefaRepositorio variaveisTarefaRepositorio;

	@Transactional
	public VariaveisTarefa salvar(VariaveisTarefa VariaveisTarefa) {
		return variaveisTarefaRepositorio.save(VariaveisTarefa);
	}

	@Override
	public VariaveisTarefa findById(Long id) {
		return variaveisTarefaRepositorio.findById(id);
	}

}