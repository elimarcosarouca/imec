package br.fucapi.ads.modelo.enumerated;

public enum StatusProcesso {

	ATIVO("ATIVO"), CANCELADO("CANCELADO"), OBSOLETO("OBSOLETO"), PENDENTE(
			"PENDENTE"), REPROVADO("REPROVADO");

	private String status;

	StatusProcesso(String status) {
		this.status = status;

	}

	public String getStatus() {
		return status;
	}

}