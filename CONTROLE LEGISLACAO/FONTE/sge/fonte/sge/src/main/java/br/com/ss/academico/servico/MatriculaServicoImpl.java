package br.com.ss.academico.servico;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.ss.academico.dominio.Aluno;
import br.com.ss.academico.dominio.Matricula;
import br.com.ss.academico.dominio.Observacao;
import br.com.ss.academico.dominio.Turma;
import br.com.ss.academico.enumerated.StatusMatricula;
import br.com.ss.academico.repositorio.MatriculaRepositorio;
import br.com.ss.academico.repositorio.MatriculaRepositorioHql;
import br.com.ss.core.seguranca.repositorio.ServicoImpl;

@Service
public class MatriculaServicoImpl extends ServicoImpl<Matricula, Long> implements MatriculaServico {

	private static final long serialVersionUID = -4305564891244729963L;

	@Autowired
	private MatriculaRepositorio repositorio;

	@Autowired
	private MatriculaRepositorioHql repositorioHql;


	@Override
	protected JpaRepository<Matricula, Long> getRepository() {
		return repositorio;
	}

	@Override
	public List<Matricula> findByAluno(Aluno aluno) {
		return this.repositorio.findByAluno(aluno);
	}

	@Override
	public Long countVagasDisponiveis(Turma turma) {
		Long count = repositorio.countMatriculas(turma, StatusMatricula.ATIVA, turma.getAno());
		return turma.getNumeroVagas() - count;
	}
	
	@Override
	public Matricula loadMatriculaMensalidades(Matricula matricula) {
		return this.repositorio.loadMatriculaMensalidades(matricula);
	}

	@Override
	public List<Matricula> pesquisar(Matricula entity) {
		return repositorioHql.pesquisar(entity);
	}


	@Override
	public List<Observacao> loadObservacoes(Matricula matricula) {
		return repositorioHql.loadObservacoes(matricula);
	}

	@Override
	public List<Matricula> listarAniversariantes(Date dataInicial,
			Date dataFinal) {
		return repositorioHql.listarAniversariantes(dataInicial, dataFinal);
	}

}