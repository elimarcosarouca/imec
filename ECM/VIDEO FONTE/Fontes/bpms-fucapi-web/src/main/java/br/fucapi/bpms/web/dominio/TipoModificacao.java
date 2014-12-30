package br.fucapi.bpms.web.dominio;

public enum TipoModificacao {

    NOVO("NOVO"), ALTERACAO("ALTERACAO"), CANCELAMENTO("CANCELAMENTO"), DISTRIBUICAO("DISTRIBUICAO");

    private final String nome;

    TipoModificacao(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }
}
