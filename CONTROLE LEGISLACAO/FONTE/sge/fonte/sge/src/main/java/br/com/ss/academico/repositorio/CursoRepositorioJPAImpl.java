package br.com.ss.academico.repositorio;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ss.academico.dominio.Curso;
import br.com.ss.core.seguranca.repositorio.RepositorioGenerico;

@Repository
public class CursoRepositorioJPAImpl extends RepositorioGenerico implements CursoRepositorioJPA {

	@SuppressWarnings("unchecked")
	@Override
	public List<Curso> findByNomeLike(String nome) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(" select cur from Curso cur ");
		sb.append(" where lower( cur.nome ) like :nome ");
		
		Query query = entityManager.createQuery(sb.toString());
		query.setParameter("nome", "%" + nome.trim().toLowerCase() + "%" );
		
		return query.getResultList();
	}

}