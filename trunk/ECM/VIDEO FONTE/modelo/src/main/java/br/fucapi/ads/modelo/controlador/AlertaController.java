package br.fucapi.ads.modelo.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.fucapi.ads.modelo.dominio.Alerta;
import br.fucapi.ads.modelo.servico.AlertaServico;

@Controller
@RequestMapping("/contato/**")
public class AlertaController {

	@Autowired
	private AlertaServico alertaServico;

	@RequestMapping(value = "/contato", method = RequestMethod.GET)
	public String list() {
		List<Alerta> lista = alertaServico.listAll();
		System.out.println(lista.size());
		return "contato/list";
	}

}