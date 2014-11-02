package br.com.ss.academico.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ss.academico.dominio.Curso;
import br.com.ss.academico.dominio.CursoDisciplina;
import br.com.ss.academico.dominio.Disciplina;
import br.com.ss.academico.repositorio.CursoDisciplinaRepositorio;
import br.com.ss.academico.repositorio.CursoDisciplinaRepositorioSql;

@Service
public class CursoDisciplinaServicoImpl implements CursoDisciplinaServico {

	@Autowired
	private CursoDisciplinaRepositorio repositorio;

	@Autowired
	private CursoDisciplinaRepositorioSql repositorioSql;

	@Override
	public List<CursoDisciplina> listarTodos() {
		return this.repositorio.findAll();
	}

	@Override
	public CursoDisciplina salvar(CursoDisciplina cursoDisciplina) {
		return this.repositorio.save(cursoDisciplina);
	}

	@Override
	public void remover(CursoDisciplina cursoDisciplina) {
		this.repositorio.delete(cursoDisciplina);

	}

	@Override
	public List<CursoDisciplina> findByCurso(Curso curso) {
		return repositorio.findByCurso(curso);
	}

	@Override
	public List<Disciplina> listaDisciplinaNotInCurso(Long idCurso) {
		return repositorioSql.listaDisciplinaNotInCurso(idCurso);
	}
}