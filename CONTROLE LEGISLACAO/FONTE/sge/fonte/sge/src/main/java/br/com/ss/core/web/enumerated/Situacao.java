package br.com.ss.core.web.enumerated;

public enum Situacao {

	ATIVO(0, "Ativo"), INATIVO(1, "Inativo");

	private int id;
	private String descricao;

	private Situacao(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public static Situacao getEnum(int id) {
		for (Situacao sit : Situacao.values()) {
			if (sit.id == id)
				return sit;
		}
		return null;
	}

	public int getId() {
		return id;
	}

	public String getDescricao() {
		return this.descricao;
	}
}