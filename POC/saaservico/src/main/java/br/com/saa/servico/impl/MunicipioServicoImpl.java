package br.com.saa.servico.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import br.com.saa.modelo.entidade.Municipio;
import br.com.saa.servico.MunicipioServico;

@Service
public class MunicipioServicoImpl extends ServicoImpl<Municipio, Serializable>
		implements MunicipioServico {

}