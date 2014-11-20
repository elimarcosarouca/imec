package br.com.ss.model.repositorio;

import java.util.List;

import br.com.ss.model.entidade.PostoCopia;

public interface PostoCopiaRepositorio extends
		GenericRepositorio<PostoCopia, Long> {

	List<PostoCopia> pesquisar(PostoCopia abstractEntity);
}