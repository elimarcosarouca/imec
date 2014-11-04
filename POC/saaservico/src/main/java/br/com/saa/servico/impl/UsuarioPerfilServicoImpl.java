package br.com.saa.servico.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import br.com.saa.modelo.entidade.UsuarioPerfil;
import br.com.saa.servico.UsuarioPerfilServico;

@Service
public class UsuarioPerfilServicoImpl extends
		ServicoImpl<UsuarioPerfil, Serializable> implements
		UsuarioPerfilServico {

}