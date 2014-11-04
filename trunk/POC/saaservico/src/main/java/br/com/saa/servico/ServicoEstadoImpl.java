package br.com.saa.servico;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import br.com.saa.modelo.entidade.Estado;

@Service
public class ServicoEstadoImpl extends ServicoImpl<Estado, Serializable>
		implements ServicoEstado {

}