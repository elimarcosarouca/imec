package br.com.saa.enumerated;

import java.util.Collection;
import java.util.Date;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

public enum StatusUsuario {

	ATIVA("A", "Ativa"), INATIVA("I", "Inativa");

	private String id;
	private String descricao;

	private StatusUsuario(String id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public static StatusUsuario getEnum(String id) {
		for (StatusUsuario sit : StatusUsuario.values()) {
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

	public static StatusUsuario fromJsonToObject(String json) {
		return new JSONDeserializer<StatusUsuario>().use(null,
				StatusUsuario.class).deserialize(json);
	}

	public String toJson() {
		return new JSONSerializer()
				.exclude("*.class")
				.transform(new DateTransformer("dd/MM/yyyy HH:mm:ss"),
						Date.class).serialize(this);
	}

	public static String toJsonArray(Collection<StatusUsuario> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}
}