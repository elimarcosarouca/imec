package br.com.ss.saa.web.enumerated;

public enum DiaSemana {

	DOMINGO(1, "Domingo"),
	SEGUNDA(2, "Segunda-feira"),
	TERCA(3, "Terça-feira"),
	QUARTA(4, "Quarta-feira"),
	QUINTA(5, "Quinta-feira"),
	SEXTA(6, "Sexta-feira"),
	SABADO(7, "Sábado");

	private int id;
	private String descricao;

	private DiaSemana(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public static DiaSemana getEnum(int id) {
		for (DiaSemana sit : DiaSemana.values()) {
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