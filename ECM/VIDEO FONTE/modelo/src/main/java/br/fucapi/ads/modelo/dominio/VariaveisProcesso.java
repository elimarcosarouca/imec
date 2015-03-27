package br.fucapi.ads.modelo.dominio;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import br.fucapi.bpms.activiti.dominio.Variaveis;

public class VariaveisProcesso {

	private Integer sequencial;
	private Integer ano;
	private String descricao;
	private List<String> gruposNotificar;
	private String solicitante;
	private String emailSolicitante;
	private String sistema;
	private String tipoSolicitacao;
	private Boolean status;
	private String situacao;
	private boolean digitalizar;
	private boolean aprovar;
	private boolean reprovar;
	private boolean finalizar;
	private boolean agendar;
	// atributo para sinalizar que o processo PODE ser cancelado
	private boolean cancelar;
	// atributo para sinalizar que o processo FOI cancelado
	private boolean cancelado;
	private String acao;
	private String parecer;
	private String protocolo;
	
	private String motivoCancelamento;
	
	private String startUserId;


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

	public List<String> getGruposNotificar() {
		return gruposNotificar;
	}

	public void setGruposNotificar(List<String> gruposNotificar) {
		this.gruposNotificar = gruposNotificar;
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

	public String getSistema() {
		return sistema;
	}

	public void setSistema(String sistema) {
		this.sistema = sistema;
	}

	public String getTipoSolicitacao() {
		return tipoSolicitacao;
	}

	public void setTipoSolicitacao(String tipoSolicitacao) {
		this.tipoSolicitacao = tipoSolicitacao;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public boolean isDigitalizar() {
		return digitalizar;
	}

	public void setDigitalizar(boolean digitalizar) {
		this.digitalizar = digitalizar;
	}

	public boolean isAprovar() {
		return aprovar;
	}

	public void setAprovar(boolean aprovar) {
		this.aprovar = aprovar;
	}

	public boolean isReprovar() {
		return reprovar;
	}

	public void setReprovar(boolean reprovar) {
		this.reprovar = reprovar;
	}

	public boolean isFinalizar() {
		return finalizar;
	}

	public void setFinalizar(boolean finalizar) {
		this.finalizar = finalizar;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public boolean isAgendar() {
		return agendar;
	}

	public void setAgendar(boolean agendar) {
		this.agendar = agendar;
	}

	public boolean isCancelar() {
		return cancelar;
	}

	public void setCancelar(boolean cancelar) {
		this.cancelar = cancelar;
	}

	public boolean isCancelado() {
		return cancelado;
	}

	public void setCancelado(boolean cancelado) {
		this.cancelado = cancelado;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public String getParecer() {
		return parecer;
	}

	public void setParecer(String parecer) {
		this.parecer = parecer;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}
	
	public String getStartUserId() {
		return startUserId;
	}

	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}

	/**
	 * Metodo responsavel por converter lista de variaveis (Map) em um objeto
	 * VariaveisProcesso
	 * 
	 * @param variaveis
	 */
	@SuppressWarnings("unchecked")
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

			} else if (var.getName().equals("gruposNotificar")) {
				this.setGruposNotificar(var.getValue() != null ? (List<String>) var
						.getValue() : null);

			} else if (var.getName().equals("solicitante")
					&& var.getValue() != null) {
				this.setSolicitante(var.getValue().toString());

			} else if (var.getName().equals("emailSolicitante")
					&& var.getValue() != null) {
				this.setEmailSolicitante(var.getValue().toString());

			} else if (var.getName().equals("sistema")
					&& var.getValue() != null) {
				this.setSistema(var.getValue().toString());

			} else if (var.getName().equals("tipoSolicitacao")
					&& var.getValue() != null) {
				this.setTipoSolicitacao(var.getValue().toString());

			} else if (var.getName().equals("status") && var.getValue() != null) {
				this.setStatus(new Boolean(var.getValue().toString()));

			} else if (var.getName().equals("protocolo")
					&& var.getValue() != null) {
				this.setProtocolo(var.getValue().toString());

			} else if (var.getName().equals("acao")
					&& var.getValue() != null) {
				this.setAcao(var.getValue().toString());
				
			} else if (var.getName().equals("parecer")
					&& var.getValue() != null) {
				this.setParecer(var.getValue().toString());

			} else if (var.getName().equals("aprovar")
					&& var.getValue() != null) {
				this.setAprovar(new Boolean(var.getValue().toString()));

			} else if (var.getName().equals("reprovar")
					&& var.getValue() != null) {
				this.setReprovar(new Boolean(var.getValue().toString()));

			} else if (var.getName().equals("agendar")
					&& var.getValue() != null) {
				this.setAgendar(new Boolean(var.getValue().toString()));

			} else if (var.getName().equals("finalizar")
					&& var.getValue() != null) {
				this.setFinalizar(new Boolean(var.getValue().toString()));

			} else if (var.getName().equals("digitalizar")
					&& var.getValue() != null) {
				this.setDigitalizar(new Boolean(var.getValue().toString()));

			} else if (var.getName().equals("cancelar")
					&& var.getValue() != null) {
				this.setCancelar(new Boolean(var.getValue().toString()));

			} else if (var.getName().equals("cancelado")
					&& var.getValue() != null) {
				this.setCancelado(new Boolean(var.getValue().toString()));

			} else if (var.getName().equals("motivoCancelamento")
					&& var.getValue() != null) {
				this.setMotivoCancelamento(var.getValue().toString());

			} else if (var.getName().equals("situacao")
					&& var.getValue() != null) {
				this.setSituacao(var.getValue().toString());

			}
		}
	}

	public Map<String, Object> converterVariaveisProcessoParaMapaVariaveis() {

		Map<String, Object> params = new java.util.HashMap<String, Object>();
		params.put("sequencial", getSequencial());
		params.put("ano", getAno());
		params.put("gruposNotificar", getGruposNotificar());
		params.put("solicitante", getSolicitante());
		params.put("emailSolicitante", getEmailSolicitante());
		params.put("sistema", this.getSistema());
		params.put("tipoSolicitacao", getTipoSolicitacao());
		params.put("status", getStatus());
		params.put("descricao", getDescricao());
		params.put("protocolo", getSequencial() + "/" + getAno());
		params.put("descricao", getDescricao());
		params.put("acao", getAcao());
		params.put("parecer", getParecer());
		params.put("agendar", isAgendar());
		params.put("aprovar", isAprovar());
		params.put("reprovar", isReprovar());
		params.put("digitalizar", isDigitalizar());
		params.put("finalizar", isFinalizar());
		params.put("cancelar", isCancelar());
		params.put("cancelado", isCancelado());
		
		params.put("motivoCancelamento", getMotivoCancelamento());
		params.put("situacao", getSituacao());
		
		params.put("startUserId", getStartUserId());
		
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
