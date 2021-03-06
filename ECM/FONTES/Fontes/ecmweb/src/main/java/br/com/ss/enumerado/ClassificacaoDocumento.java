package br.com.ss.enumerado;

public enum ClassificacaoDocumento {

	SOLTEIRO("S", "Solteiro"), CASADO("C", "Casado"), DIVORCIADO("D",
			"Divorciado"), OUTROS("O", "Outros");

	private String id;
	private String descricao;

	private ClassificacaoDocumento(String id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public static ClassificacaoDocumento getEnum(int id) {
		for (ClassificacaoDocumento sit : ClassificacaoDocumento.values()) {
			if (sit.getId().equals(id))
				return sit;
		}
		return null;
	}

	public String getId() {
		return id;
	}

	public String getDescricao() {
		return this.descricao;
	}
}