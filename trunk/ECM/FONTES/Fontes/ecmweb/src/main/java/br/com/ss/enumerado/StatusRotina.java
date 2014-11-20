package br.com.ss.enumerado;

import java.util.Collection;
import java.util.Date;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

public enum StatusRotina {

	ATIVA("A", "Ativa"), BLOQUEADO("B", "Bloqueado"), SEM_LICENCA("S",
			"Sem licenca");

	private String id;
	private String descricao;

	private StatusRotina(String id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public static StatusRotina getEnum(String id) {
		for (StatusRotina sit : StatusRotina.values()) {
			if (sit.id == id)
				return sit;
		}
		return null;
	}

	public String getId() {
		return id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public static StatusRotina fromJsonToObject(String json) {
		return new JSONDeserializer<StatusRotina>().use(null,
				StatusRotina.class).deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer()
				.exclude("*.class")
				.transform(new DateTransformer("dd/MM/yyyy HH:mm:ss"),
						Date.class).serialize(this);
	}

	public static String toJsonArray(Collection<StatusRotina> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}
}