package br.com.ss.academico.dominio;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import br.com.ss.core.seguranca.dominio.AbstractEntity;

/**
 * @author claudemirferreira
 * 
 */
@Entity
@Table(name = "acad_boletim")
public class BoletimOld extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -6438912749527248323L;

	private float nota1;

	private float nota2;

	private float nota3;

	private float nota4;

	private float nota5;

	private float nota6;

	private float nota7;

	private float nota8;

	private float media1;

	private float media2;

	private float media3;

	private float media4;

	private float mediaFinal;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idBoletim;

	@ManyToOne // (cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name = "id_disciplina", nullable = false)
	private Disciplina disciplina;

	@ManyToOne // (cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name = "id_matricula", nullable = false)
	private Matricula matricula;

	public BoletimOld() {
	}

	@Override
	public Long getId() {
		return idBoletim;
	}

	public Long getIdBoletim() {
		return idBoletim;
	}

	public void setIdBoletim(Long idBoletim) {
		this.idBoletim = idBoletim;
	}

	public float getNota1() {
		return nota1;
	}

	public void setNota1(float nota1) {
		this.nota1 = nota1;
	}

	public float getNota2() {
		return nota2;
	}

	public void setNota2(float nota2) {
		this.nota2 = nota2;
	}

	public float getNota3() {
		return nota3;
	}

	public void setNota3(float nota3) {
		this.nota3 = nota3;
	}

	public float getNota4() {
		return nota4;
	}

	public void setNota4(float nota4) {
		this.nota4 = nota4;
	}

	public float getNota5() {
		return nota5;
	}

	public void setNota5(float nota5) {
		this.nota5 = nota5;
	}

	public float getNota6() {
		return nota6;
	}

	public void setNota6(float nota6) {
		this.nota6 = nota6;
	}

	public float getNota7() {
		return nota7;
	}

	public void setNota7(float nota7) {
		this.nota7 = nota7;
	}

	public float getNota8() {
		return nota8;
	}

	public void setNota8(float nota8) {
		this.nota8 = nota8;
	}

	public float getMedia1() {
		return media1;
	}

	public void setMedia1(float media1) {
		this.media1 = media1;
	}

	public float getMedia2() {
		return media2;
	}

	public void setMedia2(float media2) {
		this.media2 = media2;
	}

	public float getMedia3() {
		return media3;
	}

	public void setMedia3(float media3) {
		this.media3 = media3;
	}

	public float getMedia4() {
		return media4;
	}

	public void setMedia4(float media4) {
		this.media4 = media4;
	}

	public float getMediaFinal() {
		return mediaFinal;
	}

	public void setMediaFinal(float mediaFinal) {
		this.mediaFinal = mediaFinal;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}
	
	public void atualizarMedia(){
		this.setMedia1((this.nota1 + this.nota2) / 2);
		this.setMedia2((this.nota3 + this.nota4) / 2);
		this.setMedia3((this.nota5 + this.nota6) / 2);
		this.setMedia4((this.nota7 + this.nota8) / 2);
	}

}