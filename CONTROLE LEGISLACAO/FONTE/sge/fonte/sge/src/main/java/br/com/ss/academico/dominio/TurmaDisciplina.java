package br.com.ss.academico.dominio;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient; 

/**
 * 
 * @author altitdb
 */
@Entity
@AssociationOverrides({
		@AssociationOverride(name = "turmaDisciplinaPk.turma", joinColumns = @JoinColumn(name = "idTurma")),
		@AssociationOverride(name = "turmaDisciplinaPk.disciplina", joinColumns = @JoinColumn(name = "idDisciplina")) })
@Table( name = "acad_turma_disciplina" )
public class TurmaDisciplina implements Serializable {

	private static final long serialVersionUID = -1220797610390530939L;

	@EmbeddedId
	private TurmaDisciplinaPk turmaDisciplinaPk = new TurmaDisciplinaPk();

	@Transient
	private Turma turma;

	@Transient
	private Disciplina disciplina;

	@Temporal(TemporalType.TIMESTAMP)
	private Date data;

	
	public Disciplina getDisciplina() {
		return turmaDisciplinaPk.getDisciplina();
	}

	public void setDisciplina(Disciplina disciplina) {
		turmaDisciplinaPk.setDisciplina(disciplina);
	}

	public Turma getTurma() {
		return turmaDisciplinaPk.getTurma();
	}

	public void setTurma(Turma turma) {
		turmaDisciplinaPk.setTurma(turma);
	}

	public TurmaDisciplinaPk getTurmaDisciplinaPk() {
		return turmaDisciplinaPk;
	}

	public void setTurmaDisciplinaPk(TurmaDisciplinaPk turmaDisciplinaPk) {
		this.turmaDisciplinaPk = turmaDisciplinaPk;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public boolean equals(Object obj) {
		return turmaDisciplinaPk.equals( ( ( TurmaDisciplina ) obj ).getTurmaDisciplinaPk() );
	}

	@Override
	public int hashCode() {
		return turmaDisciplinaPk.hashCode();
	}
	
}