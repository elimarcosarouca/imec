package br.com.ss.saa.web.enumerated;

public enum Meses {

	JANEIRO(1, "Janeiro"),
	FEVEREIRO(2, "Fevereiro"),
	MARÇO(3, "Março"),
	ABRIL(4, "Abril"),
	MAIO(5, "Maio"),
	JUNHO(6, "Junho"),
	JULHO(7, "Julho"),
	AGOSTO(8, "Agosto"),
	SETEMBRO(9, "Setembro"),
	OUTUBRO(10, "Outubro"),
	NOVEMBRO(11, "Novembro"),
	DEZEMBRO(12, "Dezembro");

	private int id;
	private String descricao;

	private Meses(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public static Meses getEnum(int id) {
		for (Meses sit : Meses.values()) {
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