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
@Table(name = "siem_familia_produto")
public class FamiliaProduto implements Serializable {

	private static final long serialVersionUID = 3147667020158234172L;

	@Id
	@Column(name = "fami_prod_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "fami_prod_nome", nullable = false, length = 60)
	private String nome;

	public FamiliaProduto() {
		super();
	}

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
		if (((FamiliaProduto) o).getId() == this.id)
			return true;
		else
			return false;
	}

	public static FamiliaProduto fromJsonToFamiliaProduto(String json) {
		return new JSONDeserializer<FamiliaProduto>().use(null,
				FamiliaProduto.class).deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}
}
