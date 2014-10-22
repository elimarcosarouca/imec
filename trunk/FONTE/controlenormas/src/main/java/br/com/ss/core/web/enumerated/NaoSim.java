package br.com.ss.core.web.enumerated;

public enum NaoSim {

	NAO(false, "Não"), SIM(true, "Sim");

	private boolean value;
	private String descricao;

	private NaoSim(boolean value, String descricao) {
		this.value = value;
		this.descricao = descricao;
	}

	public static NaoSim getEnum(boolean value) {
		return value ? SIM : NAO;
	}

	public boolean getValue() {
		return value;
	}

	public String getDescricao() {
		return this.descricao;
	}
	
}