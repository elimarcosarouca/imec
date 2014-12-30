package teste;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.bpms.web.dominio.FamiliaProduto;
import br.fucapi.bpms.web.repositorio.FamiliaProdutoRepositorio;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/META-INF/spring/applicationContext-fucapi-web.xml")
@TransactionConfiguration(defaultRollback=false)
public class Teste1 {

	@Autowired
	private FamiliaProdutoRepositorio familiaProdutoRepositorio;

	@Transactional
	@Test
	public void ttt() {
		FamiliaProduto familiaProduto = new FamiliaProduto();
		familiaProduto.setNome("grande Familia");
		familiaProdutoRepositorio.save(familiaProduto);
	}
	
}
