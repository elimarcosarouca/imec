package br.com.saa.servico.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import br.com.saa.modelo.entidade.Usuario;
import br.com.saa.servico.UsuarioServico;

@Service
public class UsuarioServicoImpl extends ServicoImpl<Usuario, Serializable>
		implements UsuarioServico {

}