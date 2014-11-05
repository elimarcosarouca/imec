package br.com.ss.saa.web.enumerated;

public enum StatusUsuario {

	ATIVO(0, "Ativo"), 
	INATIVO(1, "Inativo"), 
	BLOQUEADO(2, "Bloqueado");

	private int id;
	private String descricao;

	private StatusUsuario(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public static StatusUsuario getEnum(int id) {
		for (StatusUsuario sit : StatusUsuario.values()) {
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