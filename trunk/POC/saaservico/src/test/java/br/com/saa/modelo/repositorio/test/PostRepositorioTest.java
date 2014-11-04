package br.com.saa.modelo.repositorio.test;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.saa.modelo.entidade.Post;
import br.com.saa.modelo.repositorio.PostRepositorio;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/applicationContextServicoSaa.xml")
// @ContextConfiguration(locations="classpath:META-INF/test-context.xml")
@Ignore
public class PostRepositorioTest {

	@Autowired
	PostRepositorio repository;

	@Test
	public void test() {
		Post post = new Post();
		post.setPostDate(new Date());
		post.setTitle("claudemir  Post");

		repository.save(post);

		// Post dbpost = repository.get
		// assertNotNull(dbpost);
		// System.out.println(dbpost.getTitle());
	}

}
