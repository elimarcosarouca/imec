package br.fucapi.ads.modelo.dominio;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import br.fucapi.bpms.activiti.dominio.Variaveis;

public class VariaveisTreinamento extends VariaveisProcesso {

	private String dataInicial;
	private String dataFinal;

	private String coordenador;
	private String emailCoordenador;

	private String diretor;
	private String emailDiretor;

	private String rh;
	private String emailRh;

	private String funcionario;
	private String emailFuncionario;

	private String treinamento;
	private String custoEstimado;

	private String local;

	public String getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}

	public String getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(String coordenador) {
		this.coordenador = coordenador;
	}

	public String getEmailCoordenador() {
		return emailCoordenador;
	}

	public void setEmailCoordenador(String emailCoordenador) {
		this.emailCoordenador = emailCoordenador;
	}

	public String getDiretor() {
		return diretor;
	}

	public void setDiretor(String diretor) {
		this.diretor = diretor;
	}

	public String getEmailDiretor() {
		return emailDiretor;
	}

	public void setEmailDiretor(String emailDiretor) {
		this.emailDiretor = emailDiretor;
	}

	public String getRh() {
		return rh;
	}

	public void setRh(String rh) {
		this.rh = rh;
	}

	public String getEmailRh() {
		return emailRh;
	}

	public void setEmailRh(String emailRh) {
		this.emailRh = emailRh;
	}

	public String getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(String funcionario) {
		this.funcionario = funcionario;
	}

	public String getEmailFuncionario() {
		return emailFuncionario;
	}

	public void setEmailFuncionario(String emailFuncionario) {
		this.emailFuncionario = emailFuncionario;
	}

	public String getTreinamento() {
		return treinamento;
	}

	public void setTreinamento(String treinamento) {
		this.treinamento = treinamento;
	}

	public String getCustoEstimado() {
		return custoEstimado;
	}

	public void setCustoEstimado(String custoEstimado) {
		this.custoEstimado = custoEstimado;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
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

			if (var.getName().equals("dataInicial") && var.getValue() != null) {
				this.setDataInicial(var.getValue().toString());
			} else if (var.getName().equals("dataFinal")
					&& var.getValue() != null) {
				this.setDataFinal(var.getValue().toString());
			} else if (var.getName().equals("sequencial")
					&& var.getValue() != null) {
				super.setSequencial(Integer.valueOf(var.getValue() != null ? var
						.getValue().toString() : "0"));
			} else if (var.getName().equals("ano")) {
				try {
					super.setAno(Integer.valueOf(var.getValue().toString()));
				} catch (NumberFormatException e) {
				}
			} else if (var.getName().equals("descricao")
					&& var.getValue() != null) {
				super.setDescricao(var.getValue().toString());

			} else if (var.getName().equals("gruposNotificar")) {
				super.setGruposNotificar(var.getValue() != null ? (List<String>) var
						.getValue() : null);

			} else if (var.getName().equals("solicitante")
					&& var.getValue() != null) {
				super.setSolicitante(var.getValue().toString());

			} else if (var.getName().equals("emailSolicitante")
					&& var.getValue() != null) {
				super.setEmailSolicitante(var.getValue().toString());

			} else if (var.getName().equals("coordenador")
					&& var.getValue() != null) {
				this.setCoordenador(var.getValue().toString());

			} else if (var.getName().equals("emailCoordenador")
					&& var.getValue() != null) {
				this.setEmailCoordenador(var.getValue().toString());

			} else if (var.getName().equals("diretor")
					&& var.getValue() != null) {
				this.setDiretor(var.getValue().toString());

			} else if (var.getName().equals("emailDiretor")
					&& var.getValue() != null) {
				this.setEmailDiretor(var.getValue().toString());

			} else if (var.getName().equals("rh") && var.getValue() != null) {
				this.setRh(var.getValue().toString());

			} else if (var.getName().equals("emailRh")
					&& var.getValue() != null) {
				this.setEmailRh(var.getValue().toString());

			} else if (var.getName().equals("funcionario")
					&& var.getValue() != null) {
				this.setFuncionario(var.getValue().toString());

			} else if (var.getName().equals("emailFuncionario")
					&& var.getValue() != null) {
				this.setEmailFuncionario(var.getValue().toString());

			} else if (var.getName().equals("treinamento")
					&& var.getValue() != null) {
				this.setTreinamento(var.getValue().toString());

			} else if (var.getName().equals("custoEstimado")
					&& var.getValue() != null) {
				this.setCustoEstimado(var.getValue().toString());

			} else if (var.getName().equals("local")) {
				this.setLocal(var.getValue().toString());

			} else if (var.getName().equals("sistema")
					&& var.getValue() != null) {
				super.setSistema(var.getValue().toString());

			} else if (var.getName().equals("tipoSolicitacao")
					&& var.getValue() != null) {
				super.setTipoSolicitacao(var.getValue().toString());

			} else if (var.getName().equals("parecer")
					&& var.getValue() != null) {
				this.setParecer(var.getValue().toString());

			} else if (var.getName().equals("status") && var.getValue() != null) {
				super.setStatus(new Boolean(var.getValue().toString()));

			} else if (var.getName().equals("protocolo")
					&& var.getValue() != null) {
				this.setProtocolo(var.getValue().toString());

			} else if (var.getName().equals("acao")
					&& var.getValue() != null) {
				this.setAcao(var.getValue().toString());

			} else if (var.getName().equals("aprovar")
					&& var.getValue() != null) {
				this.setAprovar(new Boolean(var.getValue().toString()));

			} else if (var.getName().equals("reaprovar")
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

		Map<String, Object> params = super
				.converterVariaveisProcessoParaMapaVariaveis();

		params.put("dataInicial", this.getDataInicial());
		params.put("dataFinal", this.getDataFinal());
		params.put("coordenador", this.getCoordenador());
		params.put("emailCoordenador", this.getEmailCoordenador());
		params.put("diretor", this.getDiretor());
		params.put("emailDiretor", this.getEmailDiretor());
		params.put("rh", this.getRh());
		params.put("emailRh", this.getEmailRh());
		params.put("funcionario", this.getFuncionario());
		params.put("emailFuncionario", this.getEmailFuncionario());
		params.put("treinamento", this.getTreinamento());
		params.put("custoEstimado", this.getCustoEstimado());
		params.put("local", this.getLocal());
		params.put("sistema", this.getSistema());
		params.put("parecer", this.getParecer());
		params.put("descricao", super.getDescricao());
		
		return params;
	}
	
	public String ObjectToJson(String processoDefinitionKey, Protocolo protocolo) {

		String json = "{\"processDefinitionKey\":\"" + processoDefinitionKey
				+ "\",\"businessKey\":\"" + protocolo.toString() + "\","
				+ "\"variables\":[" + "{\"name\":\"dataInicial\",\"value\":\""
				+ this.getDataInicial() + "\"}"
				+ ",{\"name\":\"dataFinal\",\"value\":\"" + this.getDataFinal()
				+ "\"}" + ",{\"name\":\"sequencial\",\"value\":"
				+ super.getSequencial() + "}" + ",{\"name\":\"ano\",\"value\":"
				+ super.getAno() + "}"
				+ ",{\"name\":\"gruposNotificar\",\"value\":"
				+ super.getGruposNotificar() + "}"
				+ ",{\"name\":\"solicitante\",\"value\":\""
				+ super.getSolicitante() + "\"}"
				+ ",{\"name\":\"emailSolicitante\",\"value\":\""
				+ super.getEmailSolicitante() + "\"}"
				+ ",{\"name\":\"coordenador\",\"value\":\""
				+ this.getCoordenador() + "\"}"
				+ ",{\"name\":\"emailCoordenador\",\"value\":\""
				+ this.getEmailCoordenador() + "\"}"
				+ ",{\"name\":\"diretor\",\"value\":\"" + this.getDiretor()
				+ "\"}" + ",{\"name\":\"emailDiretor\",\"value\":\""
				+ this.getEmailDiretor() + "\"}"
				+ ",{\"name\":\"rh\",\"value\":\"" + this.getRh() + "\"}"
				+ ",{\"name\":\"emailRh\",\"value\":\"" + this.getEmailRh()
				+ "\"}" + ",{\"name\":\"funcionario\",\"value\":\""
				+ this.getFuncionario() + "\"}"
				+ ",{\"name\":\"emailFuncionario\",\"value\":\""
				+ this.getEmailFuncionario() + "\"}"
				+ ",{\"name\":\"treinamento\",\"value\":\""
				+ this.getTreinamento() + "\"}"
				+ ",{\"name\":\"custoEstimado\",\"value\":\""
				+ this.getCustoEstimado() + "\"}"
				+ ",{\"name\":\"local\",\"value\":\"" + this.getLocal() + "\"}"
				+ ",{\"name\":\"sistema\",\"value\":\"" + this.getSistema()
				+ "\"}" + ",{\"name\":\"descricao\",\"value\":\""
				+ super.getDescricao() + "\"}"
				+ ",{\"name\":\"protocolo\",\"value\":\""
				+ protocolo.getSequencial() + "/" + protocolo.getAno() + "\"}"
				+ "]}";

		return json;
	}
}