package br.com.ss.academico.enumerated;

public enum Turno {

	MATUTINO(0, "Matutino"), VESPERTINO(1, "Vespertino"), INTEGRAL(2, "Integral");

	private int id;
	private String descricao;

	private Turno(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public static Turno getEnum(int id) {
		for (Turno sit : Turno.values()) {
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