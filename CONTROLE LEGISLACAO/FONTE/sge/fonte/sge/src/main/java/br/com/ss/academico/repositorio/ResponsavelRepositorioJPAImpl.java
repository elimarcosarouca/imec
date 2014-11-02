package br.com.ss.academico.repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ss.academico.dominio.Responsavel;
import br.com.ss.core.seguranca.repositorio.RepositorioGenerico;

@Repository
@SuppressWarnings("unchecked")
public class ResponsavelRepositorioJPAImpl extends RepositorioGenerico implements ResponsavelRepositorioJPA {

	@Override
	public List<Responsavel> findByNomeLike(String nome) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(" select res from Responsavel res ");
		sb.append(" where lower( res.nome ) like :nome ");
		
		Query query = entityManager.createQuery(sb.toString());
		query.setParameter("nome", "%" + nome.trim().toLowerCase() + "%" );
		
		return query.getResultList();
	}
	

	@Override
	public Responsavel findByNome(String nome) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(" select res from Responsavel res ");
		sb.append(" where lower( res.nome ) = :nome ");
		
		Query query = entityManager.createQuery(sb.toString());
		query.setParameter("nome", nome.trim().toLowerCase());
		
		return (Responsavel) query.getSingleResult();
	}


	@Override
	public List<Responsavel> pesquisar(Responsavel entity, String nomeAluno) {
		List<String> condictions = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		sb.append(" select distinct res from Responsavel res ");
		if (notEmpty(nomeAluno)) {
			sb.append(" join res.alunos aluno ");
			condictions.add(" lower( aluno.nome ) like :nomeAluno ");
		}
		if (notEmpty(entity.getNome())) {
			condictions.add(" lower( res.nome ) like :nome ");
		}
		String orderBy = " order by res.nome ";
		Query query = entityManager.createQuery(generateHql(sb.toString(), condictions) + orderBy);
		
		if ( notEmpty(nomeAluno)) {
			query.setParameter("nomeAluno", "%" + nomeAluno.trim().toLowerCase() + "%");
		}
		if (notEmpty(entity.getNome())) {
			query.setParameter("nome", "%" + entity.getNome().trim().toLowerCase() + "%");
		}
		return query.getResultList();
	}

}