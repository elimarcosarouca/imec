package br.com.ss.academico.repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ss.academico.dominio.Aluno;
import br.com.ss.core.seguranca.repositorio.RepositorioGenerico;

@Repository
public class AlunoRepositorioJPAImpl extends RepositorioGenerico implements AlunoRepositorioJPA {
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Aluno> findByEntity(Aluno aluno) {
		
		StringBuilder sb = new StringBuilder();
		List<String> condictions = new ArrayList<String>();
		
		sb.append(" select alu from Aluno alu ");
		if ( notEmpty(aluno) ) {
			if ( notEmpty(aluno.getNome())) {
				condictions.add(" lower( alu.nome ) like :nome ");
			}
			if ( notEmpty(aluno.getSexo()) && aluno.getSexo().getId() >= 0) {
				condictions.add(" alu.sexo = :sexo ");
			}
			if ( notEmpty(aluno.getResponsavel())) {
				condictions.add(" lower( alu.responsavel ) like :responsavel ");
			}
		}

		String orderBy = " order by alu.nome ";
		
		Query query = entityManager.createQuery(generateHql(sb.toString(), condictions) + orderBy);
		if ( notEmpty(aluno) ) {
			if ( notEmpty(aluno.getNome())) {
				query.setParameter("nome", "%" + aluno.getNome() + "%");
			}
			if ( notEmpty(aluno.getSexo()) && aluno.getSexo().getId() >= 0) {
				query.setParameter("sexo", aluno.getSexo());
			}
			if ( notEmpty(aluno.getResponsavel())) {
				query.setParameter("responsavel", aluno.getResponsavel() );
			}
		}
		
		return query.getResultList();
	}

}