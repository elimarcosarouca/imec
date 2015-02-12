package br.fucapi.ads.modelo.repositorio;

import java.util.List;

import br.fucapi.ads.modelo.dominio.Categoria;

public interface CategoriaRepositorio extends
		GenericRepositorio<Categoria, Long> {

	List<Categoria> pesquisar(Categoria abstractEntity);

}