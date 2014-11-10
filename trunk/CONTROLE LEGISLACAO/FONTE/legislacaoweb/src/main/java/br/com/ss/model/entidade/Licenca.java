package br.com.ss.model.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the iansa_curso database table.
 */
@Entity
@Table(name = "LEGIS_LICENCA")
public class Licenca extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 6622307844544139433L;
	
	@Id
	@GeneratedValue(generator = "SEQ_LEGIS_LICENCA", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_LEGIS_LICENCA", name = "SEQ_LEGIS_LICENCA")
	@Column(name = "ID_LEGIS_LICENCA")
	private Long id;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataInicio;

	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataFim;

	public Licenca() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

}