package br.com.ss.model.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ss.academico.enumerated.Competencia;

/**
 * The persistent class for the iansa_curso database table.
 */
@Entity
@Table(name = "LEGIS_LEGISLACAO")
public class Legislacao extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 6622307844544139433L;

	@Id
	@GeneratedValue(generator = "SEQ_LEGIS_LEGISLACAO", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_LEGIS_LEGISLACAO", name = "SEQ_LEGIS_LEGISLACAO")
	@Column(name = "ID_LEGIS_LEGISLACAO")
	private Long id;

	@Column(nullable = false, length = 20)
	private String numero;

	@Column(nullable = false, length = 60)
	private String path_file;

	// TODO ENUMERATE
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 1)
	private Competencia competencia;

	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataInicio;

	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataAlerta;

	@Column(nullable = false, length = 200)
	private String descricao;

	public Legislacao() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPath_file() {
		return path_file;
	}

	public void setPath_file(String path_file) {
		this.path_file = path_file;
	}

	public Competencia getCompetencia() {
		return competencia;
	}

	public void setCompetencia(Competencia competencia) {
		this.competencia = competencia;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataAlerta() {
		return dataAlerta;
	}

	public void setDataAlerta(Date dataAlerta) {
		this.dataAlerta = dataAlerta;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}