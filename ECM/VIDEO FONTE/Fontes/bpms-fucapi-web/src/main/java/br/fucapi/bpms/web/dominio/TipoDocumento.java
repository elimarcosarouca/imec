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
@Table(name = "siem_tipo_documento")
public class TipoDocumento implements Serializable {

	private static final long serialVersionUID = -1174678502477802010L;

	@Id
	@Column(name = "tipo_docu_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "tipo_docu_nome", nullable = false, length = 30)
	private String nome;

	@Column(name = "tipo_docu_sigla", nullable = false, length = 4)
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
		if (((TipoDocumento)o).getId() == this.id)
			return true;
		else return false;
	}
	
	public static TipoDocumento fromJsonToTipoDocumento(String json) {
		return new JSONDeserializer<TipoDocumento>().use(null, TipoDocumento.class).deserialize(json);
	}
	
	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
}
