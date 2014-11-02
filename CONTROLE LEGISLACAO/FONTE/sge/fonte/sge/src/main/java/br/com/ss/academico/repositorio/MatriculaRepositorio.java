package br.com.ss.academico.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ss.academico.dominio.Aluno;
import br.com.ss.academico.dominio.Matricula;
import br.com.ss.academico.dominio.Turma;
import br.com.ss.academico.enumerated.StatusMatricula;

@Repository
public interface MatriculaRepositorio extends JpaRepository<Matricula, Long> {

	@Query("select mat from Matricula mat where mat.aluno = :aluno")
	public List<Matricula> findByAluno( @Param("aluno") Aluno aluno );
	
	@Query( " select count(mat.idMatricula) "
			+ "from Matricula mat "
			+ "join mat.turma tur "
			+ "where mat.turma = :turma "
			+ "and mat.status = :status "
			+ "and tur.ano = :ano " )
	public Long countMatriculas( @Param("turma") Turma turma, 
									@Param("status") StatusMatricula status,
									@Param("ano") Integer ano );
	
	/**
	 * Faz o fetch em Mensalidades.
	 * @param matricula
	 * @return
	 */
	@Query("select mat from Matricula mat left join fetch mat.mensalidades where mat = :matricula")
	public Matricula loadMatriculaMensalidades(@Param("matricula") Matricula matricula);
	
	
}