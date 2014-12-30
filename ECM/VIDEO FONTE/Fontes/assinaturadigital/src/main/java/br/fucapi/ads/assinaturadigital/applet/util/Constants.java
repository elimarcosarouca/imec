package br.fucapi.ads.assinaturadigital.applet.util;

public enum Constants {
	
	PARAM_ID_MANIF_SUF("PARAM_ID_MANIF_SUF"),
	PARAM_DATA_GERACAO("PARAM_DATA_GERACAO"),
	PARAM_COOKIES("PARAM_COOKIES");
	
	//Atributos
	private String nome;

	private Constants(String nome){
		this.nome = nome;		
	}

	public String toString() {
		return nome;
	}

}