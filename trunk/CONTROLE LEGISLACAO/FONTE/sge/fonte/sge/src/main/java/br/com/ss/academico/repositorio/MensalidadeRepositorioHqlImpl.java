package br.com.ss.academico.repositorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ss.academico.dominio.Aluno;
import br.com.ss.academico.dominio.Matricula;
import br.com.ss.academico.dominio.Mensalidade;
import br.com.ss.academico.enumerated.StatusPagamento;
import br.com.ss.academico.enumerated.TipoPesquisaData;
import br.com.ss.core.seguranca.repositorio.RepositorioGenerico;
import br.com.ss.core.web.utils.DateUtil;

@SuppressWarnings("unchecked")
@Repository
public class MensalidadeRepositorioHqlImpl extends RepositorioGenerico implements MensalidadeRepositorioHql{
	
	@Override
	public List<Mensalidade> pesquisar(Mensalidade entity, Date dataInicio, 
									Date dataFim, TipoPesquisaData tipoPesquisaData) {
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();
		
		sb.append(" select men from Mensalidade men ");
		sb.append(" join fetch men.matricula mat ");
		
		if ( notEmpty(entity.getMatricula().getAluno()) ) {
			condictions.add(" men.matricula.aluno = :aluno ");
		}
		if ( notEmpty(entity.getMatricula().getIdMatricula()) 
				&& entity.getMatricula().getIdMatricula() > 0 ) {
			condictions.add(" men.matricula.idMatricula = :idMatricula");
		}
		if ( notEmpty(entity.getStatusPagamento()) ) {
			condictions.add(" men.statusPagamento = :statusPagamento ");
		}
		if ( notEmpty(tipoPesquisaData)) {
			if ( tipoPesquisaData == TipoPesquisaData.VECIMENTO ) {
				if (notEmpty(dataInicio)) {
					condictions.add(" men.dataVencimento >= :dataInicio ");
				}
				if (notEmpty(dataFim)) {
					condictions.add(" men.dataVencimento <= :dataFim ");
				}
			} else {
				if (notEmpty(dataInicio)) {
					condictions.add(" men.dataPagamento >= :dataInicio ");
				}
				if (notEmpty(dataFim)) {
					condictions.add(" men.dataPagamento <= :dataFim ");
				}
			}
		}
		String orderBy = " order by men.dataVencimento asc, men.matricula.aluno asc, men.matricula.turma.curso.nome ";

		Query query = entityManager.createQuery(generateHql(sb.toString(), condictions) + orderBy);
		
		if ( notEmpty(entity.getMatricula().getAluno()) ) {
			query.setParameter("aluno", entity.getMatricula().getAluno());
		}
		if ( notEmpty(entity.getStatusPagamento()) ) {
			query.setParameter("statusPagamento", entity.getStatusPagamento());
		}
		if ( notEmpty(entity.getMatricula().getIdMatricula()) 
				&& entity.getMatricula().getIdMatricula() > 0 ) {
			query.setParameter("idMatricula", entity.getMatricula().getIdMatricula());
		}
		if ( notEmpty(tipoPesquisaData)) {
			if (notEmpty(dataInicio)) {
				query.setParameter("dataInicio",dataInicio);
			}
			if (notEmpty(dataFim)) {
				query.setParameter("dataFim", dataFim);
			}
			if (notEmpty(entity.getDataPagamento())) {
				
			}
		}
		return query.getResultList();
	}

	@Override
	public List<Mensalidade> listarMensalidadesEmAtraso(Aluno aluno, StatusPagamento statusPagamento) {
		
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();
		Date hoje = new Date();
		
		sb.append(" select men from Mensalidade men ");
		if(statusPagamento != null) {
			condictions.add(" men.statusPagamento = :statusPagamento ");
		}
		condictions.add(" men.dataPagamento = null ");
		condictions.add(" men.dataVencimento < :hoje ");
		
		if (notEmpty(aluno)) {
			condictions.add(" men.matricula.aluno = :aluno ");
		}
		
		String orderBy = " order by men.dataVencimento asc, men.matricula.aluno asc, men.matricula.turma.curso.nome ";

		Query query = entityManager.createQuery(generateHql(sb.toString(), condictions) + orderBy);
		
		query.setParameter("hoje", hoje );

		if(statusPagamento != null) {
			query.setParameter("statusPagamento", statusPagamento );
		}
		
		if (notEmpty(aluno)) {
			query.setParameter("aluno", aluno );	
		}
		return query.getResultList();
	}
	

	@Override
	public Integer getMesMenorMensalidadeMatricula(Matricula matricula) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select min(men.idMensalidade)  ");
		sb.append(" from Mensalidade men  ");
		sb.append(" where men.matricula = :matricula  ");
		
		Query query = entityManager.createQuery(sb.toString());
		query.setParameter("matricula", matricula );
		
		Long idMensalidade = (Long) query.getSingleResult();
		
		sb = new StringBuilder();
		sb.append(" select men.dataVencimento from Mensalidade men  ");
		sb.append( " where men.idMensalidade = :idMensalidade " );
		
		query = entityManager.createQuery(sb.toString());
		query.setParameter("idMensalidade", idMensalidade );
		Date data = (Date) query.getSingleResult();
		
		return (Integer) DateUtil.getMes(data);
	}

}