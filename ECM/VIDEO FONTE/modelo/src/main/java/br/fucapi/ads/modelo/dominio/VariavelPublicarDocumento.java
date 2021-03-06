package br.fucapi.ads.modelo.dominio;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import br.fucapi.ads.modelo.utils.GeralUtils;
//import br.fucapi.ads.modelo.utils.GeralUtils;
import br.fucapi.bpms.activiti.dominio.Variaveis;
import br.fucapi.bpms.alfresco.dominio.Usuario;

public class VariavelPublicarDocumento extends Variavel {

	private static final long serialVersionUID = 6327176552063724332L;

	// dados DEVEM enviado para o activiti
	private String tipoSolicitacao;

	private String protocoloOrigem;

	private List<String> elaboradores;

	private String emailProprietario;

	private List<String> aprovadores;

	private List<String> emailAprovadores;

	private List<String> concessoes;

	private List<String> emailConcessoes;

	private List<Long> postosCopia;

	private List<String> gruposNotificar;

	private Arquivo arquivoDoc;

	private Arquivo arquivoControlado;

	private Arquivo arquivoNaoControlado;

	private Arquivo arquivoObsoleto;

	private Arquivo arquivoCancelado;

	private String nomeDocumento;

	private boolean possuiTarja;

	private boolean publicacaoAutomatica;

	private boolean enviarConcensao;

	private boolean finalizarProcesso;

	private String alteracoes;

	private String codigo;

	private Long idAlerta;

	// dados da telas
	private PostoCopia postoCopia;

	private List<PostoCopia> postoCopias;

	private Setor setor;

	private Categoria categoria;

	private Unidade unidade;

	private List<Setor> setores;

	private List<Categoria> categorias;

	private List<Unidade> unidades;

	private List<Usuario> usuariosGrupoRevisores;

	private List<Usuario> proprietarios;

	private Date dataVencimento;

	private Date dataNotificacao;

	private int notificarVencimento;

	private int versaoRevisao;

	private final String PUBLICAR_DOCUMENTO = "PUBLICAR_DOCUMENTO";

	private List<PostoCopia> postosCopiaObjeto;

	/*
	 * Atributo utilizado para guardar no activiti o objeto completo de usuario
	 * aprovador.
	 */
	private List<Usuario> aprovadoresObjeto;

	/*
	 * Atributo utilizado para guardar no activiti o objeto completo de usuario
	 * elaborador.
	 */
	private List<Usuario> elaboradoresObjeto;

	/*
	 * Atributo utilizado para guardar no activiti o objeto completo de usuario
	 * concenso.
	 */
	private List<Usuario> concessoesObjeto;

	public VariavelPublicarDocumento() {
		this.tipoSolicitacao = PUBLICAR_DOCUMENTO;
		this.unidade = new Unidade();
		this.categoria = new Categoria();
		this.setor = new Setor();
		this.postoCopia = new PostoCopia();
		this.versaoRevisao = 0;
		this.idAlerta = 0l;
		this.possuiTarja = true;
		this.publicacaoAutomatica = true;
		this.aprovadores = new ArrayList<String>();
		this.elaboradores = new ArrayList<String>();
		this.concessoes = new ArrayList<String>();
		this.postosCopia = new ArrayList<Long>();
		this.emailAprovadores = new ArrayList<String>();
		this.emailConcessoes = new ArrayList<String>();
		this.arquivoDoc = new Arquivo();
		this.arquivoControlado = new Arquivo();
		this.arquivoNaoControlado = new Arquivo();
		this.arquivoObsoleto = new Arquivo();
		this.arquivoCancelado = new Arquivo();
		this.postosCopiaObjeto = new ArrayList<PostoCopia>();
		this.aprovadoresObjeto = new ArrayList<Usuario>();
		this.elaboradoresObjeto = new ArrayList<Usuario>();
		this.concessoesObjeto = new ArrayList<Usuario>();
		this.notificarVencimento = 30;
	}

	public void tratarAtributos(List<Usuario> aprovadoresTarget,
			List<Usuario> concessoesTarget, List<Usuario> elaboradoesTarget,
			List<PostoCopia> postosCopiaTarget) {

		this.dataNotificacao = GeralUtils.gerarDataNotificacao(
				this.dataVencimento, this.notificarVencimento);

		if (aprovadoresTarget != null && aprovadoresTarget.size() > 0) {
			this.aprovadoresObjeto = aprovadoresTarget;
			for (Usuario u : aprovadoresTarget) {
				this.aprovadores.add(u.getUserName());
				this.emailAprovadores.add(u.getEmail());

			}
		}

		if (concessoesTarget != null && concessoesTarget.size() > 0) {
			this.concessoesObjeto = concessoesTarget;
			for (Usuario u : concessoesTarget) {
				this.concessoes.add(u.getUserName());
				this.emailConcessoes.add(u.getEmail());
			}
		}

		if (elaboradoesTarget != null && elaboradoesTarget.size() > 0) {
			this.elaboradoresObjeto = elaboradoesTarget;
			for (Usuario u : elaboradoesTarget) {
				this.elaboradores.add(u.getUserName());
			}
		}

		if (postosCopiaTarget != null && postosCopiaTarget.size() > 0) {
			this.postosCopiaObjeto = postosCopiaTarget;
			for (PostoCopia p : postosCopiaTarget) {
				this.postosCopia.add(p.getId());
			}
		}

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

	public List<String> getConcessoes() {
		return concessoes;
	}

	public void setConcessoes(List<String> concessoes) {
		this.concessoes = concessoes;
	}

	public List<String> getEmailConcessoes() {
		return emailConcessoes;
	}

	public void setEmailConcessoes(List<String> emailConcessoes) {
		this.emailConcessoes = emailConcessoes;
	}

	public List<Long> getPostosCopia() {
		return postosCopia;
	}

	public void setPostosCopia(List<Long> postosCopia) {
		this.postosCopia = postosCopia;
	}

	public List<PostoCopia> getPostosCopiaObjeto() {
		return postosCopiaObjeto;
	}

	public void setPostosCopiaObjeto(List<PostoCopia> postosCopiaObjeto) {
		this.postosCopiaObjeto = postosCopiaObjeto;
	}

	public Date getDataVencimento() {
		return this.dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataNotificacao() {
		return dataNotificacao;
	}

	public void setDataNotificacao(Date dataNotificacao) {
		this.dataNotificacao = dataNotificacao;
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

	public List<String> getElaboradores() {
		return elaboradores;
	}

	public void setElaboradores(List<String> elaboradores) {
		this.elaboradores = elaboradores;
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public List<Setor> getSetores() {
		return setores;
	}

	public void setSetores(List<Setor> setores) {
		this.setores = setores;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
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

	public int getVersaoRevisao() {
		return versaoRevisao;
	}

	public void setVersaoRevisao(int versaoRevisao) {
		this.versaoRevisao = versaoRevisao;
	}

	public Long getIdAlerta() {
		return idAlerta;
	}

	public void setIdAlerta(Long idAlerta) {
		this.idAlerta = idAlerta;
	}

	public Arquivo getArquivoDoc() {
		return arquivoDoc;
	}

	public Arquivo getArquivoControlado() {
		return arquivoControlado;
	}

	public void setArquivoControlado(Arquivo arquivoControlado) {
		this.arquivoControlado = arquivoControlado;
	}

	public Arquivo getArquivoNaoControlado() {
		return arquivoNaoControlado;
	}

	public void setArquivoNaoControlado(Arquivo arquivoNaoControlado) {
		this.arquivoNaoControlado = arquivoNaoControlado;
	}

	public Arquivo getArquivoObsoleto() {
		return arquivoObsoleto;
	}

	public void setArquivoObsoleto(Arquivo arquivoObsoleto) {
		this.arquivoObsoleto = arquivoObsoleto;
	}

	public Arquivo getArquivoCancelado() {
		return arquivoCancelado;
	}

	public void setArquivoCancelado(Arquivo arquivoCancelado) {
		this.arquivoCancelado = arquivoCancelado;
	}

	public void setArquivoDoc(Arquivo arquivoDoc) {
		this.arquivoDoc = arquivoDoc;
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

	public boolean isEnviarConcensao() {
		return enviarConcensao;
	}

	public void setEnviarConcensao(boolean enviarConcensao) {
		this.enviarConcensao = enviarConcensao;
	}

	public boolean isFinalizarProcesso() {
		return finalizarProcesso;
	}

	public void setFinalizarProcesso(boolean finalizarProcesso) {
		this.finalizarProcesso = finalizarProcesso;
	}

	public String getAlteracoes() {
		return alteracoes;
	}

	public void setAlteracoes(String alteracoes) {
		this.alteracoes = alteracoes;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<PostoCopia> getPostosCopiaObject() {
		return postosCopiaObjeto;
	}

	public void setPostosCopiaObject(List<PostoCopia> postosCopiaObject) {
		this.postosCopiaObjeto = postosCopiaObject;
	}

	public List<Usuario> getAprovadoresObjeto() {
		return aprovadoresObjeto;
	}

	public void setAprovadoresObjeto(List<Usuario> aprovadoresObjeto) {
		this.aprovadoresObjeto = aprovadoresObjeto;
	}

	public List<Usuario> getElaboradoresObjeto() {
		return elaboradoresObjeto;
	}

	public void setElaboradoresObjeto(List<Usuario> elaboradoresObjeto) {
		this.elaboradoresObjeto = elaboradoresObjeto;
	}

	public List<Usuario> getConcessoesObjeto() {
		return concessoesObjeto;
	}

	public void setConcessoesObjeto(List<Usuario> concessoesObjeto) {
		this.concessoesObjeto = concessoesObjeto;
	}

	public List<PostoCopia> getPostoCopias() {
		return postoCopias;
	}

	public void setPostoCopias(List<PostoCopia> postoCopias) {
		this.postoCopias = postoCopias;
	}

	public String getNomeDocumento() {
		return nomeDocumento;
	}

	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}

	public String pegarRodape() {
		return "Nome: " + getNomeDocumento() + " - Código: " + getCodigo();
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
				System.out.println(((Arquivo) var.getValue()).getUuid());

			} else if (var.getName().equals("arquivoDoc")) {
				setArquivoDoc(var.getValue() != null ? (Arquivo) var.getValue()
						: null);

			} else if (var.getName().equals("arquivoControlado")) {
				setArquivoControlado(var.getValue() != null ? (Arquivo) var
						.getValue() : null);

			} else if (var.getName().equals("arquivoNaoControlado")) {
				setArquivoNaoControlado(var.getValue() != null ? (Arquivo) var
						.getValue() : null);

			} else if (var.getName().equals("arquivoObsoleto")) {
				setArquivoObsoleto(var.getValue() != null ? (Arquivo) var
						.getValue() : null);

			} else if (var.getName().equals("arquivoCancelado")) {
				setArquivoCancelado(var.getValue() != null ? (Arquivo) var
						.getValue() : null);

			} else if (var.getName().equals("concessoes")) {
				setConcessoes(var.getValue() != null ? (List<String>) var
						.getValue() : null);

			} else if (var.getName().equals("emailConcessoes")) {
				setEmailConcessoes(var.getValue() != null ? (List<String>) var
						.getValue() : null);

			} else if (var.getName().equals("tipoSolicitacao")
					&& var.getValue() != null) {
				setTipoSolicitacao(var.getValue().toString());

			} else if (var.getName().equals("solicitante")
					&& var.getValue() != null) {
				setSolicitante(var.getValue().toString());

			} else if (var.getName().equals("protocolo")
					&& var.getValue() != null) {
				this.setProtocolo(var.getValue().toString());

			} else if (var.getName().equals("protocoloOrigem")
					&& var.getValue() != null) {
				this.setProtocoloOrigem(var.getValue().toString());

			} else if (var.getName().equals("enviarConcensao")
					&& var.getValue() != null) {
				this.setEnviarConcensao(new Boolean(var.getValue().toString()));

			} else if (var.getName().equals("finalizarProcesso")
					&& var.getValue() != null) {
				this.setFinalizarProcesso(new Boolean(var.getValue().toString()));

			} else if (var.getName().equals("publicacaoAutomatica")
					&& var.getValue() != null) {
				this.setPublicacaoAutomatica(new Boolean(var.getValue()
						.toString()));

			} else if ("aprovadores".equals(var.getName())
					&& var.getValue() != null) {
				this.setAprovadores(var.getValue() != null ? (List<String>) var
						.getValue() : null);

			} else if (var.getName().equals("emailAprovadores")
					&& var.getValue() != null) {
				this.setEmailAprovadores(var.getValue() != null ? (List<String>) var
						.getValue() : null);

			} else if ("elaboradores".equals(var.getName())
					&& var.getValue() != null) {
				this.setElaboradores(var.getValue() != null ? (List<String>) var
						.getValue() : null);

			} else if (var.getName().equals("idAlerta")) {
				try {
					this.setIdAlerta(Long.valueOf(var.getValue().toString()));
				} catch (NumberFormatException e) {

				}

			} else if (var.getName().equals("versaoRevisao")) {
				try {
					this.setVersaoRevisao(Integer.valueOf(var.getValue()
							.toString()));
				} catch (NumberFormatException e) {

				}

			} else if (var.getName().equals("alteracoes")
					&& var.getValue() != null) {
				this.setAlteracoes(var.getValue().toString());

			} else if (var.getName().equals("codigo") && var.getValue() != null) {
				this.setCodigo(var.getValue().toString());

			} else if (var.getName().equals("categoria")
					&& var.getValue() != null) {

				setCategoria(var.getValue() != null ? (Categoria) var
						.getValue() : null);

			} else if (var.getName().equals("unidade")
					&& var.getValue() != null) {

				setUnidade(var.getValue() != null ? (Unidade) var.getValue()
						: null);

			} else if (var.getName().equals("setor") && var.getValue() != null) {

				setSetor(var.getValue() != null ? (Setor) var.getValue() : null);
			} else if (var.getName().equals("dataVencimento")
					&& var.getValue() != null) {
				String data = var.getValue().toString();

				DateFormat df = new SimpleDateFormat(
						"EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
				try {
					this.dataVencimento = df.parse(data);
				} catch (ParseException e) {
					e.printStackTrace();
				}

			} else if (var.getName().equals("dataNotificacao")
					&& var.getValue() != null) {

				String data = var.getValue().toString();

				DateFormat df = new SimpleDateFormat(
						"EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
				try {
					this.dataNotificacao = df.parse(data);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else if ("postosCopia".equals(var.getName())
					&& var.getValue() != null) {
				this.setPostosCopia(var.getValue() != null ? (List<Long>) var
						.getValue() : null);

			} else if ("postosCopiaObjeto".equals(var.getName())
					&& var.getValue() != null) {
				this.setPostosCopiaObjeto(var.getValue() != null ? (List<PostoCopia>) var
						.getValue() : null);

			} else if ("aprovadoresObjeto".equals(var.getName())
					&& var.getValue() != null) {
				this.setAprovadoresObjeto(var.getValue() != null ? (List<Usuario>) var
						.getValue() : null);

			} else if ("elaboradoresObjeto".equals(var.getName())
					&& var.getValue() != null) {
				this.setElaboradoresObjeto(var.getValue() != null ? (List<Usuario>) var
						.getValue() : null);

			} else if ("concessoesObjeto".equals(var.getName())
					&& var.getValue() != null) {
				this.setConcessoesObjeto(var.getValue() != null ? (List<Usuario>) var
						.getValue() : null);

			} else if (var.getName().equals("statusProcesso")
					&& var.getValue() != null) {
				this.setStatusProcesso(var.getValue().toString());

			} else if (var.getName().equals("justificativaStatus")
					&& var.getValue() != null) {
				this.setJustificativaStatus(var.getValue().toString());

			} else if (var.getName().equals("nomeDocumento")
					&& var.getValue() != null) {
				this.setNomeDocumento(var.getValue().toString());
			}
		}
	}

	public Map<String, Object> converterVariaveis() {
		Map<String, Object> params = super.converterVariaveis();

		params = super.converterVariaveis();

		params.put("nomeDocumento", this.getNomeDocumento().toUpperCase());
		params.put("tipoSolicitacao", this.getTipoSolicitacao());
		params.put("gruposNotificar", this.getGruposNotificar());

		params.put("elaboradores", this.getElaboradores());
		params.put("emailProprietario", this.getEmailProprietario());

		params.put("concessoes", this.getConcessoes());
		params.put("emailConcessoes", this.getEmailConcessoes());

		params.put("aprovadores", this.getAprovadores());
		params.put("emailArovadores", this.getEmailAprovadores());

		params.put("versaoRevisao", this.getVersaoRevisao());
		params.put("idAlerta", this.getIdAlerta());
		params.put("possuiTarja", this.isPossuiTarja());
		params.put("publicacaoAutomatica", this.isPublicacaoAutomatica());
		params.put("enviarConcensao", this.isEnviarConcensao());

		params.put("arquivoDoc", this.getArquivoDoc());
		params.put("arquivoControlado", this.getArquivoControlado());
		params.put("arquivoNaoControlado", this.getArquivoNaoControlado());
		params.put("arquivoObsoleto", this.getArquivoObsoleto());
		params.put("arquivoCancelado", this.getArquivoCancelado());

		params.put("categoria", this.getCategoria());
		params.put("unidade", this.getUnidade());
		params.put("setor", this.getSetor());

		if (null == this.getProtocoloOrigem())
			params.put("protocoloOrigem",
					this.getAno() + "" + this.getSequencial());
		else
			params.put("protocoloOrigem", this.getProtocoloOrigem());

		params.put("alteracoes", this.getAlteracoes());
		params.put("codigo", this.getCodigo());

		params.put("dataVencimento", this.getDataVencimento());
		params.put("dataNotificacao", this.getDataNotificacao());

		params.put("postosCopia", this.getPostosCopia());

		params.put("postosCopiaObjeto", this.getPostosCopiaObjeto());
		params.put("aprovadoresObjeto", this.getAprovadoresObjeto());
		params.put("elaboradoresObjeto", this.getElaboradoresObjeto());
		params.put("concessoesObjeto", this.getConcessoesObjeto());

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