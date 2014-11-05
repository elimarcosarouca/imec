package br.com.ss.saa.web.enumerated;

public enum Sexo {

	MASCULINO(0, "Masculino"), FEMININO(1, "Feminino");

	private int id;
	private String descricao;

	private Sexo(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public static Sexo getEnum(int id) {
		for (Sexo sit : Sexo.values()) {
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