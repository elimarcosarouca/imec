package br.com.saa.modelo.repositorio.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.saa.enumerated.StatusUsuario;
import br.com.saa.modelo.entidade.Estado;
import br.com.saa.modelo.entidade.Municipio;
import br.com.saa.modelo.entidade.Rotina;
import br.com.saa.modelo.entidade.Sistema;
import br.com.saa.modelo.entidade.Usuario;
import br.com.saa.modelo.repositorio.EstadoRepositorio;
import br.com.saa.modelo.repositorio.MunicipioRepositorio;
import br.com.saa.modelo.repositorio.SistemaRepositorio;
import br.com.saa.modelo.repositorio.UsuarioRepositorio;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/applicationContextServicoSaa.xml")
// @ContextConfiguration(locations="classpath:META-INF/test-context.xml")
// @Ignore
public class MunicipioRepositoryTest {

	@Autowired
	EstadoRepositorio estadoRepositorio;

	@Autowired
	MunicipioRepositorio municipioRepositorio;

	@Autowired
	UsuarioRepositorio usuarioRepositorio;

	@Autowired
	SistemaRepositorio sistemaRepositorio;;

	@Test
	public void test() {

		Estado estado = new Estado();
		estado.setId(1l);
		estado.setNome("AMAZONAS");
		estado.setUf("AM");
		estadoRepositorio.save(estado);

		Municipio municipio = new Municipio();
		municipio.setNome("Manaus");
		municipio.setEstado(estado);
		municipioRepositorio.save(municipio);

		Usuario usuario = new Usuario();
		usuario.setDataCadastro(new Date());
		usuario.setEmail("claudemirramosferreira@gmail.com");
		usuario.setEndereco("bbbbbb");
		usuario.setLogin("admin");
		usuario.setMunicipio(municipio);
		usuario.setNome("claudemir ramos ferreira");
		usuario.setSenha("8C6976E5B5410415BDE908BD4DEE15DFB167A9C873FC4BB8A81F6F2AB448A918");
		usuario.setStatus(StatusUsuario.ATIVA);
		usuario.setId(1l);
		usuarioRepositorio.save(usuario);

		Sistema sistema = new Sistema();
		sistema.setId(1l);
		sistema.setCodigo("SAA");
		sistema.setImagem("eeeeee");
		sistema.setNome("SAA");
		
		sistemaRepositorio.save(sistema);
		
		Rotina rotina = new Rotina();
		
		

	}

}