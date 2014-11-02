package br.com.ss.academico.enumerated;

public enum EstadoCivil {

	SOLTEIRO(0, "Solteiro(a)"), 
	CASADO(1, "Casado(a)"), 
	DIVORCIADO(2, "Divorciado(a)"), 
	VIUVO(3, "Viúdo(a)"), 
	OUTROS(4, "Outros");

	private int id;
	private String descricao;

	private EstadoCivil(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public static EstadoCivil getEnum(int id) {
		for (EstadoCivil sit : EstadoCivil.values()) {
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