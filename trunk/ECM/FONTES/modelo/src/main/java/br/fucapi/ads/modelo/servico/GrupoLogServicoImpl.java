package br.fucapi.ads.modelo.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.ads.modelo.dominio.GrupoLog;
import br.fucapi.ads.modelo.repositorio.GrupoLogRepositorio;

@Service
public class GrupoLogServicoImpl implements GrupoLogServico {

	@Autowired
	GrupoLogRepositorio repositorio;
	
	
	@Override
	public List<GrupoLog> listarTodos() {
		return repositorio.findAll();
	}

	@Override
	@Transactional
	public GrupoLog salvar(GrupoLog grupoLog) {
		return repositorio.saveAndFlush(grupoLog);
	}
	

}
