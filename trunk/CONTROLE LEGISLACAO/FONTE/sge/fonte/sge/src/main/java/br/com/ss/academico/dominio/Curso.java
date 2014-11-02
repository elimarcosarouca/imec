package br.com.ss.academico.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.ss.core.seguranca.dominio.AbstractEntity;

/**
 * The persistent class for the iansa_curso database table.
 */
@Entity
@Table(name = "acad_curso")
public class Curso extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 6622307844544139433L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idCurso;

	@Column(nullable = false, length = 50)
	private String nome;

	@Column(nullable = false)
	private double valor;

	@OneToMany(fetch = FetchType.LAZY, 
				cascade = { CascadeType.MERGE, CascadeType.REMOVE }, 
				mappedBy = "curso")
	private List<CursoDisciplina> cursoDisciplina = new ArrayList<CursoDisciplina>();
	

	@OneToMany(fetch = FetchType.LAZY, 
				cascade = { CascadeType.MERGE, CascadeType.REMOVE }, 
				mappedBy = "curso")
	private List<Turma> turmas = new ArrayList<Turma>();
	

	@Override
	public Long getId() {
		return this.idCurso;
	}

	public Long getIdCurso() {
		return this.idCurso;
	}

	public void setIdCurso(Long idCurso) {
		this.idCurso = idCurso;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public List<CursoDisciplina> getCursoDisciplina() {
		return cursoDisciplina;
	}

	public void setCursoDisciplina(List<CursoDisciplina> cursoDisciplina) {
		this.cursoDisciplina = cursoDisciplina;
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

}