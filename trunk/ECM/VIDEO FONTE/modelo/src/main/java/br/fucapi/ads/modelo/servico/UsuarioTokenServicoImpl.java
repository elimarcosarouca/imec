package br.fucapi.ads.modelo.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.ads.modelo.dominio.UsuarioToken;
import br.fucapi.ads.modelo.repositorio.UsuarioTokenRepositorio;

@Service
public class UsuarioTokenServicoImpl implements UsuarioTokenServico {

	@Autowired
	private UsuarioTokenRepositorio usuarioRepositorio;
	
	@Override
	public UsuarioToken findByToken(String token) {
		return usuarioRepositorio.findByToken(token);
	}

	@Transactional
	public UsuarioToken salvar(UsuarioToken usuarioToken) {
		return usuarioRepositorio.save(usuarioToken);
	}

	public UsuarioTokenRepositorio getUsuarioRepositorio() {
		return usuarioRepositorio;
	}

	public void setUsuarioRepositorio(UsuarioTokenRepositorio usuarioRepositorio) {
		this.usuarioRepositorio = usuarioRepositorio;
	}

}
