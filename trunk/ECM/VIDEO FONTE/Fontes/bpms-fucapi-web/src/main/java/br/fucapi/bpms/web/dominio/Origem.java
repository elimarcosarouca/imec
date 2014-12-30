package br.fucapi.bpms.web.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Table(name = "siem_origem")
public class Origem implements Serializable {
	
	private static final long serialVersionUID = -5911428824459001493L;

	@Id
	@Column(name = "orig_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "orig_nome", nullable = false, length = 60)
	private String nome;

	@Column(name = "orig_sigla", nullable = false, length = 4)
	private String sigla;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
		
	public boolean equals(Object o) {
		if (((Origem)o).getId() == this.id)
			return true;
		else return false;
	}
	
	public static Origem fromJsonToOrigem(String json) {
		return new JSONDeserializer<Origem>().use(null, Origem.class).deserialize(json);
	}
	
	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
}
