package br.com.ss.controlenormas.enumerated;

public enum StatusPagamento {

	PENDENTE(0, "Pendente"), PAGO(1, "Pago"), CANCELADO(2, "Cancelado");

	private int id;
	private String descricao;

	private StatusPagamento(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public static StatusPagamento getEnum(int id) {
		for (StatusPagamento sit : StatusPagamento.values()) {
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