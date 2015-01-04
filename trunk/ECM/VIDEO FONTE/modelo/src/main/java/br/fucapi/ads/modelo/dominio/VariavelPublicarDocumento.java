package br.fucapi.ads.modelo.dominio;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import br.fucapi.bpms.activiti.dominio.Variaveis;
import br.fucapi.bpms.alfresco.dominio.Usuario;

public class VariavelPublicarDocumento extends Variavel {

	private static final long serialVersionUID = 6327176552063724332L;

	private String tipoSolicitacao;

	private Usuario proprietario;

	private String emailProprietario;

	private List<String> revisores;

	private List<String> emailRevisores;

	private List<String> gruposNotificar;

	private PostoCopia postoCopia;

	private Setor setor;

	private TipoDocumento tipoDocumento;

	private Unidade unidade;

	private List<PostoCopia> postoCopias;

	private List<Setor> setores;

	private List<TipoDocumento> tipoDocumentos;

	private List<Unidade> unidades;

	private List<Usuario> usuariosGrupoRevisores;

	private List<Usuario> proprietarios;
	
	private String dataVencimento;
	
	private int notificarVencimento;
	
	private String PUBLICAR_DOCUMENTO = "PUBLICAR_DOCUMENTO";

	public VariavelPublicarDocumento() {
		this.tipoSolicitacao = PUBLICAR_DOCUMENTO;
		this.unidade = new Unidade();
		this.tipoDocumento = new TipoDocumento();
		this.setor = new Setor();
		this.postoCopia = new PostoCopia();
	}

	public List<String> getRevisores() {
		return revisores;
	}

	public void setRevisores(List<String> revisores) {
		this.revisores = revisores;
	}

	public List<String> getEmailRevisores() {
		return emailRevisores;
	}

	public void setEmailRevisore(List<String> emailRevisores) {
		this.emailRevisores = emailRevisores;
	}

	public String getTipoSolicitacao() {
		return tipoSolicitacao;
	}

	public void setTipoSolicitacao(String tipoSolicitacao) {
		this.tipoSolicitacao = tipoSolicitacao;
	}

	public List<String> getGruposNotificar() {
		return gruposNotificar;
	}

	public void setGruposNotificar(List<String> gruposNotificar) {
		this.gruposNotificar = gruposNotificar;
	}

	public Usuario getProprietario() {
		return proprietario;
	}

	public void setProprietario(Usuario proprietario) {
		this.proprietario = proprietario;
	}

	public String getEmailProprietario() {
		return emailProprietario;
	}

	public void setEmailProprietario(String emailProprietario) {
		this.emailProprietario = emailProprietario;
	}

	public void setEmailRevisores(List<String> emailRevisores) {
		this.emailRevisores = emailRevisores;
	}

	public PostoCopia getPostoCopia() {
		return postoCopia;
	}

	public void setPostoCopia(PostoCopia postoCopia) {
		this.postoCopia = postoCopia;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public List<PostoCopia> getPostoCopias() {
		return postoCopias;
	}

	public void setPostoCopias(List<PostoCopia> postoCopias) {
		this.postoCopias = postoCopias;
	}

	public List<Setor> getSetores() {
		return setores;
	}

	public void setSetores(List<Setor> setores) {
		this.setores = setores;
	}

	public List<TipoDocumento> getTipoDocumentos() {
		return tipoDocumentos;
	}

	public void setTipoDocumentos(List<TipoDocumento> tipoDocumentos) {
		this.tipoDocumentos = tipoDocumentos;
	}

	public List<Unidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidade> unidades) {
		this.unidades = unidades;
	}

	public List<Usuario> getUsuariosGrupoRevisores() {
		return usuariosGrupoRevisores;
	}

	public void setUsuariosGrupoRevisores(List<Usuario> usuariosGrupoRevisores) {
		this.usuariosGrupoRevisores = usuariosGrupoRevisores;
	}

	public List<Usuario> getProprietarios() {
		return proprietarios;
	}

	public void setProprietarios(List<Usuario> proprietarios) {
		this.proprietarios = proprietarios;
	}

	public String getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(String dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public int getNotificarVencimento() {
		return notificarVencimento;
	}

	public void setNotificarVencimento(int notificarVencimento) {
		this.notificarVencimento = notificarVencimento;
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
				setGruposNotificar(var.getValue() != null ? (List<String>) var
						.getValue() : null);

			} else if (var.getName().equals("revisores")) {
				setRevisores(var.getValue() != null ? (List<String>) var
						.getValue() : null);

			} else if (var.getName().equals("tipoSolicitacao")
					&& var.getValue() != null) {
				setTipoSolicitacao(var.getValue().toString());

			} else if (var.getName().equals("protocolo")
					&& var.getValue() != null) {
				this.setProtocolo(var.getValue().toString());

			}
		}
	}

	public Map<String, Object> converterVariaveisProcessoParaMapaVariaveis() {

		Map<String, Object> params = super
				.converterVariaveisProcessoParaMapaVariaveis();

		params.put("tipoSolicitacao", this.getTipoSolicitacao());
		params.put("revisores", this.getRevisores());
		params.put("emailRevisores", this.getEmailRevisores());
		params.put("gruposNotificar", this.getGruposNotificar());

		params.put("proprietario", this.getProprietario());
		params.put("emailProprietario", this.getEmailProprietario());

		return params;
	}

	public String ObjectToJson(String processoDefinitionKey, Protocolo protocolo) {

		/*
		 * String json = "{\"processDefinitionKey\":\"" + processoDefinitionKey
		 * + "\",\"businessKey\":\"" + protocolo.toString() + "\"," +
		 * "\"variables\":[" + "{\"name\":\"dataInicial\",\"value\":\"" +
		 * this.getDataInicial() + "\"}" +
		 * ",{\"name\":\"dataFinal\",\"value\":\"" + this.getDataFinal() + "\"}"
		 * + ",{\"name\":\"sequencial\",\"value\":" + super.getSequencial() +
		 * "}" + ",{\"name\":\"ano\",\"value\":" + super.getAno() + "}" +
		 * ",{\"name\":\"gruposNotificar\",\"value\":" +
		 * super.getGruposNotificar() + "}" +
		 * ",{\"name\":\"solicitante\",\"value\":\"" + super.getSolicitante() +
		 * "\"}" + ",{\"name\":\"emailSolicitante\",\"value\":\"" +
		 * super.getEmailSolicitante() + "\"}" +
		 * ",{\"name\":\"coordenador\",\"value\":\"" + this.getCoordenador() +
		 * "\"}" + ",{\"name\":\"emailCoordenador\",\"value\":\"" +
		 * this.getEmailCoordenador() + "\"}" +
		 * ",{\"name\":\"diretor\",\"value\":\"" + this.getDiretor() + "\"}" +
		 * ",{\"name\":\"emailDiretor\",\"value\":\"" + this.getEmailDiretor() +
		 * "\"}" + ",{\"name\":\"rh\",\"value\":\"" + this.getRh() + "\"}" +
		 * ",{\"name\":\"emailRh\",\"value\":\"" + this.getEmailRh() + "\"}" +
		 * ",{\"name\":\"funcionario\",\"value\":\"" + this.getFuncionario() +
		 * "\"}" + ",{\"name\":\"emailFuncionario\",\"value\":\"" +
		 * this.getEmailFuncionario() + "\"}" +
		 * ",{\"name\":\"treinamento\",\"value\":\"" + this.getTreinamento() +
		 * "\"}" + ",{\"name\":\"custoEstimado\",\"value\":\"" +
		 * this.getCustoEstimado() + "\"}" + ",{\"name\":\"local\",\"value\":\""
		 * + this.getLocal() + "\"}" + ",{\"name\":\"sistema\",\"value\":\"" +
		 * this.getSistema() + "\"}" + ",{\"name\":\"descricao\",\"value\":\"" +
		 * super.getDescricao() + "\"}" +
		 * ",{\"name\":\"protocolo\",\"value\":\"" + protocolo.getSequencial() +
		 * "/" + protocolo.getAno() + "\"}" + "]}";
		 */

		return "";
	}
}