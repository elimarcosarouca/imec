package br.com.ss.controlenormas.enumerated;

public enum TipoPesquisaData {

	VECIMENTO(0, "Data de vencimento"), PAGAMENTO(1, "Data de pagamento");

	private int id;
	private String descricao;

	private TipoPesquisaData(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public static TipoPesquisaData getEnum(int id) {
		for (TipoPesquisaData sit : TipoPesquisaData.values()) {
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