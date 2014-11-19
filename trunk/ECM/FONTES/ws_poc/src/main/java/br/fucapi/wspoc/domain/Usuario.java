package br.fucapi.wspoc.domain;

import org.springframework.roo.addon.json.RooJson;

import flexjson.JSONSerializer;

@RooJson
public class Usuario {
	
	
	private String nome;

	private String email;

	private String login;

	private String senha;

	private String funcao;

	private String foto;
	
	
	
	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getLogin() {
		return login;
	}



	public void setLogin(String login) {
		this.login = login;
	}



	public String getSenha() {
		return senha;
	}



	public void setSenha(String senha) {
		this.senha = senha;
	}



	public String getFuncao() {
		return funcao;
	}



	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}



	public String getFoto() {
		return foto;
	}



	public void setFoto(String foto) {
		this.foto = foto;
	}



	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
}
