package br.fucapi.bpms.web.teste;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.client.HttpClientErrorException;

import br.fucapi.bpms.activiti.dominio.ProcessoDefinicao;
import br.fucapi.bpms.activiti.dominio.ProcessoInstancia;
import br.fucapi.bpms.activiti.dominio.Variaveis;
import br.fucapi.bpms.activiti.servico.ActivitiServico;
import br.fucapi.bpms.alfresco.dominio.Usuario;
import br.fucapi.bpms.alfresco.servico.AlfrescoServico;
import br.fucapi.bpms.web.dominio.Desenho;
import br.fucapi.bpms.web.dominio.FamiliaProduto;
import br.fucapi.bpms.web.dominio.Origem;
import br.fucapi.bpms.web.dominio.TipoDocumento;
import br.fucapi.bpms.web.dominio.TipoModificacao;
import br.fucapi.bpms.web.dominio.VariaveisProcesso;

import com.lowagie.text.DocumentException;

public class TesteWebMain {

	private AlfrescoServico alfrescoServico;
	
	private ActivitiServico activitiServico;
	
	public AlfrescoServico getAlfrescoServico() {
		return alfrescoServico;
	}

	public void setAlfrescoServico(AlfrescoServico alfrescoServico) {
		this.alfrescoServico = alfrescoServico;
	}
	
	public ActivitiServico getActivitiServico() {
		return activitiServico;
	}

	public void setActivitiServico(ActivitiServico activitiServico) {
		this.activitiServico = activitiServico;
	}
	
	public TesteWebMain() {
		ApplicationContext app = new ClassPathXmlApplicationContext(
				"classpath*:**/META-INF/spring/applicationContext.xml");
		
		alfrescoServico = (AlfrescoServico)app.getBean("alfrescoServicoImpl");
		
		activitiServico = (ActivitiServico)app.getBean("activitiServicoImpl");
	}
	
	public static void main(String[] args) throws org.dom4j.DocumentException {
		TesteWebMain teste = new TesteWebMain();
		Usuario usuario = null;
		try {
			usuario = teste.login("admin", "admin");
			System.out.println(usuario.getTicket()); 
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		String parentUUID = "9b9a11aa-3f64-4d1b-b2db-efaa30391392";
//		String parentUUID = "6f00ba2e-86c0-46f7-b062-d229ca78f8e8";
		
		try {
			teste.alfrescoServico.anexarArquivo(parentUUID, "04_2013", null, "apenas um teste", usuario.getTicket(), new File("C:/WSF_Client.pdf"));
			
			teste.alfrescoServico.anexarArquivo(null, null, "5c4bb8d1-4280-46d8-b697-4625294c8d5a", "apenas um teste", usuario.getTicket(), new File("C:/WSF_Client.pdf"));
			
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Usuario login(String usuario, String senha) throws HttpClientErrorException, DocumentException, org.dom4j.DocumentException {
		 return alfrescoServico.autenticarUsuario(usuario, senha);
	}
	
	public void iniciarProcesso() {
		
		ProcessoInstancia processoStart = new ProcessoInstancia();
		processoStart.setProcessDefinitionId("");
		processoStart.setBusinessKey("19/2013");

// PROCESSO DEFINICAO		
		ProcessoDefinicao pd = new ProcessoDefinicao();
		pd.setId("fluxoAprovacao:1:304"); 
		pd.setName("FLUXO DE APROVAÇÃO"); 
		pd.setKey("fluxoAprovacao");
		pd.setDeploymentId(301);

// DESENHOS		
		Desenho d = new Desenho();
		d.setComplemento("complemento teste");
//		d.setTipo("designacao teste");
		d.setFile(null);
		
		FamiliaProduto fp = new FamiliaProduto();
		fp.setId(1l); fp.setNome("Familia teste");
		d.setFamilia(fp);
		
		d.setTipoModificacao(TipoModificacao.ALTERACAO);
		d.setUuid("workspace://SpacesStore/670e13ff-cdb4-4e4c-9769-680bcb87d56b");
		
		List<Desenho> listaDesenhos = new ArrayList<Desenho>();
		listaDesenhos.add(d);

// ORIGEM		
		Origem o = new Origem(); o.setId(1l);

// TIPO DOCUMENTO		
		TipoDocumento t = new TipoDocumento();
		t.setId(1l);

// REVISOR e APROVADOR
		String revisor ="claudemirferreira";
		
		String aprovador = "claudemirferreira";
		
		
// VARIAVEIS PROCESSO		
		VariaveisProcesso variaveisProcesso = new VariaveisProcesso();
		variaveisProcesso.setAno(2013);
		variaveisProcesso.setSequencial(19);
		variaveisProcesso.setDataInicial("31/09/2013 - 11:29");
		variaveisProcesso.setDescricao("Apenas um teste");
		variaveisProcesso.setDesenhos(listaDesenhos);
		variaveisProcesso.setOrigem(o);
		variaveisProcesso.setTipoDocumento(t);
		variaveisProcesso.setRevisor1(revisor);
		variaveisProcesso.setRevisor2(revisor);
		variaveisProcesso.setAprovador(aprovador);

		List<Variaveis> listaVariaveis = variaveisProcesso.converterVariaveisProcessoParaVariaveisActiviti();
		
		processoStart.setProcessoDefinicao(pd);
		processoStart.setProcessDefinitionId("fluxoAprovacao:3:1904");
		processoStart.setVariaveisProcesso(variaveisProcesso);
		processoStart.setVariables(listaVariaveis);
		
		String json = processoStart.toJson();
		
		activitiServico.iniciarInstanciaProcesso(json);
	}
	
	public void pesquisarProcessoFiltro() {
		
		TipoDocumento tipoDocumento = new TipoDocumento();
		tipoDocumento.setId(1l);
		tipoDocumento.setNome("ORDEM DE MODIFICA");
		tipoDocumento.setSigla("OM");
		
		Map<String, Object> filtro = new HashMap<String, Object>();
//		filtro.put("origemId", "3");
		filtro.put("tipoDocumento", tipoDocumento);
//		filtro.put("sequencial", "207");
//		filtro.put("ano", "2013");
//		filtro.put("businessKey", "206/2013");
//		filtro.put("uuid", "workspace://SpacesStore/5ee65cf9-da1a-4571-b6bc-4f4a78609bdc");
		
/*		List<ProcessoInstancia> listaProcessos =  activitiServico.getHistoricoProcessosFiltroVariaveis(filtro, "PENDENTE");		
		for (ProcessoInstancia processoInstancia : listaProcessos) {
			
		}
*/
	}
	
	public void getTreeExplorer() {
		/*
		 * workspace:SpacesStore/i/d6943520-ef94-476e-875d-cf8bc5ca1d01
		 * pasta raiz de Eng. Produtos
		 */
		
		try {
			alfrescoServico.getExplorerSiteTree("workspace:SpacesStore/i/d6943520-ef94-476e-875d-cf8bc5ca1d01");
		} catch (org.dom4j.DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
