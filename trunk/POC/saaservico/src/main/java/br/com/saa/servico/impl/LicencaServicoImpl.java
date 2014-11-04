package br.com.saa.servico.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import br.com.saa.modelo.entidade.Licenca;
import br.com.saa.servico.LicencaServico;

@Service
public class LicencaServicoImpl extends ServicoImpl<Licenca, Serializable>
		implements LicencaServico {

}