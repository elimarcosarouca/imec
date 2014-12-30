package br.fucapi.bpms.web.dominio;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import br.fucapi.bpms.activiti.dominio.Variaveis;

public class VariaveisProcesso {

	private String dataInicial;
	private Date dataFinal;
	private Integer sequencial;
	private Integer ano;
	private TipoDocumento tipoDocumento;

	private Origem origem;
	private Arquivo arquivo;
	private List<Desenho> desenhos;
	private String descricao;
	private String revisor1;
	private String emailRevisor1;
	private String revisor2;
	private String emailRevisor2;
	private String aprovador;
	private List<String> gruposNotificar;
	private String emailCriador;
	private String emailAprovador;
	private String criador;
	
	public String getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
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

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Origem getOrigem() {
		return origem;
	}

	public void setOrigem(Origem origem) {
		this.origem = origem;
	}

	public List<Desenho> getDesenhos() {
		return desenhos;
	}

	public void setDesenhos(List<Desenho> desenhos) {
		this.desenhos = desenhos;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

	public String getRevisor1() {
		return revisor1;
	}

	public void setRevisor1(String revisor1) {
		this.revisor1 = revisor1;
	}
	
	public String getEmailRevisor1() {
		return emailRevisor1;
	}

	public void setEmailRevisor1(String emailRevisor1) {
		this.emailRevisor1 = emailRevisor1;
	}

	public String getRevisor2() {
		return revisor2;
	}

	public void setRevisor2(String revisor2) {
		this.revisor2 = revisor2;
	}
	
	public String getEmailRevisor2() {
		return emailRevisor2;
	}

	public void setEmailRevisor2(String emailRevisor2) {
		this.emailRevisor2 = emailRevisor2;
	}

	public String getAprovador() {
		return aprovador;
	}

	public void setAprovador(String aprovador) {
		this.aprovador = aprovador;
	}
	
	public String getEmailAprovador() {
		return emailAprovador;
	}

	public void setEmailAprovador(String emailAprovador) {
		this.emailAprovador = emailAprovador;
	}
	
	public String getCriador() {
		return criador;
	}

	public void setCriador(String criador) {
		this.criador = criador;
	}

	public List<String> getGruposNotificar() {
		return gruposNotificar;
	}

	public void setGruposNotificar(List<String> gruposNotificar) {
		this.gruposNotificar = gruposNotificar;
	}

	public String getEmailCriador() {
		return emailCriador;
	}

	public void setEmailCriador(String emailCriador) {
		this.emailCriador = emailCriador;
	}

	/**
	 * Metodo responsavel por converter lista de variaveis (Map) em um objeto
	 * VariaveisProcesso
	 * 
	 * @param variaveis
	 */
	@SuppressWarnings("unchecked")
	@Autowired(required = false)
	public void converterListaVariaveisParaVariaveisProcesso(List<Variaveis> variaveisLista) {
		for (Variaveis var : variaveisLista) {
			if (var.getName().equals("dataInicial")) {
				this.setDataInicial(var.getValue().toString());
				
			} else if (var.getName().equals("sequencial")) {
				this.setSequencial(Integer.valueOf(var.getValue()!=null?var.getValue().toString():"0")); 
				
			} else if (var.getName().equals("ano")) {
				this.setAno(Integer.valueOf(var.getValue().toString()));
				
			} else if (var.getName().equals("tipoDocumento")) {
				TipoDocumento td = new TipoDocumento();
				if (var.getValue() instanceof Map)  {
					Map<String, Object> params =  (Map<String, Object>)var.getValue();
					td.setId(Long.valueOf(params.get("id")!=null?params.get("id").toString():"0")); 
					td.setNome(params.get("nome")!=null?params.get("nome").toString():null);
					td.setSigla(params.get("sigla")!=null?params.get("sigla").toString():null);
				} else if (var.getValue() instanceof TipoDocumento ){
					td = (TipoDocumento)var.getValue();
				}
				this.setTipoDocumento(td);
				
			} else if (var.getName().equals("origem")) {
				Origem ori = new Origem();
				if (var.getValue() instanceof Map)  {
					Map<String, Object> params =  (Map<String, Object>)var.getValue();
					ori.setId(Long.valueOf(params.get("id")!=null?params.get("id").toString():"0")); 
					ori.setNome(params.get("nome")!=null?params.get("nome").toString():null);
					ori.setSigla(params.get("sigla")!=null?params.get("sigla").toString():null);
				} else if(var.getValue() instanceof Origem) {
					ori = (Origem)var.getValue();
				}
				this.setOrigem(ori);
				
			} else if (var.getName().equals("desenhos")) {
				this.desenhos = new ArrayList<Desenho>();
				
				if (var.getValue() !=null && var.getValue() instanceof List) {
					if (((List<Object>)var.getValue()).iterator().next() instanceof Map) {
						List<Object> objResultado = (List<Object>)var.getValue();
						Desenho desenho = null;
						Map<String, Object> mapaDesenho = null;
						for (Object o : objResultado) {
							mapaDesenho = (Map<String, Object>)o;
							desenho = new Desenho();
							desenho.setComplemento(mapaDesenho.get("complemento")!=null?mapaDesenho.get("complemento").toString():null);
//							desenho.setTipo(mapaDesenho.get("tipo")!=null?mapaDesenho.get("tipo").toString():null);
							desenho.setGrupo(mapaDesenho.get("grupo")!=null?mapaDesenho.get("grupo").toString():null);
							desenho.setTipoModificacao(TipoModificacao.valueOf(mapaDesenho.get("tipoModificacao").toString()));
							desenho.setUuid(mapaDesenho.get("uuid").toString());
							desenho.setUuidSubstituido(mapaDesenho.get("uuidSubstituido")!=null?mapaDesenho.get("uuidSubstituido").toString():null);
							
							Map<String, Object> familiaMapa = (Map<String, Object>) mapaDesenho.get("familia");
							FamiliaProduto familia = new FamiliaProduto();
							familia.setId(Long.valueOf(familiaMapa.get("id")!=null?familiaMapa.get("id").toString():null));
							familia.setNome(familiaMapa.get("nome")!=null?familiaMapa.get("nome").toString():null);
							desenho.setFamilia(familia);
	
							this.desenhos.add(desenho);
						}
					} else if (((List<Object>)var.getValue()).iterator().next() instanceof Desenho) {
						this.desenhos = (List<Desenho>)var.getValue();
					}
				}
				
			} else if (var.getName().equals("descricao")) {
				this.setDescricao(var.getValue().toString());
				
			} else if (var.getName().equals("revisor1")) {
				this.setRevisor1(var.getValue().toString());
				
			} else if (var.getName().equals("emailRevisor1")) {
				this.setEmailRevisor1(var.getValue().toString());
			
			} else if (var.getName().equals("emailRevisor2")) {
				this.setEmailRevisor2(var.getValue().toString());
				
			} else if (var.getName().equals("revisor2")) {
				this.setRevisor2(var.getValue().toString());
				
			} else if (var.getName().equals("aprovador")) {
				this.setAprovador(var.getValue().toString());
				
			} else if (var.getName().equals("emailAprovador")) {
				this.setEmailAprovador(var.getValue().toString());
				
			} else if (var.getName().equals("emailCriador")) {
				this.setEmailCriador(var.getValue().toString());
				
			} else if (var.getName().equals("arquivo")) {
				this.arquivo = new Arquivo();
				if (var.getValue() instanceof Map) {
					Map<String, Object> params =  (Map<String, Object>)var.getValue();
					this.arquivo.setUuid(params.get("arquivo")!=null?params.get("arquivo").toString():null);
				} else if (var.getValue() instanceof Arquivo) {
					this.arquivo = (Arquivo)var.getValue();
				}
				
			} else if (var.getName().equals("gruposNotificar")) {
				this.setGruposNotificar(var.getValue()!=null?(List<String>)var.getValue():null);
			}
		}
	}

	public Map<String, Object> converterVariaveisProcessoParaMapaVariaveis() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dataInicial", this.getDataInicial());
		params.put("sequencial", this.getSequencial());
		params.put("ano", this.getAno());
		params.put("tipoDocumento", this.getTipoDocumento());
		params.put("tipoDocumentoId", this.getTipoDocumento().getId());
		params.put("origem", this.getOrigem());
		params.put("origemId", this.getOrigem().getId());
		params.put("desenhos", this.desenhos);
		params.put("arquivo", this.arquivo);
		params.put("descricao", this.getDescricao());
		params.put("revisor1", this.getRevisor1());
		params.put("emailRevisor1", this.getEmailRevisor1());
		params.put("revisor2", this.getRevisor2());
		params.put("emailRevisor2", this.getEmailRevisor2());
		params.put("aprovador", this.getAprovador());
		params.put("emailAprovador", this.getEmailAprovador());
		params.put("emailCriador", this.getEmailCriador());
		params.put("gruposNotificar", this.getGruposNotificar());
		params.put("criador", this.getCriador());
		return params;
	}
	
	public List<Variaveis> converterVariaveisProcessoParaVariaveisActiviti() {
		List<Variaveis> ListaVariaveis = new ArrayList<Variaveis>();

		Variaveis var = new Variaveis();
		var.setName("dataInicial");
		var.setValue(this.getDataInicial());
		ListaVariaveis.add(var);

		var = new Variaveis();
		var.setName("sequencial");
		var.setValue(this.getSequencial().toString());
		ListaVariaveis.add(var);

		var = new Variaveis();
		var.setName("ano");
		var.setValue(this.getAno().toString());
		ListaVariaveis.add(var);

		var = new Variaveis();
		var.setName("tipoDocumento");
		var.setValue(this.getTipoDocumento());
		ListaVariaveis.add(var);

		var = new Variaveis();
		var.setName("tipoDocumentoId");
		var.setValue(this.getTipoDocumento().getId());
		ListaVariaveis.add(var);

		var = new Variaveis();
		var.setName("origem");
		var.setValue(this.getOrigem());
		ListaVariaveis.add(var);
		
		var = new Variaveis();
		var.setName("origemId");
		var.setValue(this.getOrigem().getId());
		ListaVariaveis.add(var);
		
		var = new Variaveis();
		var.setName("desenhos");
		var.setValue(this.desenhos);
		ListaVariaveis.add(var);
		
		var = new Variaveis();
		var.setName("arquivo");
		var.setValue(this.arquivo);
		ListaVariaveis.add(var);	

		var = new Variaveis();
		var.setName("descricao");
		var.setValue(this.getDescricao());
		ListaVariaveis.add(var);
		
		var = new Variaveis();
		var.setName("revisor1");
		var.setValue(this.getRevisor1());
		ListaVariaveis.add(var);
		
		var = new Variaveis();
		var.setName("emailRevisor1");
		var.setValue(this.getEmailRevisor1());
		ListaVariaveis.add(var);

		var = new Variaveis();
		var.setName("revisor2");
		var.setValue(this.getRevisor2());
		ListaVariaveis.add(var);
		
		var = new Variaveis();
		var.setName("emailRevisor2");
		var.setValue(this.getEmailRevisor2());
		ListaVariaveis.add(var);
		
		var = new Variaveis();
		var.setName("aprovador");
		var.setValue(this.getAprovador());
		ListaVariaveis.add(var);
		
		var = new Variaveis();
		var.setName("emailAprovador");
		var.setValue(this.getEmailAprovador());
		ListaVariaveis.add(var);
	
		var = new Variaveis();
		var.setName("emailCriador");
		var.setValue(this.getEmailCriador());
		ListaVariaveis.add(var);
		
		var = new Variaveis();
		var.setName("criador");
		var.setValue(this.getCriador());
		ListaVariaveis.add(var);
		
		var = new Variaveis();
		var.setName("gruposNotificar");
		var.setValue(this.getGruposNotificar());
		ListaVariaveis.add(var);
		
		
		return ListaVariaveis;
	}
}
