package br.com.ecm.activiti.testes;

import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import br.com.ecm.activiti.dominio.ProcessoInstanciaEntidade;
import br.com.ecm.activiti.dominio.Variaveis;

@ContextConfiguration(locations = { "classpath:applicationContext-teste.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ProcessoTeste {
	
	@Autowired
	private ClassPathResource processo;
	
	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	@Autowired
	@Qualifier("restTemplateActiviti")
	private RestTemplate restTemplate;
	
	@Autowired
	@Qualifier("activitiProperties")
	private Properties properties;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void startProcess() throws Exception {
		RepositoryService repositoryService = activitiRule
				.getRepositoryService();
		repositoryService
				.createDeployment()
				.addInputStream("fluxoAprovacao.bpmn20.xml",
						new FileInputStream(processo.toString())).deploy();
		
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("username", "kermit");

		ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey("fluxoAprovacao", variableMap);
		
		Assert.assertNotNull(processInstance.getId());

		System.out.println("id " + processInstance.getId() + " "
				+ processInstance.getProcessDefinitionId());

		System.out.println("Number of process instances: "
				+ runtimeService.createProcessInstanceQuery().count());

		ProcessEngines.getProcessEngineInfos();
	}
	
	@Test
	public void getProcessosByVariables() {
		List<ProcessInstance> result = runtimeService.createProcessInstanceQuery().variableValueEquals("origemId", "1").list();
		Assert.assertNotNull(result);
	}
	
	@Test
	public void getProcessos() {
		String uri = properties.getProperty("activiti.server.processos");
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		ProcessoInstanciaEntidade p = ProcessoInstanciaEntidade.fromJsonToObject(response.getBody());
	}
	
	@Ignore
	@Test
	public void getTarefasUsuario() {
		String uri = MessageFormat.format(properties.getProperty("activiti.server.usuario.tarefas"), "kermit");
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
	}
	
	@Ignore
	@Test
	public void completarTarefa() {
		String uri = MessageFormat.format(properties.getProperty("activiti.server.usuario.tarefas.completar"), "");
		String json = "\"action\" : \"resolve\"";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set( "Content-type", "application/json");
		
		HttpEntity<String> request = new HttpEntity<String>(json, headers);
//		ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);
		
		Map<String, Object> taskVariables = new HashMap<String, Object>();
		taskVariables.put("vacationApproved", "false");
		taskVariables.put("managerMotivation", "Apenas Teste e nada mais!!!");
		
		restTemplate.put(uri, request, taskVariables);
//		ResponseEntity<String> response = restTemplate.execute(uri, HttpMethod.PUT, request, String.class, null);
		
	}
	
	@Ignore
	@Test
	public void startProcesso() {
		//{\"processDefinitionKey\":\"MailSender\"}
		String json = "{\"processDefinitionId\":\"processoPrincipal:2:16275\"}";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set( "Content-type", "application/json");
		
		HttpEntity<String> request = new HttpEntity<String>(null, headers);
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("processDefinitionId", "processoPrincipal:2:16275");
		
		//processoPrincipal:2:16275
		String uri = "http://170.10.200.224:8080/activiti-rest/service/runtime/process-instances";

		ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class, params);
		
		System.out.println(response.getBody());
		
	}
	
	@Ignore
	@Test
	public void getVariables() {
		String uri = MessageFormat.format(properties.getProperty("activiti.server.processo.variaveis"), 947);
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		List<Variaveis> var = Variaveis.fromJsonArrayToVariaveis(response.getBody());
	}
}
