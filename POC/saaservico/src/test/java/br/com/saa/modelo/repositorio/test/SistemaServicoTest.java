package br.com.saa.modelo.repositorio.test;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.saa.modelo.entidade.Tipo;
import br.com.saa.modelo.repositorio.TipoRepositorio;

@RunWith(SpringJUnit4ClassRunner.class)
 @ContextConfiguration(locations ="classpath:META-INF/applicationContextServicoSaa.xml")
//@ContextConfiguration(locations = "classpath:META-INF/test-context.xml")
@Ignore
public class SistemaServicoTest {

	@Autowired
	TipoRepositorio servico;

	@Test
	public void test() {

		List<Tipo> lista = servico.listAll();
		for (Tipo tipo : lista) {
			System.out.println(tipo.getNome());
		}

	}

}