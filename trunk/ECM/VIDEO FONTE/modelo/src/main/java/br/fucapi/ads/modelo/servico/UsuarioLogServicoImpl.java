package br.fucapi.ads.modelo.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.ads.modelo.dominio.UsuarioLog;
import br.fucapi.ads.modelo.repositorio.UsuarioLogRepositorio;

@Service
public class UsuarioLogServicoImpl implements UsuarioLogServico {

	@Autowired
	UsuarioLogRepositorio repositorio;

	@Override
	public List<UsuarioLog> listarTodos() {
		return repositorio.findAll();
	}

	@Override
	@Transactional
	public UsuarioLog salvar(UsuarioLog UsuarioLog) {
		return repositorio.saveAndFlush(UsuarioLog);
	}

}
