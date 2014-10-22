package br.com.ss.controlenormas.dominio;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ss.core.seguranca.dominio.AbstractEntity;

/**
 * The persistent class for the iansa_curso database table.
 */
@Entity
@Table(name = "saa_licenca")
public class Licenca extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 6622307844544139433L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idLicenca;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataInicio;
	
	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataFim;

	public Licenca() {
	}

	public Long getId() {
		return this.idLicenca;
	}

	public Long getIdLicenca() {
		return this.idLicenca;
	}

	public Long getIdNorma() {
		return idNorma;
	}

	public void setIdNorma(Long idNorma) {
		this.idNorma = idNorma;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public Date getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(Date dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

}