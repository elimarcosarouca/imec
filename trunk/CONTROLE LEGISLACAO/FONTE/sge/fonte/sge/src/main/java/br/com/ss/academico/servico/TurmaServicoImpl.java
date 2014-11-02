package br.com.ss.academico.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.ss.academico.dominio.Matricula;
import br.com.ss.academico.dominio.Turma;
import br.com.ss.academico.repositorio.TurmaRepositorio;
import br.com.ss.academico.repositorio.TurmaRepositorioHql;
import br.com.ss.core.seguranca.repositorio.ServicoImpl;

@Service
public class TurmaServicoImpl extends ServicoImpl<Turma, Long> implements TurmaServico {

	private static final long serialVersionUID = -4305564891244729963L;

	@Autowired
	private TurmaRepositorio repositorio;

	@Autowired
	private TurmaRepositorioHql repositorioHql;

	@Override
	public List<Turma> findByAno(Integer ano) {
		return this.repositorio.findByAno(ano);
	}

	@Override
	public Turma findByMatricula(Matricula matricula) {
		return this.repositorio.findByMatricula(matricula);
	}

	@Override
	public List<Turma> pesquisar(Turma entity) {
		return repositorioHql.pesquisar(entity);
	}

	@Override
	protected JpaRepository<Turma, Long> getRepository() {
		return repositorio;
	}

	@Override
	public List<Turma> listarTurmasVigentes() {
		return repositorioHql.listarTurmasVigentes();
	}

}