package br.com.ss.academico.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ss.academico.dominio.Boletim;
import br.com.ss.academico.dominio.DetalheBoletim;
import br.com.ss.academico.dominio.Disciplina;
import br.com.ss.academico.dominio.Matricula;
import br.com.ss.academico.dominio.Turma;
import br.com.ss.academico.repositorio.BoletimRepositorio;
import br.com.ss.academico.repositorio.BoletimRepositorioJPA;
import br.com.ss.academico.repositorio.DisciplinaRepositorioSql;
import br.com.ss.core.seguranca.repositorio.ServicoImpl;

@Service
@Transactional
public class BoletimServicoImpl extends ServicoImpl<Boletim, Long> implements
		BoletimServico {

	private static final long serialVersionUID = -4305564891244729963L;

	@Autowired
	private BoletimRepositorio repositorio;
	
	@Autowired
	private BoletimRepositorioJPA repositorioHql;
	
	@Autowired
	private DisciplinaRepositorioSql disciplinaRepositorioSql;

	@Override
	public List<Boletim> listarTodos() {
		return this.repositorio.findAll();
	}

	@Override
	public Boletim salvar(Boletim boletim) {
		return this.repositorio.save(boletim);
	}

	@Override
	public void remover(Boletim curso) {
		this.repositorio.delete(curso);
	}

	@Override
	public List<Boletim> pesquisarBoletim(Matricula matricula) {
		return this.repositorio.pesquisarBoletim(matricula);
	}

	@Override
	public void gerarBoletim(Matricula matricula) {

		List<Disciplina> disciplinas = disciplinaRepositorioSql
				.listaDisciplinaPorCurso(matricula.getTurma().getCurso().getId());

		Boletim boletim = new Boletim();
		boletim.setMatricula(matricula);
		
		for (Disciplina disciplina : disciplinas) {
			DetalheBoletim det = new DetalheBoletim();
			det.setDisciplina(disciplina);
			det.setBoletim(boletim);
			boletim.getDetalheBoletims().add(det);
		}
		
		this.repositorio.save(boletim);
	}

	@Override
	public List<Boletim> listBoletimPorTurma(Turma turma) {
		return this.repositorioHql.listaBoletimPorTurma(turma);
	}
	
	@Override
	public List<Boletim> pesquisar(Boletim entity) {
		return null;
	}

	@Override
	protected JpaRepository<Boletim, Long> getRepository() {
		return null;
	}
}