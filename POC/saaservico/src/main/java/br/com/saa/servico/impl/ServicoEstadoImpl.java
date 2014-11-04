package br.com.saa.servico.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import br.com.saa.modelo.entidade.Estado;
import br.com.saa.servico.EstadoServico;

@Service
public class ServicoEstadoImpl extends ServicoImpl<Estado, Serializable>
		implements EstadoServico {

}