package br.com.ss.academico.dominio;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.ss.core.web.utils.IntegerConstantUtils;

@Embeddable
public class CursoDisciplinaId implements Serializable {

	private static final long serialVersionUID = -8682045998279798805L;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "id_curso" )
	private Curso curso;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "id_disciplina" )
	private Disciplina disciplina;
    
    
    public CursoDisciplinaId() { }

    
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


    @Override
    public boolean equals( Object other ) {
        if ( ( this == other ) ) {
            return true;
        }
        if ( ( other == null ) ) {
            return false;
        }
        if ( !( other instanceof CursoDisciplinaId ) ) {
            return false;
        }
        CursoDisciplinaId castOther = (CursoDisciplinaId) other;

        return ( this.getCurso().getIdCurso() == castOther.getCurso().getIdCurso() )
                && ( this.disciplina.getIdDisciplina() == castOther.getDisciplina().getIdDisciplina() );
    }

    @Override
    public int hashCode() {
        long result = IntegerConstantUtils.DEZESSETE;

        result = IntegerConstantUtils.TRINTA_SETE * result + this.getCurso().getIdCurso();
        result = IntegerConstantUtils.TRINTA_SETE * result + this.getDisciplina().getIdDisciplina();
        return (int) result;
    }

}
