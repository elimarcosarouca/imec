package br.fucapi.ads.modelo.repositorio;

import java.util.List;

import br.fucapi.ads.modelo.dominio.Funcionario;
import br.fucapi.ads.modelo.dominio.Setor;

public interface FuncionarioRepositorio extends
		GenericRepositorio<Funcionario, Long> {

	List<Funcionario> pesquisar(Funcionario abstractEntity);
}