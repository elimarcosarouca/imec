package br.com.ss.academico.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.ss.academico.dominio.Aluno;
import br.com.ss.academico.dominio.Turma;
import br.com.ss.academico.repositorio.AlunoRepositorio;
import br.com.ss.academico.repositorio.AlunoRepositorioJPA;
import br.com.ss.core.seguranca.repositorio.ServicoImpl;

@Service
public class AlunoServicoImpl extends ServicoImpl<Aluno, Long> implements AlunoServico {

	private static final long serialVersionUID = -4305564891244729963L;

	@Autowired
	private AlunoRepositorio repositorio;
	
	@Autowired
	private AlunoRepositorioJPA repositorioJpa;

	@Override
	protected JpaRepository<Aluno, Long> getRepository() {
		return repositorio;
	}
	
	@Override
	public List<Aluno> findByTurma(Turma turma) {
		return this.repositorio.findByTurma(turma);
	}

	@Override
	public List<Aluno> pesquisar(Aluno entity) {
		return repositorioJpa.findByEntity(entity);
	}

}