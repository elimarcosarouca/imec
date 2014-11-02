package br.com.ss.academico.servico;

import java.util.List;

import br.com.ss.academico.dominio.Responsavel;
import br.com.ss.core.seguranca.servico.IService;

public interface ResponsavelServico extends IService<Responsavel, Long>  {

	public List<Responsavel> findByNomeLike(String nome);

	public Responsavel findByNome(String nome);

	public List<Responsavel> pesquisar(Responsavel pesquisa, String nome);
}