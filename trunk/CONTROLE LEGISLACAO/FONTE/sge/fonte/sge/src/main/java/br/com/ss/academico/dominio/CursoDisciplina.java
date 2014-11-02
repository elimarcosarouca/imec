package br.com.ss.academico.dominio;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author altitdb
 */
@Entity
@Table( name = "acad_curso_disciplina" )
public class CursoDisciplina implements Serializable {

	private static final long serialVersionUID = -1220797610390530939L;

	@EmbeddedId
    @AttributeOverrides( {
        @AttributeOverride( name = "idCurso", column = @Column( name = "id_curso", nullable = false ) ),
        @AttributeOverride( name = "idDisciplina", column = @Column( name = "id_disciplina", nullable = false ) ) } )
	private CursoDisciplinaId id = new CursoDisciplinaId();

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "id_curso", insertable = false, updatable = false )
	private Curso curso;

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "id_disciplina", insertable = false, updatable = false )
	private Disciplina disciplina;

	@Temporal(TemporalType.TIMESTAMP)
	private Date data;		// FIXME #Peninha, Qual a finalidade desse campo??

	
	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public CursoDisciplinaId getId() {
		return id;
	}

	public void setId(CursoDisciplinaId id) {
		this.id = id;
	}

}