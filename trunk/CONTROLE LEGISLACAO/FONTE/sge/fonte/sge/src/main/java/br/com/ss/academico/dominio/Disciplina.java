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
 * 
 */
@Entity
@Table(name = "acad_disciplina")
public class Disciplina extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -7349356023588133306L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idDisciplina;

	@Column(nullable = false, length = 50)
	private String nome;

	@Column(length = 100)
	private String descricao;
	
	@Column(nullable=false)
	private int maximoFaltas;
	

	@OneToMany(fetch = FetchType.LAZY, 
				cascade = { CascadeType.MERGE, CascadeType.REMOVE }, 
				mappedBy = "disciplina")
	private List<CursoDisciplina> cursoDisciplina = new ArrayList<CursoDisciplina>();

	@OneToMany(fetch = FetchType.LAZY, 
				cascade = { CascadeType.MERGE, CascadeType.REMOVE }, 
				mappedBy = "turmaDisciplinaPk.disciplina")
	private List<TurmaDisciplina> turmaDisciplina = new ArrayList<TurmaDisciplina>();

	public Disciplina() {
	}

	@Override
	public Long getId() {
		return this.idDisciplina;
	}

	public Long getIdDisciplina() {
		return this.idDisciplina;
	}

	public void setIdDisciplina(Long idDisciplina) {
		this.idDisciplina = idDisciplina;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<CursoDisciplina> getCursoDisciplina() {
		return cursoDisciplina;
	}

	public void setCursoDisciplina(List<CursoDisciplina> cursoDisciplina) {
		this.cursoDisciplina = cursoDisciplina;
	}

	public List<TurmaDisciplina> getTurmaDisciplina() {
		return turmaDisciplina;
	}

	public void setTurmaDisciplina(List<TurmaDisciplina> turmaDisciplina) {
		this.turmaDisciplina = turmaDisciplina;
	}

	public int getMaximoFaltas() {
		return maximoFaltas;
	}

	public void setMaximoFaltas(int maximoFaltas) {
		this.maximoFaltas = maximoFaltas;
	}

}