package br.com.ss;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.ss.academico.dominio.Michell;
import br.com.ss.academico.repositorio.MichellRepositorio;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:META-INF/spring/applicationContext.xml")
// @ContextConfiguration(locations="classpath*:META-INF/spring/test-context.xml")
 @Ignore
public class MichellRepositoryTest {

	@Autowired
	MichellRepositorio repository;

	@Test
	public void test() {

		Michell u = new Michell();

		u.setNome("micheel telo");

		repository.save(u);

		// List<Perfil> perfis = repository.listarPerfilPorUsuario(2);

		// assertNotNull(perfis);

		// Perfil dbperfil = repository.findOne(perfil.getPerfilId());
		// assertNotNull(dbperfil);
		// System.out.println(dbperfil.getTitle());
	}

}