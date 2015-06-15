package br.fucapi.bpms.activiti.enumerated;

public enum NomeVariavel {

	STATUS_PROCESSO("statusProcesso"), DATA_NOTIFICACAO("dataNotificacao"), DATA_VENCIMENTO(
			"dataVencimento");

	private String nome;

	NomeVariavel(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

}