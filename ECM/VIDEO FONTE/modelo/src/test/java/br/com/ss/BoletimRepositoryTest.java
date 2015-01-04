package br.com.ss;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.fucapi.ads.modelo.dominio.Protocolo;
import br.fucapi.ads.modelo.repositorio.ProtocoloRepositorio;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/test-applicationContext.xml")
// @ContextConfiguration(locations="classpath*:META-INF/spring/test-context.xml")
 @Ignore
public class BoletimRepositoryTest {

	@Autowired
	ProtocoloRepositorio repository;

	@Test
	public void test() {
		System.out.println("1");
		Protocolo protocolo = new Protocolo();
		protocolo.setAno(2015);

		protocolo = repository.pesquisarPorAno(protocolo);

		System.out.println(protocolo.getSequencial());
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