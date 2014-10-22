package br.com.ss.controlenormas.enumerated;

public enum GrauParentesco {

	PAI(0, "Pai"), 
	MAE(1, "Mãe"), 
	AVO(2, "Avô (Avó)"), 
	TIO(3, "Tio (Tia)"), 
	IRMAO(4, "Irmão (Irmã)"),
	PADRASTO(5, "Padrasto (Madrasta)"), 
	OUTRO(6, "Outro");

	private int id;
	private String descricao;

	private GrauParentesco(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public static GrauParentesco getEnum(int id) {
		for (GrauParentesco sit : GrauParentesco.values()) {
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