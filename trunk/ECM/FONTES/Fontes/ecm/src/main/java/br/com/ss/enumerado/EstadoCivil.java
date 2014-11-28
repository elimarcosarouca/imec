package br.com.ss.enumerado;

public enum EstadoCivil {

	SOLTEIRO("S", "Solteiro"), CASADO("C", "Casado"), DIVORCIADO("D",
			"Divorciado"), OUTROS("O", "Outros");

	private String id;
	private String descricao;

	private EstadoCivil(String id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public static EstadoCivil getEnum(int id) {
		for (EstadoCivil sit : EstadoCivil.values()) {
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