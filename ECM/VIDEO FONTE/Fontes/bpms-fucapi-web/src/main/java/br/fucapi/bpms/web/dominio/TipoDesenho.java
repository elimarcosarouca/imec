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
@Table(name = "siem_tipo_desenho")
public class TipoDesenho implements Serializable {

	private static final long serialVersionUID = -1174678502477802010L;

	@Id
	@Column(name = "tipo_dese_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "tipo_dese_nome", nullable = false, length = 30)
	private String nome;

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
	
	public boolean equals(Object o) {
		if (((TipoDesenho)o).getId() == this.id)
			return true;
		else return false;
	}
	
	public static TipoDesenho fromJsonToTipoDesenho(String json) {
		return new JSONDeserializer<TipoDesenho>().use(null, TipoDesenho.class).deserialize(json);
	}
	
	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
}
