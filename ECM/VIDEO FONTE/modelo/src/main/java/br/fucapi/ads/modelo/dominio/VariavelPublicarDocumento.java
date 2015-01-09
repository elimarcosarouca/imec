package br.fucapi.ads.modelo.dominio;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import br.fucapi.bpms.activiti.dominio.Variaveis;
import br.fucapi.bpms.alfresco.dominio.Usuario;

public class VariavelPublicarDocumento extends Variavel {

	private static final long serialVersionUID = 6327176552063724332L;
	
	//dados enviado para o activiti
	private String tipoSolicitacao;

	private String protocoloOrigem;

	private Usuario proprietario;

	private String emailProprietario;

	private List<String> aprovadores;

	private List<String> emailAprovadores;
	
	private List<String> concensos;

	private List<String> emailConcensos;

	private List<String> gruposNotificar;
	
	private Arquivo arquivo;
	
	private boolean possuiTarja;
	
	private boolean publicacaoAutomatica;


	// dados da telas
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

	private int versaoDocumento;

	private String PUBLICAR_DOCUMENTO = "PUBLICAR_DOCUMENTO";

	public VariavelPublicarDocumento() {
		this.tipoSolicitacao = PUBLICAR_DOCUMENTO;
		this.unidade = new Unidade();
		this.tipoDocumento = new TipoDocumento();
		this.setor = new Setor();
		this.postoCopia = new PostoCopia();
		this.versaoDocumento = 1;
		this.possuiTarja = true;
		this.publicacaoAutomatica = true;
	}

	public String getPUBLICAR_DOCUMENTO() {
		return PUBLICAR_DOCUMENTO;
	}

	public List<String> getAprovadores() {
		return aprovadores;
	}

	public void setAprovadores(List<String> aprovadores) {
		this.aprovadores = aprovadores;
	}

	public List<String> getEmailAprovadores() {
		return emailAprovadores;
	}

	public void setEmailAprovadores(List<String> emailAprovadores) {
		this.emailAprovadores = emailAprovadores;
	}

	public List<String> getConcensos() {
		return concensos;
	}

	public void setConcensos(List<String> concensos) {
		this.concensos = concensos;
	}

	public List<String> getEmailConcensos() {
		return emailConcensos;
	}

	public void setEmailConcensos(List<String> emailConcensos) {
		this.emailConcensos = emailConcensos;
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

	public String getProtocoloOrigem() {
		return protocoloOrigem;
	}

	public void setProtocoloOrigem(String protocoloOrigem) {
		this.protocoloOrigem = protocoloOrigem;
	}

	public int getVersaoDocumento() {
		return versaoDocumento;
	}

	public void setVersaoDocumento(int versaoDocumento) {
		this.versaoDocumento = versaoDocumento;
	}

	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

	public boolean isPossuiTarja() {
		return possuiTarja;
	}

	public void setPossuiTarja(boolean possuiTarja) {
		this.possuiTarja = possuiTarja;
	}

	public boolean isPublicacaoAutomatica() {
		return publicacaoAutomatica;
	}

	public void setPublicacaoAutomatica(boolean publicacaoAutomatica) {
		this.publicacaoAutomatica = publicacaoAutomatica;
	}

	/**
	 * Metodo responsavel por converter lista de variaveis (Map) em um objeto
	 * VariaveisProcesso
	 * 
	 * @param variaveis
	 */
	@SuppressWarnings("unchecked")
	@Autowired(required = false)
	public void converterListaVariaveis(List<Variaveis> variaveisLista) {

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

			} else if (var.getName().equals("concensos")) {
				setConcensos(var.getValue() != null ? (List<String>) var
						.getValue() : null);
				
			} else if (var.getName().equals("emailConcensos")) {
				setEmailConcensos(var.getValue() != null ? (List<String>) var
						.getValue() : null);

			} else if (var.getName().equals("tipoSolicitacao")
					&& var.getValue() != null) {
				setTipoSolicitacao(var.getValue().toString());

			} else if (var.getName().equals("protocolo")
					&& var.getValue() != null) {
				this.setProtocolo(var.getValue().toString());

			} else if (var.getName().equals("protocoloOrigem")
					&& var.getValue() != null) {
				this.setProtocoloOrigem(var.getValue().toString());

			} else if (var.getName().equals("emailAprovadores")
					&& var.getValue() != null) {
				//TODO DEVERA SER ALIMENTADA A LISTA				
//				this.setEmailAprovadores( var.getValue().toString());

			} else if (var.getName().equals("versaoDocumento")) {
				try {
					this.setVersaoDocumento(Integer.valueOf(var.getValue()
							.toString()));
				} catch (NumberFormatException e) {

				}

			}

		}
	}

	public Map<String, Object> converterVariaveis() {

		Map<String, Object> params = super.converterVariaveis();

		params.put("tipoSolicitacao", this.getTipoSolicitacao());
		params.put("gruposNotificar", this.getGruposNotificar());

		params.put("proprietario", this.getProprietario());
		params.put("emailProprietario", this.getEmailProprietario());

		params.put("concesos", this.getConcensos());
		params.put("emailConcensos", this.getEmailConcensos());
		params.put("provadores", this.getAprovadores());
		params.put("emailArovadores", this.getEmailAprovadores());

		params.put("versaoDocumento", this.getVersaoDocumento());
		params.put("possuiTarja", this.isPossuiTarja());
		params.put("publicacaoAutomatica", this.isPublicacaoAutomatica());

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