package br.com.saa.servico.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import br.com.saa.modelo.entidade.Rotina;
import br.com.saa.servico.RotinaServico;

@Service
public class RotinaServicoImpl extends ServicoImpl<Rotina, Serializable>
		implements RotinaServico {

}