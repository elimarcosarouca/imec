package br.com.saa.servico.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import br.com.saa.modelo.entidade.Perfil;
import br.com.saa.servico.PerfilServico;

@Service
public class PerfilServicoImpl extends ServicoImpl<Perfil, Serializable>
		implements PerfilServico {

}