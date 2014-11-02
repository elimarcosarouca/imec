package br.com.ss.academico.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.ss.academico.dominio.Disciplina;
import br.com.ss.academico.repositorio.DisciplinaRepositorio;
import br.com.ss.academico.repositorio.DisciplinaRepositorioSql;
import br.com.ss.core.seguranca.repositorio.ServicoImpl;

@Service
public class DisciplinaServicoImpl extends ServicoImpl<Disciplina, Long> implements DisciplinaServico {

	private static final long serialVersionUID = -4305564891244729963L;

	@Autowired
	private DisciplinaRepositorio repositorio;

	@Autowired
	private DisciplinaRepositorioSql disciplinaRepositorioSql;


	@Override
	public List<Disciplina> findByNomeLike(String nome) {
		return this.repositorio.findByNomeLike(nome);
	}

	@Override
	public List<Disciplina> listaDisciplinaPorCurso(Long idCurso) {
		return this.disciplinaRepositorioSql.listaDisciplinaPorCurso(idCurso);
	}

	@Override
	public List<Disciplina> pesquisar(Disciplina entity) {
		return disciplinaRepositorioSql.pesquisar(entity);
	}

	@Override
	protected JpaRepository<Disciplina, Long> getRepository() {
		return repositorio;
	}
}