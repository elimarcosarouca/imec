package br.fucapi.ads.modelo.repositorio;

import java.util.List;

import br.fucapi.ads.modelo.dominio.PostoCopia;

public interface PostoCopiaRepositorio extends
		GenericRepositorio<PostoCopia, Long> {

	List<PostoCopia> pesquisar(PostoCopia abstractEntity);
}