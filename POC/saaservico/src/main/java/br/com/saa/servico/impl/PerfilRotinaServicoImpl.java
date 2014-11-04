package br.com.saa.servico.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import br.com.saa.modelo.entidade.PerfilRotina;
import br.com.saa.servico.PerfilRotinaServico;

@Service
public class PerfilRotinaServicoImpl extends
		ServicoImpl<PerfilRotina, Serializable> implements PerfilRotinaServico {

}