package br.com.saa.servico.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import br.com.saa.modelo.entidade.UsuarioSistema;
import br.com.saa.servico.UsuarioSistemaServico;

@Service
public class UsuarioSistemaServicoImpl extends
		ServicoImpl<UsuarioSistema, Serializable> implements
		UsuarioSistemaServico {

}