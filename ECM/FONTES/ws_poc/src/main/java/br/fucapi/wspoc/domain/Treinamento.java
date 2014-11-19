package br.fucapi.wspoc.domain;

import java.security.acl.Owner;
import java.util.Collection;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class Treinamento extends Tarefa {

	private String nome;

	private String descricao;

	private Float custoEstimado;

	private Usuario colaborador;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Float getCustoEstimado() {
		return custoEstimado;
	}

	public void setCustoEstimado(Float custoEstimado) {
		this.custoEstimado = custoEstimado;
	}

	public Usuario getColaborador() {
		return colaborador;
	}

	public void setColaborador(Usuario colaborador) {
		this.colaborador = colaborador;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static String toJsonArray(Collection<Treinamento> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

}
