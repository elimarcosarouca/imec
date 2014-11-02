package br.com.ss.academico.servico;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.ss.academico.dominio.Responsavel;
import br.com.ss.academico.repositorio.ResponsavelRepositorio;
import br.com.ss.academico.repositorio.ResponsavelRepositorioJPA;
import br.com.ss.core.seguranca.repositorio.ServicoImpl;

@Service
public class ResponsavelServicoImpl extends ServicoImpl<Responsavel, Long> implements ResponsavelServico, Serializable {

	private static final long serialVersionUID = -4305564891244729963L;

	@Autowired
	private ResponsavelRepositorio repositorio;

	@Autowired
	private ResponsavelRepositorioJPA repositorioJpa;

	@Override
	protected JpaRepository<Responsavel, Long> getRepository() {
		return repositorio;
	}

	@Override
	public List<Responsavel> findByNomeLike(String nome) {
		return this.repositorioJpa.findByNomeLike(nome);
	}

	@Override
	public Responsavel findByNome(String nome) {
		return this.repositorioJpa.findByNome(nome);
	}

	@Override
	public List<Responsavel> pesquisar(Responsavel entity) {
		return pesquisar(entity, null);
	}

	@Override
	public List<Responsavel> pesquisar(Responsavel entity, String nomeAluno) {
		return repositorioJpa.pesquisar(entity, nomeAluno);
	}


}