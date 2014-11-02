package br.com.ss.academico.repositorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ss.academico.dominio.Matricula;
import br.com.ss.academico.dominio.Observacao;
import br.com.ss.core.seguranca.repositorio.RepositorioGenerico;

@SuppressWarnings("unchecked")
@Repository
public class MatriculaRepositorioHqlImpl extends RepositorioGenerico implements MatriculaRepositorioHql {
	
	@Override
	public List<Matricula> pesquisar(Matricula entity) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select mat from Matricula mat ");

		if (notEmpty(entity.getAluno())
				&& notEmpty(entity.getAluno().getNome())) {
			condictions.add(" lower( mat.aluno.nome ) like :nomeAluno ");
		}
		if (notEmpty(entity.getStatus())) {
			condictions.add(" mat.status = :status ");
		}
		if (notEmpty(entity.getTurma().getAno())
				|| notEmpty(entity.getTurma().getTurno())) {
			sb.append(" join mat.turma turma ");
			if (notEmpty(entity.getTurma().getAno())
					&& entity.getTurma().getAno() > 0) {
				condictions.add(" turma.ano = :ano ");
			}
			if (notEmpty(entity.getTurma().getTurno())) {
				condictions.add(" turma.turno = :turno ");
			}
		}
		if (notEmpty(entity.getTurma().getCurso())) {
			sb.append(" join mat.turma.curso curso ");
			if (notEmpty(entity.getTurma().getCurso())) {
				condictions.add(" curso = :curso ");
			}
		}

		String orderBy = " order by mat.data desc, mat.aluno.nome asc ";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);
		if (notEmpty(entity.getAluno())
				&& notEmpty(entity.getAluno().getNome())) {
			query.setParameter("nomeAluno", "%"
					+ entity.getAluno().getNome().trim().toLowerCase() + "%");
		}
		if (notEmpty(entity.getStatus())) {
			query.setParameter("status", entity.getStatus());
		}
		if (notEmpty(entity.getTurma().getAno())
				&& entity.getTurma().getAno() > 0) {
			query.setParameter("ano", entity.getTurma().getAno());
		}
		if (notEmpty(entity.getTurma().getTurno())) {
			query.setParameter("turno", entity.getTurma().getTurno());
		}
		if (notEmpty(entity.getTurma().getCurso())) {
			query.setParameter("curso", entity.getTurma().getCurso());
		}
		return query.getResultList();
	}

	@Override
	public List<Observacao> loadObservacoes(Matricula matricula) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select ob from Observacao ob");
		sb.append(" where ob.matricula = :matricula ");
		return entityManager.createQuery(sb.toString())
				.setParameter("matricula", matricula).getResultList();
	}

	@Override
	public List<Matricula> listarAniversariantes(Date dataInicial,
			Date dataFinal) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();

		sb.append(" select mat from Matricula mat ");
		condictions
				.add("  mat.aluno.dataNascimento > :dataInicial and mat.aluno.dataNascimento < :dataFinal ");

//		sb.append(" join mat.turma turma ");
//		condictions.add(" turma.ano = :ano ");
		String orderBy = " order by mat.aluno.dataNascimento desc, mat.aluno.nome asc ";

		Query query = entityManager.createQuery(generateHql(sb.toString(),
				condictions) + orderBy);

		query.setParameter("dataInicial", dataInicial);
		
		query.setParameter("dataFinal", dataFinal);

		return query.getResultList();
	}
	
}