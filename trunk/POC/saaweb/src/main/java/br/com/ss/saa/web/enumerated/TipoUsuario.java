package br.com.ss.saa.web.enumerated;

public enum TipoUsuario {

	MASTER(0, "Master"), 
	ADMINISTRADOR(1, "Administrador"), 
	SGE_SECRETARIA(2, "Secretaria"),
	SGE_PROFESSOR(3, "Professor");

	private int id;
	private String descricao;

	private TipoUsuario(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public static TipoUsuario getEnum(int id) {
		for (TipoUsuario tp : TipoUsuario.values()) {
			if (tp.id == id)
				return tp;
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