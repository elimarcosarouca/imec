package br.fucapi.bpms.web.dominio;

public enum Situacao {

	VALIDO("VALIDO"), INVALIDO("INVALIDO"), TRANSITORIO("TRANSITORIO");
	 
    private final String nome;

    Situacao(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }
}
