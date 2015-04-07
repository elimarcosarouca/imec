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
	private String statusProcesso;
	private String uuidPasta;

	private String protocolo;
	
	// TODO - checar regra com o Claudemir
	/* Atributo para ser utizado tanto no cancelamento de solicitacao 
	 * quanto na reprovacao */
	private String justificativaStatus;

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
		return getAno()+""+getSequencial();
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public String getStatusProcesso() {
		return statusProcesso;
	}

	public void setStatusProcesso(String statusProcesso) {
		this.statusProcesso = statusProcesso;
	}
	
	public String getUuidPasta() {
		return uuidPasta;
	}

	public void setUuidPasta(String uuidPasta) {
		this.uuidPasta = uuidPasta;
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
			
			System.out.println(var.getName()+": "+var.getValue().toString());

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

			} else if (var.getName().equals("loginSolicitante")
					&& var.getValue() != null) {
				this.setSolicitante(var.getValue().toString());

			} else if (var.getName().equals("emailSolicitante")
					&& var.getValue() != null) {
				this.setEmailSolicitante(var.getValue().toString());

			} else if (var.getName().equals("protocolo")
					&& var.getValue() != null) {
				this.setProtocolo(var.getValue().toString());
				
			} else if (var.getName().equals("statusProcesso")
					&& var.getValue() != null) {
				this.setStatusProcesso(var.getValue().toString());
			
			} else if (var.getName().equals("justificativaStatus")
					&& var.getValue() != null) {
				this.setJustificativaStatus(var.getValue().toString());
			}
		}
	}

	public Map<String, Object> converterVariaveis() {

		Map<String, Object> params = new java.util.HashMap<String, Object>();
		params.put("sequencial", getSequencial());
		params.put("ano", getAno());
		params.put("solicitante", getSolicitante());
		params.put("emailSolicitante", getEmailSolicitante());
		params.put("descricao", getDescricao());
		params.put("protocolo", getAno()+""+getSequencial());
		params.put("descricao", getDescricao());
		params.put("statusProcesso", getStatusProcesso());

		return params;
	}

	public String getJustificativaStatus() {
		return justificativaStatus;
	}

	public void setJustificativaStatus(String justificativaStatus) {
		this.justificativaStatus = justificativaStatus;
	}
}
