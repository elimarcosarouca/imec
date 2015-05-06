package br.com.ss;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.fucapi.ads.modelo.dominio.Alerta;
import br.fucapi.ads.modelo.repositorio.AlertaRepositorio;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/applicationContext-modelo.xml")
// @ContextConfiguration(locations="classpath*:META-INF/spring/test-context.xml")
 @Ignore
public class BoletimRepositoryTest {

	@Autowired
	AlertaRepositorio repository;

	@Test
	public void test() {
		System.out.println("1");
		Alerta alerta = new Alerta();
		alerta.setConcluida(false);
		alerta.setDataAlerta(new Date());
		alerta.setDataCadastro(new Date());
		alerta.setDataVencimento(new Date());
		alerta.setProtocolo("20151");
		alerta.setSolicitante("admin");

		repository.saveOrUpdate(alerta);

	}

	// @Test
	public void salvar() {
		// Boletim boletim = repository.findOne(1l);
		//
		// boletim.setMedia1( (float) 7.0);
		// repository.save(boletim);
		// System.out.println("salvou");

	}

}