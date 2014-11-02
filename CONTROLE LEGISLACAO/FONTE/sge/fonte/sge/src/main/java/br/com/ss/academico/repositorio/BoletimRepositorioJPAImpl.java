package br.com.ss.academico.repositorio;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ss.academico.dominio.Boletim;
import br.com.ss.academico.dominio.Turma;
import br.com.ss.academico.enumerated.StatusMatricula;
import br.com.ss.core.seguranca.repositorio.RepositorioGenerico;

@SuppressWarnings("unchecked")
@Repository
public class BoletimRepositorioJPAImpl extends RepositorioGenerico implements BoletimRepositorioJPA {

	@Override
	public List<Boletim> listaBoletimPorTurma(Turma turma) {
		String hql = " select bol from Boletim bol "
				+ "join bol.matricula mat "
				+ "join mat.turma tur "
				+ "where mat.turma = :turma "
				+ "and mat.status = :ativa ";
		Query query = entityManager.createQuery(hql);
		query.setParameter("turma", turma);
		query.setParameter("ativa", StatusMatricula.ATIVA);
		return query.getResultList();
	}

	
}
