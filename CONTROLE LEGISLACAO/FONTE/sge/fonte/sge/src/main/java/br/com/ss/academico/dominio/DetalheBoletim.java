package br.com.ss.academico.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.ss.academico.enumerated.StatusBoletim;
import br.com.ss.core.seguranca.dominio.AbstractEntity;
import br.com.ss.core.web.enumerated.NumeroUtil;

/**
 * @author Robson
 */
@Entity
@Table(name = "acad_detalhe_boletim")
public class DetalheBoletim extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -6438912749527248323L;

	@Column
	private Float nota1;

	@Column
	private Float nota2;

	@Column
	private Float nota3;

	@Column
	private Float nota4;

	@Column
	private Float nota5;

	@Column
	private Float nota6;

	@Column
	private Float nota7;

	@Column
	private Float nota8;
	

	@Column
	private Float media1;

	@Column
	private Float media2;

	@Column
	private Float media3;

	@Column
	private Float media4;

	@Column
	private Float mediaGeral;
	
	
	@Column
	private Integer faltasBimestre1;

	@Column
	private Integer faltasBimestre2;

	@Column
	private Integer faltasBimestre3;

	@Column
	private Integer faltasBimestre4;

	@Column
	private Integer totalFaltas = 0;
	
	
	@Column
	private boolean recuperacao;

	@Column
	private Float notaRecuperacao;
	

	@Column
	private Float mediaFinal;
	
	@Enumerated
	@Column
	private StatusBoletim statusDisciplina = StatusBoletim.LANCAMENTOS_PENDENTES;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idDetalheBoletim;

	@ManyToOne
	@JoinColumn(name = "id_disciplina", nullable = false)
	private Disciplina disciplina;

	@ManyToOne
	@JoinColumn(name = "id_boletim", nullable = false)
	private Boletim boletim;

	public DetalheBoletim() { }

	
	public void calcularMedias() {
		
		this.setMedia1((this.nota1 + this.nota2) / 2);
		this.setMedia2((this.nota3 + this.nota4) / 2);
		this.setMedia3((this.nota5 + this.nota6) / 2);
		this.setMedia4((this.nota7 + this.nota8) / 2);
		
		calcularMediaGeral();
	}

	
	private void calcularMediaGeral() {
		mediaGeral = ( getMedia1() + getMedia2() + getMedia3() + getMedia4() ) / NumeroUtil.QUATRO;
	}
	

	public void calcularMediaFinal( final Float mediaEscolar) {
		
		if ( recuperacao ) {
			mediaFinal = ( mediaGeral + notaRecuperacao ) / NumeroUtil.DOIS;
		} else {
			mediaFinal = mediaGeral;
		}
		
		atribuirStatusDisciplina(mediaEscolar);
		
	}


	/** atualiza o status se o aluno esta aprovado ou reprovado na disciplina. */
	private void atribuirStatusDisciplina(final Float mediaEscolar) {
		statusDisciplina = StatusBoletim.APROVADO;
		// valida as medias
		if (mediaFinal < mediaEscolar
				// valida as faltas
				|| ( totalFaltas != null && totalFaltas > disciplina.getMaximoFaltas() ) ) {
			statusDisciplina = StatusBoletim.REPROVADO;
		}
	}
	
	
	/* -------- Gets/Sets ---------------- */
	@Override
	public Long getId() {
		return idDetalheBoletim;
	}

	public Long getDetalheIdBoletim() {
		return idDetalheBoletim;
	}

	public void setIdDetalheBoletim(Long idDetalheBoletim) {
		this.idDetalheBoletim = idDetalheBoletim;
	}

	public Float getNota1() {
		return nota1;
	}

	public void setNota1(Float nota1) {
		this.nota1 = nota1;
	}

	public Float getNota2() {
		return nota2;
	}

	public void setNota2(Float nota2) {
		this.nota2 = nota2;
	}

	public Float getNota3() {
		return nota3;
	}

	public void setNota3(Float nota3) {
		this.nota3 = nota3;
	}

	public Float getNota4() {
		return nota4;
	}

	public void setNota4(Float nota4) {
		this.nota4 = nota4;
	}

	public Float getNota5() {
		return nota5;
	}

	public void setNota5(Float nota5) {
		this.nota5 = nota5;
	}

	public Float getNota6() {
		return nota6;
	}

	public void setNota6(Float nota6) {
		this.nota6 = nota6;
	}

	public Float getNota7() {
		return nota7;
	}

	public void setNota7(Float nota7) {
		this.nota7 = nota7;
	}

	public Float getNota8() {
		return nota8;
	}

	public void setNota8(Float nota8) {
		this.nota8 = nota8;
	}

	public Float getMedia1() {
		return media1;
	}

	public void setMedia1(Float media1) {
		this.media1 = media1;
	}

	public Float getMedia2() {
		return media2;
	}

	public void setMedia2(Float media2) {
		this.media2 = media2;
	}

	public Float getMedia3() {
		return media3;
	}

	public void setMedia3(Float media3) {
		this.media3 = media3;
	}

	public Float getMedia4() {
		return media4;
	}

	public void setMedia4(Float media4) {
		this.media4 = media4;
	}

	public Float getMediaFinal() {
		return mediaFinal;
	}

	public void setMediaFinal(Float mediaFinal) {
		this.mediaFinal = mediaFinal;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public Float getNotaRecuperacao() {
		return notaRecuperacao;
	}

	public void setNotaRecuperacao(Float notaRecuperacao) {
		this.notaRecuperacao = notaRecuperacao;
	}

	public StatusBoletim getStatusDisciplina() {
		return statusDisciplina;
	}

	public void setStatusDisciplina(StatusBoletim statusDisciplina) {
		this.statusDisciplina = statusDisciplina;
	}

	public Boletim getBoletim() {
		return boletim;
	}

	public void setBoletim(Boletim boletim) {
		this.boletim = boletim;
	}

	public Float getMediaGeral() {
		return mediaGeral;
	}

	public boolean isRecuperacao() {
		return recuperacao;
	}

	public void setRecuperacao(boolean recuperacao) {
		this.recuperacao = recuperacao;
		if ( !recuperacao ) {
			setNotaRecuperacao(0f);
		}
	}


	public Integer getFaltasBimestre1() {
		return faltasBimestre1;
	}


	public void setFaltasBimestre1(Integer faltasBimestre1) {
		this.faltasBimestre1 = faltasBimestre1;
	}


	public Integer getFaltasBimestre2() {
		return faltasBimestre2;
	}


	public void setFaltasBimestre2(Integer faltasBimestre2) {
		this.faltasBimestre2 = faltasBimestre2;
	}


	public Integer getFaltasBimestre3() {
		return faltasBimestre3;
	}


	public void setFaltasBimestre3(Integer faltasBimestre3) {
		this.faltasBimestre3 = faltasBimestre3;
	}


	public Integer getFaltasBimestre4() {
		return faltasBimestre4;
	}


	public void setFaltasBimestre4(Integer faltasBimestre4) {
		this.faltasBimestre4 = faltasBimestre4;
	}


	public Integer getTotalFaltas() {
		return totalFaltas;
	}


	public void setTotalFaltas(Integer totalFaltas) {
		this.totalFaltas = totalFaltas;
	}


	public Long getIdDetalheBoletim() {
		return idDetalheBoletim;
	}


	public void setMediaGeral(Float mediaGeral) {
		this.mediaGeral = mediaGeral;
	}

}