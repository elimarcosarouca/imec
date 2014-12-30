package br.fucapi.bpms.activiti.teste;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.history.NativeHistoricVariableInstanceQuery;
import org.activiti.engine.impl.HistoricProcessInstanceQueryImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.fucapi.bpms.activiti.dominio.ProcessoInstancia;
import br.fucapi.bpms.activiti.dominio.ProcessoInstanciaEntidade;
import br.fucapi.bpms.activiti.dominio.TarefaInstancia;
import br.fucapi.bpms.activiti.dominio.TarefaInstanciaEntidade;
import br.fucapi.bpms.activiti.dominio.Variaveis;

public class TesteMain {
	
	private static RestTemplate restTemplate;
	private static Properties properties;
	private static ApplicationContext app;
	
	private static RuntimeService runtimeService;
	private static HistoryService historyService;
	private static TaskService taskService;
	
	public static void main(String[] args) {
		
		app = new ClassPathXmlApplicationContext(
				"META-INF/spring/applicationContext.xml");
		
		restTemplate = (RestTemplate) app.getBean("restTemplateActiviti");
		properties = (Properties) app.getBean("activitiProperties");
		
		runtimeService = (RuntimeService) app.getBean("runtimeService");
		historyService = (HistoryService) app.getBean("historyService");
		
		taskService = (TaskService) app.getBean("taskService");
		
		String retorno = null;
	
//		retorno = getTarefasUsuario();
//		retorno = completarTarefa("12588");
//		retorno = startProcesso();
//		getProcessos();
		
//		System.out.println(retorno);
		
//		getProcessosByVariables();
//		postProcessosByVariables();
//		completarTarefa("101");
//		getProcessos();
//		getVariablesFromHistoricProcess();
		getHistoricProcessByVariables();
		
//		getTarefasDeProcesso("");
//		getVariaveisDeTarefa("");
		
//		getProcessos();
		
//		parseTipoDocumentoDeserializable();
	}
	
	public static void parseTipoDocumentoDeserializable() {
		String uri = "http://170.10.84.245:9090/activiti-rest/service/runtime/process-instances/2764/variables/tipoDocumentoId/data";
		ResponseEntity<byte[]> response = restTemplate.getForEntity(uri, byte[].class);
		
		InputStream is = new ByteArrayInputStream(response.getBody());
		
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(is);
			Object o = in.readObject();
			Map<String, Object> mapa = (Map<String, Object>)o;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		

//		FUNCIONAL
		/* HttpClient client = new HttpClient();
	     HttpMethod get = new GetMethod("http://170.10.84.245:9090/activiti-rest/service/runtime/process-instances/2764/variables/tipoDocumentoId/data");
	     get.setRequestHeader("Accept", "application/x-java-serialized-object");
	     try {
	    	 Credentials defaultcreds = new UsernamePasswordCredentials("kermit", "kermit");
	    	 client.getState().setCredentials(new AuthScope("170.10.84.245", 9090, AuthScope.ANY_REALM), defaultcreds); 
	    	
	    	 client.executeMethod(get);
	    	 System.out.println(get.getStatusCode());
	    	 ObjectInputStream in = new ObjectInputStream(get.getResponseBodyAsStream());
	    	 Object o = in.readObject();
	    	 
	    	 Map<String, Object> mapa = (Map<String, Object>)o;
	    	 
	    	 TipoDocumento t = new TipoDocumento();
	    	 t.setNome(mapa.get("nome").toString());
	    	 
	    	 System.out.println(t.getNome());
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}*/
       
	}
	
	public static List<TarefaInstancia> getTarefasDeProcesso(String idProcesso) {
		String uri = MessageFormat.format(properties.getProperty("activiti.server.tarefas.processoId"), "426");
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

		TarefaInstanciaEntidade tarefaEntidade = TarefaInstanciaEntidade.fromJsonToTarefaInstanciaEntidade(response.getBody());
		
		for (TarefaInstancia tarefa : tarefaEntidade.getData()) {
			tarefa.setVariables(getVariaveisDeTarefa(tarefa.getId()));
		}
		
		return tarefaEntidade.getData();
	}
	
	public static List<Variaveis> getVariaveisDeTarefa(String id) {
		String uri = MessageFormat.format(properties.getProperty("activiti.server.tarefa.variaveis"), id);
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		
		 return Variaveis.fromJsonArrayToVariaveis(response.getBody());
	}
	
	public static void getVariablesFromHistoricProcess() {
		String uri = "http://170.10.84.245:9090/activiti-rest/service/history/historic-process-instances/305/variables";
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		
		System.out.println(response.getBody());
	}
	
	public static void postProcessosByVariables() {
		
		String uri = "http://170.10.84.245:9090/activiti-rest/service/query/historic-process-instances";
		
		String json = "{\"variables\" : [{\"value\":\"1\"}]}";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set( "Content-type", "application/json");
		
		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);
	}
	
	public static void getHistoricProcessByVariables() {
		
		List<Variaveis> listaVariaveis = null;
		Map<String, Object> var = new HashMap<String, Object>();
//		var.put("aprovador", "claudemirferreira");
//		var.put("sequencial", 212);
//		var.put("ano", 2013);
		var.put("tipoDocumento", 1l); 
//		var.put("sequencial", null);
//		var.put("origem", "1");
		
		HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();
		
		for (String s : var.keySet()) {
			query.variableValueEquals(s, var.get(s));
		}

		List<HistoricProcessInstance> result = query.list();
		
		List<ProcessoInstancia> listaProcessos = new ArrayList<ProcessoInstancia>();
		List<ProcessoInstancia> listaProcessosPendentes = new ArrayList<ProcessoInstancia>();
		List<ProcessoInstancia> listaProcessosConcluidos = new ArrayList<ProcessoInstancia>();
		
		if (!result.isEmpty()) {
			
			ProcessoInstancia processoInstancia = null;
			
			for (HistoricProcessInstance historicProcess : result) {
				processoInstancia = new ProcessoInstancia();
				processoInstancia.parseHistoricProcessToProcessoInstancia(historicProcess);
				
				listaVariaveis = getVariaveisAPIExplorer(processoInstancia.getId());
				processoInstancia.setVariables(listaVariaveis);
				listaProcessos.add(processoInstancia);
				
				if (historicProcess.getEndTime()!=null) {
					listaProcessosConcluidos.add(processoInstancia);
				} else {
					listaProcessosPendentes.add(processoInstancia);
				}
			}
		}

	}
	
	public static List<Variaveis> getVariaveisAPIExplorer(String idProcesso) {
		List<HistoricVariableInstance> listaVariaveis = historyService.createHistoricVariableInstanceQuery().processInstanceId(idProcesso).list();
		if (listaVariaveis != null)
			return setVariavesFromHistoricVariables(listaVariaveis);
		return null;
	}

	
	private static List<Variaveis> setVariavesFromHistoricVariables(List<HistoricVariableInstance> historicVariablesInstance) {
		List<Variaveis> variaveis = new ArrayList<Variaveis>();
		Variaveis var = null;
		for (HistoricVariableInstance hv : historicVariablesInstance) {
			if (hv.getValue() != null) {
				var = new Variaveis();
				var.setName(hv.getVariableName());
				var.setValue(hv.getValue());
				variaveis.add(var);
			}
		}
		
		return variaveis;
	}

	
	public static void getProcessosByVariables() {
		
		Map<String, String> var = new HashMap<String, String>();
//		var.put("origemId", null; var.put("tipoDocumentoId", null); 
		var.put("ano", "2013");
		var.put("sequencial", "019");
//		var.put("businessKey", "019\\/2013");
		
		ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();
		
		for (String s : var.keySet()) {
			query.variableValueEquals(s, var.get(s));
		}
		
		List<ProcessInstance> result = query.list();
		
		List<ProcessoInstancia> listaProcessoAux = new ArrayList<ProcessoInstancia>();
		
		if (!result.isEmpty()) {
			String variaveisJson = null;
			for (ProcessInstance processInstance : result) {
//				variaveisJson = getVariaveisProcesso(processInstance.getId());
//				List<Variaveis> variaveis = Variaveis.fromJsonArrayToVariaveis(variaveisJson);
				ExecutionEntity executionEntity = (ExecutionEntity)processInstance;
				ProcessoInstancia processo = new ProcessoInstancia();
				processo.parseExecutionEntityToProcessoInstancia(executionEntity);
//				processo.setVariables(variaveis);
				
				listaProcessoAux.add(processo);
			}
		}
		
	}
	
	public static List<ProcessoInstancia> getProcessos() {
		String uri = properties.getProperty("activiti.server.processos");
		
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		
		ProcessoInstanciaEntidade processoEntidade = ProcessoInstanciaEntidade.fromJsonToObject(response.getBody());
		
		for (ProcessoInstancia processoInstancia : processoEntidade.getData()) {
//			processoInstancia.setVariables(getVariaveisProcesso(processoInstancia.getId()));
		}
		
		return processoEntidade.getData();
	}
	
	public static String getTarefasUsuario() {
		String uri = MessageFormat.format(properties.getProperty("activiti.server.tarefas.usuario"), "kermit");
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		
		return response.getBody();
	}
	
	public static String completarTarefa(String idTarefa) {
		
		// Teste
		List<TarefaInstancia> listaTarefas = getTarefasDeProcesso("401");
		
		String uri = MessageFormat.format(properties.getProperty("activiti.server.tarefa"), listaTarefas.iterator().next().getId());
		
		String json = "{\"action\" : \"complete\"}";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set( "Content-type", "application/json");
		
		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);
		
		return response.getStatusCode().toString();
	}
	
	public static String startProcesso() {
		
		// Funcional
//		String json = "{" +
//				"\"processDefinitionId\":\"fluxoAprovacao:3:1904\"," +
//				"\"businessKey\":\"014/2103\"," +
//				"\"variables\": [" +
//					"{\"name\":\"tipoDocumentoId\", \"value\":\"1\"}" +
//					",{\"name\":\"origemId\", \"value\":\"1\"}" +
//					",{\"name\":\"descricao\", \"value\":\"Exemplo de start de processo\"}" +
//					",{\"name\":\"revisor\", \"value\":\"claudemirferreira\"}" +
//					",{\"name\":\"aprovador\", \"value\":\"claudemirferreira\"}" +
//				"]}";
		
		String json = "{" +
				"\"businessKey\":\"019/2013\"," +
				"\"processDefinitionId\":\"fluxoAprovacao:1:2804\"," +
				"\"variables\":[" +
				"{\"name\":\"dataInicial\",\"value\":\"31/09/2013 - 11:29\"}," +
				"{\"name\":\"sequencial\",\"value\":\"73\"}," +
				"{\"name\":\"ano\",\"value\":\"2013\"}," +
				"{\"name\":\"tipoDocumentoId\",\"value\":{\"id\":1, \"nome\":\"Ordem Melhoria\", \"sigla\":\"GWA\"}}," +
				"{\"name\":\"origemId\",\"value\":\"1\"}," +
				"{\"name\":\"desenhos\",\"value\":null}," +
				"{\"name\":\"descricao\",\"value\":\"Apenas um teste\"}," +
				"{\"name\":\"revisor\",\"value\":\"claudemirferreira\"}," +
				"{\"name\":\"aprovador\",\"value\":\"claudemirferreira\"}" +
				"]" +
				"}";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set( "Content-type", "application/json");
		
		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		
		String uri = properties.getProperty("activiti.server.processos");
		ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);
		System.out.println(response.getBody());
		
		return response.getBody();
	}
	
	public static List<Variaveis> getVariaveisProcesso(String idProcesso) {
		String uri = MessageFormat.format(properties.getProperty("activiti.server.processo.variaveis"), idProcesso);
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		
		return Variaveis.fromJsonArrayToVariaveisList(response.getBody());
	}
	
	public static void updateVariables() {
//		PUT runtime/process-instances/{processInstanceId}/variables
		
		Map<String, Object> mapa = new HashMap<String, Object>();
		
		String uri = "http://170.10.84.245:9090/activiti-rest/service/runtime/process-instances/701/variables";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set( "Content-type", "multipart/form-data");
		
		HttpEntity<String> request = new HttpEntity<String>(null, headers);
		
		restTemplate.put(uri, request, mapa);
				
	}
	
/*	public static Map<String, Object> getVariaveisProcesso(String idProcesso) {
		String uri = MessageFormat.format(properties.getProperty("activiti.server.processo.variaveis"), idProcesso);
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		
		return Variaveis.fromJsonArrayToVariaveisMap(response.getBody());
	}
*/}
