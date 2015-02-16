package br.fucapi.ads.modelo.dominio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import br.fucapi.ads.modelo.utils.GeralUtils;
//import br.fucapi.ads.modelo.utils.GeralUtils;
import br.fucapi.bpms.activiti.dominio.Variaveis;
import br.fucapi.bpms.alfresco.dominio.Usuario;

public class VariavelPublicarDocumento extends Variavel {

	private static final long serialVersionUID = 6327176552063724332L;
	
	//dados DEVEM enviado para o activiti
	private String tipoSolicitacao;

	private String protocoloOrigem;

	private  List<String> elaboradores;

	private String emailProprietario;

	private List<String> aprovadores;

	private List<String> emailAprovadores;
	
	private List<String> concensos;

	private List<String> emailConcensos;
	
	private List<String> postosCopia;

	private List<String> gruposNotificar;
	
	private Arquivo arquivoDoc;
	
	private Arquivo arquivoControlado;
	
	private Arquivo arquivoNaoControlado;
	
	private boolean possuiTarja;
	
	private boolean publicacaoAutomatica;
	
	private boolean enviarConcensao;


	// dados da telas
	private PostoCopia postoCopia;

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

	public VariavelPublicarDocumento() {
		this.tipoSolicitacao = PUBLICAR_DOCUMENTO;
		this.unidade = new Unidade();
		this.categoria = new Categoria();
		this.setor = new Setor();
		this.postoCopia = new PostoCopia();
		this.versaoRevisao = 0;
		this.possuiTarja = true;
		this.publicacaoAutomatica = true;
		this.aprovadores = new ArrayList<String>();
		this.elaboradores = new ArrayList<String>();
		this.concensos = new ArrayList<String>();
		this.emailAprovadores = new ArrayList<String>();
		this.emailConcensos = new ArrayList<String>();
		this.arquivoDoc = new Arquivo();
		this.arquivoControlado = new Arquivo();
		this.arquivoNaoControlado = new Arquivo();
	}

	public void tratarAtributos(List<Usuario> aprovadoresTarget, 
			List<Usuario> concensosTarget, List<Usuario> elaboradoesTarget) {
		
		this.dataNotificacao = GeralUtils.gerarDataNotificacao(this.dataVencimento, this.notificarVencimento);
		
		if(aprovadoresTarget != null && aprovadoresTarget.size() > 0){
			for (Usuario u :  concensosTarget) {
				this.aprovadores.add(u.getUserName());
				this.emailAprovadores.add(u.getEmail());
				
			}
		}
		
		if(concensosTarget != null && concensosTarget.size() > 0){
			for (Usuario u : concensosTarget) {
				this.concensos.add(u.getUserName());
				this.emailConcensos.add(u.getEmail());
			}
		}
		
		if(elaboradoesTarget != null && elaboradoesTarget.size() > 0){
			for (Usuario u : elaboradoesTarget) {
				this.elaboradores.add(u.getUserName());
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

	public List<String> getPostosCopia() {
		return postosCopia;
	}

	public void setPostosCopia(List<String> postosCopia) {
		this.postosCopia = postosCopia;
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
				System.out.println(((Arquivo)var.getValue()).getUuid());
				
			} else if (var.getName().equals("arquivoDoc")) {
				setArquivoDoc(var.getValue() != null ? (Arquivo) var
						.getValue() : null);
				System.out.println("#################################### "+((Arquivo)var.getValue()).getUuid());

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
				
			} else if (var.getName().equals("enviarConcensao")
					&& var.getValue() != null) {
				this.setEnviarConcensao(new Boolean (var.getValue().toString()));
				
			} else if (var.getName().equals("publicacaoAutomatica")
					&& var.getValue() != null) {
				this.setPublicacaoAutomatica(new Boolean (var.getValue().toString()));

			} else if (var.getName().equals("emailAprovadores")
					&& var.getValue() != null) {
				//TODO DEVERA SER ALIMENTADA A LISTA				
//				this.setEmailAprovadores( var.getValue().toString());

			} else if (var.getName().equals("versaoRevisao")) {
				try {
					this.setVersaoRevisao(Integer.valueOf(var.getValue()
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

		params.put("elaboradores", this.getElaboradores());
		params.put("emailProprietario", this.getEmailProprietario());

		params.put("emailConcensos", this.getEmailConcensos());
		params.put("emailArovadores", this.getEmailAprovadores());

		params.put("versaoRevisao", this.getVersaoRevisao());
		params.put("possuiTarja", this.isPossuiTarja());
		params.put("publicacaoAutomatica", this.isPublicacaoAutomatica());
		params.put("enviarConcensao", this.isEnviarConcensao());
		
		params.put("arquivoDoc", this.getArquivoDoc());
		params.put("arquivoControlado", this.getArquivoControlado());
		params.put("arquivoNaoControlado", this.getArquivoNaoControlado());
		
		params.put("aprovadores", this.getAprovadores());
		params.put("concensos", this.getConcensos());
		params.put("elaboradores", this.getElaboradores());
		params.put("categoria", this.getCategoria());
		
		if (null == this.getProtocoloOrigem())
			params.put("protocoloOrigem", this.getProtocolo());
		
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