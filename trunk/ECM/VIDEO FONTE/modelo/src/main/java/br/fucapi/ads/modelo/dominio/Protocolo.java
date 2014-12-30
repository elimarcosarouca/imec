package br.fucapi.ads.modelo.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fuca_protocolo")
public class Protocolo implements Serializable {

	private static final long serialVersionUID = 1072596548624389200L;

	@Id
	@Column(name = "prot_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "prot_sequencial", nullable = false, length = 10)
	private int sequencial;

	@Column(name = "prot_ano", nullable = false, length = 4)
	private int ano;
	
	@Column(name = "prot_tipo_protocolo", nullable = false, length = 30)
	private String tipoProtocolo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSequencial() {
		return sequencial;
	}

	public void setSequencial(int sequencial) {
		this.sequencial = sequencial;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public Protocolo(int id, int sequencial, int ano) {
		this.id = id;
		this.sequencial = sequencial;
		this.ano = ano;
	}

	public Protocolo() {
		super();
	}

	public String getTipoProtocolo() {
		return tipoProtocolo;
	}

	public void setTipoProtocolo(String tipoProtocolo) {
		this.tipoProtocolo = tipoProtocolo;
	}

	@Override
	public String toString() {
		return this.sequencial + "/" +this.ano;
	}

}
