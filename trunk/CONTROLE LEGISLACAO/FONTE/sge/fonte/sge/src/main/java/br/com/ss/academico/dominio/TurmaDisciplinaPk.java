package br.com.ss.academico.dominio;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 
 * @author altitdb
 */
@Embeddable
public class TurmaDisciplinaPk implements Serializable {

	private static final long serialVersionUID = -8682045998279798805L;

	@ManyToOne
	@JoinColumn(name = "idTurma")
	private Turma turma;

	@ManyToOne
	@JoinColumn(name = "idDisciplina")
	private Disciplina disciplina;

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		if (!(obj instanceof TurmaDisciplinaPk)) {
			return false;
		}

		final TurmaDisciplinaPk other = (TurmaDisciplinaPk) obj;
		if (this.turma != other.turma
				&& (this.turma == null || !this.turma.equals(other.turma))) {
			return false;
		}
		if (this.disciplina != other.disciplina
				&& (this.disciplina == null || !this.disciplina
						.equals(other.disciplina))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 97 * hash + (this.turma != null ? this.turma.hashCode() : 0);
		hash = 97 * hash
				+ (this.disciplina != null ? this.disciplina.hashCode() : 0);
		return hash;
	}

}
