package br.com.ss.academico.enumerated;

public enum StatusBoletim {

	LANCAMENTOS_PENDENTES(0, "Lancamentos Pendentes"), 
	APROVADO(1, "Aprovado"), 
	REPROVADO(2, "Reprovado");

	private int id;
	private String descricao;

	private StatusBoletim(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public static StatusBoletim getEnum(int id) {
		for (StatusBoletim sit : StatusBoletim.values()) {
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