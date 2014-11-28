package br.com.ss.enumerated;

public enum Competencia {

	SOLTEIRO("A", "AAAA"), CASADO("B", "BBBB");

	private String id;
	private String descricao;

	private Competencia(String id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public static Competencia getEnum(int id) {
		for (Competencia sit : Competencia.values()) {
			if (sit.getId().equals(id))
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
}