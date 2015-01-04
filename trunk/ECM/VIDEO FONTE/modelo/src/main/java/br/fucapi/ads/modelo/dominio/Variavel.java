package br.fucapi.ads.modelo.dominio;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import br.fucapi.bpms.activiti.dominio.Variaveis;

public class Variavel implements Serializable {

	private static final long serialVersionUID = 291560041382271809L;

	private Integer sequencial;
	private Integer ano;
	private String descricao;
	private String solicitante;
	private String emailSolicitante;

	private String protocolo;

	public Variavel() {

	}

	public Integer getSequencial() {
		return sequencial;
	}

	public void setSequencial(Integer sequencial) {
		this.sequencial = sequencial;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public String getEmailSolicitante() {
		return emailSolicitante;
	}

	public void setEmailSolicitante(String emailSolicitante) {
		this.emailSolicitante = emailSolicitante;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	/**
	 * Metodo responsavel por converter lista de variaveis (Map) em um objeto
	 * VariaveisProcesso
	 * 
	 * @param variaveis
	 */
	@Autowired(required = false)
	public void converterListaVariaveisParaVariaveisProcesso(
			List<Variaveis> variaveisLista) {

		for (Variaveis var : variaveisLista) {

			if (var.getName().equals("sequencial") && var.getValue() != null) {
				this.setSequencial(Integer.valueOf(var.getValue() != null ? var
						.getValue().toString() : "0"));

			} else if (var.getName().equals("ano") && var.getValue() != null) {
				try {
					this.setAno(Integer.valueOf(var.getValue().toString()));
				} catch (NumberFormatException e) {
				}

			} else if (var.getName().equals("descricao")
					&& var.getValue() != null) {
				this.setDescricao(var.getValue().toString());

			} else if (var.getName().equals("solicitante")
					&& var.getValue() != null) {
				this.setSolicitante(var.getValue().toString());

			} else if (var.getName().equals("emailSolicitante")
					&& var.getValue() != null) {
				this.setEmailSolicitante(var.getValue().toString());

			} else if (var.getName().equals("protocolo")
					&& var.getValue() != null) {
				this.setProtocolo(var.getValue().toString());
			}
		}
	}

	public Map<String, Object> converterVariaveisProcessoParaMapaVariaveis() {

		Map<String, Object> params = new java.util.HashMap<String, Object>();
		params.put("sequencial", getSequencial());
		params.put("ano", getAno());
		params.put("solicitante", getSolicitante());
		params.put("emailSolicitante", getEmailSolicitante());
		params.put("descricao", getDescricao());
		params.put("protocolo", getSequencial() + "/" + getAno());
		params.put("descricao", getDescricao());

		return params;
	}
	/*
	 * public String ObjectToJson(String processoDefinitionKey, Protocolo
	 * protocolo) {
	 * 
	 * processoDefinitionKey = ("TREINAMENTO");
	 * 
	 * String json = "\"processDefinitionKey\":\"" + processoDefinitionKey +
	 * "\",\"businessKey\":\"" + protocolo + "\"," + "\"variables\":[{" +
	 * "\"sequencial\":\"" + this.getSequencial() + "\",\"ano\":\"" +
	 * this.getAno() + "\",\"gruposNotificar\":\"" + this.getGruposNotificar() +
	 * "\",\"solicitante\":\"" + this.getSolicitante() +
	 * "\",\"emailSolicitante\":\"" + this.getEmailSolicitante() +
	 * "\",\"sistema\":\"" + this.getSistema() + "\",\"descricao:" +
	 * this.getDescricao() + "\",\"protocolo:" + this.getProtocolo() + "\"}]";
	 * 
	 * return json; }
	 */
}
