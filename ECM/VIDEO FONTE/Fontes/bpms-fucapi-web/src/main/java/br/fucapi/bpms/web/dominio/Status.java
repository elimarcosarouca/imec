package br.fucapi.bpms.web.dominio;

public enum Status {

    PENDENTE("PENDENTE"), CONCLUIDO("CONCLUIDO"), CANCELADO("CANCELADO");
    
    private final String nome;

    Status(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

}
