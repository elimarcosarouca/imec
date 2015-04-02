package br.fucapi.bpms.activiti.servico.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.fucapi.bpms.activiti.dominio.ProcessoDefinicao;
import br.fucapi.bpms.activiti.dominio.ProcessoDefinicaoEntidade;
import br.fucapi.bpms.activiti.dominio.ProcessoInstancia;
import br.fucapi.bpms.activiti.dominio.ProcessoInstanciaEntidade;
import br.fucapi.bpms.activiti.dominio.TarefaInstancia;
import br.fucapi.bpms.activiti.dominio.TarefaInstanciaEntidade;
import br.fucapi.bpms.activiti.dominio.Variaveis;
import br.fucapi.bpms.activiti.servico.ActivitiServico;

@Service("activitiServicoImpl")
public class ActivitiServicoImpl implements ActivitiServico, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2691466800072077335L;

	private final String CONTENT_TYPE = "application/json; charset=utf-8";

	@Autowired
	@Qualifier("restTemplateActiviti")
	private RestTemplate restTemplate;

	@Autowired
	private Properties activitiProperties;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private ManagementService managementService;

	@Autowired
	private IdentityService identityService;

	public List<TarefaInstancia> getTarefasUsuario(String userName) {

		String uri = MessageFormat.format(activitiProperties
				.getProperty("activiti.server.tarefas.usuario"), userName, 500);

		System.out.println(uri);

		ResponseEntity<String> response = restTemplate.getForEntity(uri,
				String.class);

		TarefaInstanciaEntidade tarefaInstanciaEntidade = TarefaInstanciaEntidade
				.fromJsonToTarefaInstanciaEntidade(response.getBody());

		for (TarefaInstancia tarefa : tarefaInstanciaEntidade.getData()) {
			if (tarefa.getProcessInstanceId() != null)
				tarefa.setVariables(this.getVariaveisAPIExplorer(tarefa
						.getProcessInstanceId()));
		}

		return tarefaInstanciaEntidade.getData();
	}

	public List<TarefaInstancia> getTodasTarefas() {

		String uri = MessageFormat.format(
				activitiProperties.getProperty("activiti.server.tarefa.lista"),
				500);
		ResponseEntity<String> response = restTemplate.getForEntity(uri,
				String.class);

		TarefaInstanciaEntidade tarefaInstanciaEntidade = TarefaInstanciaEntidade
				.fromJsonToTarefaInstanciaEntidade(response.getBody());

		for (TarefaInstancia tarefa : tarefaInstanciaEntidade.getData()) {
			// TODO VALIDARO IF COM O ELIMARCOS
			if (tarefa.getProcessInstanceId() != null)
				tarefa.setVariables(this.getVariaveisAPIExplorer(tarefa
						.getProcessInstanceId()));
		}

		return tarefaInstanciaEntidade.getData();
	}

	public List<TarefaInstancia> getHistoricoTarefasProcessoInstancia(
			String idProcesso) {

		String uri = activitiProperties
				.getProperty("activiti.server.query.tarefas.processoId");

		String json = "{\"processInstanceId\": \"" + idProcesso + "\" }";
		System.out.println("uri =" + uri);
		System.out.println("json =" + json);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", this.CONTENT_TYPE);

		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(uri,
				request, String.class);

		System.out.println(response.getStatusCode().toString());

		TarefaInstanciaEntidade tarefaEntidade = TarefaInstanciaEntidade
				.fromJsonToTarefaInstanciaEntidade(response.getBody());

		List<TarefaInstancia> listaTarefaResult = new ArrayList<TarefaInstancia>();

		if (tarefaEntidade.getData() != null
				&& tarefaEntidade.getData().size() != 0) {
			for (TarefaInstancia tarefa : tarefaEntidade.getData()) {
				tarefa = getTarefaInstanciaPorIdIncludeVariaveis(
						tarefa.getId(), true, false);
				listaTarefaResult.add(tarefa);
				System.out.println("entrou");
			}
			// Solucao temporaria para resolver pesquisa de variaveis de
			// processo quando a mesma ja estiver finalizada
		} else {
			TarefaInstancia tarefa = new TarefaInstancia();
			tarefa.setVariables(this.getVariaveisAPIExplorer(idProcesso));
			listaTarefaResult.add(tarefa);
		}

		return listaTarefaResult;

	}

	public List<TarefaInstancia> getTarefasProcessoInstancia(String idProcesso) {

		String uri = MessageFormat.format(activitiProperties
				.getProperty("activiti.server.tarefas.processoId"), idProcesso);
		ResponseEntity<String> response = restTemplate.getForEntity(uri,
				String.class);

		TarefaInstanciaEntidade tarefaEntidade = TarefaInstanciaEntidade
				.fromJsonToTarefaInstanciaEntidade(response.getBody());

		if (tarefaEntidade.getData() != null
				&& tarefaEntidade.getData().size() != 0) {
			for (TarefaInstancia tarefa : tarefaEntidade.getData()) {
				// tarefa.setVariables(this.getVariaveisTarefa(tarefa.getId()));
				tarefa.setVariables(this.getVariaveisAPIExplorer(tarefa
						.getProcessInstanceId()));
			}
			// Solucao temporaria para resolver pesquisa de variaveis de
			// processo quando a mesma ja estiver finalizada
		} else {
			TarefaInstancia tarefa = new TarefaInstancia();
			tarefa.setVariables(this.getVariaveisAPIExplorer(idProcesso));
			tarefaEntidade.getData().add(tarefa);
		}

		return tarefaEntidade.getData();
	}

	public List<Variaveis> getVariaveisAPIExplorer(String idProcess) {
		List<HistoricVariableInstance> listaVariaveis = historyService
				.createHistoricVariableInstanceQuery()
				.processInstanceId(idProcess).list();

		if (listaVariaveis != null)
			return this.setVariavesFromHistoricVariables(listaVariaveis);

		return null;
	}

	public List<Variaveis> getVariaveisTarefa(String idTarefa) {
		String uri = MessageFormat.format(activitiProperties
				.getProperty("activiti.server.tarefa.variaveis"), idTarefa);
		ResponseEntity<String> response = restTemplate.getForEntity(uri,
				String.class);

		return Variaveis.fromJsonArrayToVariaveis(response.getBody());
	}

	public TarefaInstancia getTarefaInstanciaPorIdIncludeVariaveis(
			String idTarefa, boolean includeTaskLocalVariables,
			boolean includeProcessVariables) {
		String uri = MessageFormat.format(activitiProperties
				.getProperty("activiti.server.historico.tarefa.variaveis"),
				idTarefa, includeTaskLocalVariables, includeProcessVariables);

		System.out.println(uri);
		ResponseEntity<String> response = restTemplate.getForEntity(uri,
				String.class);

		TarefaInstanciaEntidade tarefaInstanciaEntidade = new TarefaInstanciaEntidade();

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			tarefaInstanciaEntidade = objectMapper.readValue(
					response.getBody(), TarefaInstanciaEntidade.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return tarefaInstanciaEntidade.getData().get(0);
	}

	public String assinarTarefa(String idTarefa, String userName) {
		String uri = MessageFormat.format(
				activitiProperties.getProperty("activiti.server.tarefa"),
				idTarefa);

		String json = "{\"action\" : \"claim\"," + "\"assignee\":\"" + userName
				+ "\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", this.CONTENT_TYPE);

		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(uri,
				request, String.class);

		return response.getStatusCode().toString();
	}

	public String alterarExecutor(String idTarefa, String userName) {

		String uri = MessageFormat.format(
				activitiProperties.getProperty("activiti.server.tarefa"),
				idTarefa);

		String json = "{\"action\" : \"delegate\"," + "\"assignee\":\""
				+ userName + "\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", this.CONTENT_TYPE);

		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(uri,
				request, String.class);

		return response.getStatusCode().toString();

	}

	public String completarTarefa(String idTarefa, String jsonVariaveis) {

		/*
		 * variavel jsonVariaveis [EXEMPLO] =
		 * "{"name":"status", "value":true},{"name":"parecer", "value":null}"
		 */

		String uri = MessageFormat.format(
				activitiProperties.getProperty("activiti.server.tarefa"),
				idTarefa);

		String json = "{\"action\" : \"complete\"," + "\"variables\":["
				+ jsonVariaveis + "]" + "}";

		System.out.println("uri " + uri);
		System.out.println("json " + json);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", "application/json; charset=utf-8");

		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(uri,
				request, String.class);

		return response.getStatusCode().toString();
	}

	public ProcessoDefinicao getProcessoDefinicao(String idProcessoDefinicao) {
		String uri = MessageFormat.format(activitiProperties
				.getProperty("activiti.server.processo.modelo.id"),
				idProcessoDefinicao);

		ResponseEntity<String> response = restTemplate.getForEntity(uri,
				String.class);

		return ProcessoDefinicao.fromJsonToObject(response.getBody());
	}

	public List<ProcessoDefinicao> getProcessosDefinicao() {
		String uri = activitiProperties
				.getProperty("activiti.server.processo.modelo");
		ResponseEntity<String> response = restTemplate.getForEntity(uri,
				String.class);

		ProcessoDefinicaoEntidade processoDefinicaoEntidade = ProcessoDefinicaoEntidade
				.fromJsonToObject(response.getBody());

		return processoDefinicaoEntidade.getData();
	}

	public List<ProcessoInstancia> getProcessosInstancia() {
		/*
		 * TODO - Bloco de codigos abaixo não atende, pois o mesmo não retorna
		 * as variaveis de processo dentro de cada processo. Isso se dá devido a
		 * um bug presente na versão 5.13 do activiti.
		 * 
		 * http://forums.activiti.org/content/list-process-instances-rest-returns
		 * -empty-processvariables
		 */
		// String uri = properties.getProperty("activiti.server.processos");
		// ResponseEntity<String> response = restTemplate.getForEntity(uri,
		// String.class);
		// return response.getBody();

		// Solucao temporaria
		String uri = activitiProperties
				.getProperty("activiti.server.processos");
		ResponseEntity<String> response = restTemplate.getForEntity(uri,
				String.class);

		ProcessoInstanciaEntidade processoInstanciaEntidade = ProcessoInstanciaEntidade
				.fromJsonToObject(response.getBody());

		for (ProcessoInstancia processoInstancia : processoInstanciaEntidade
				.getData()) {
			processoInstancia.setVariables(this
					.getVariaveisProcesso(processoInstancia.getId()));
			processoInstancia.setProcessoDefinicao(this
					.getProcessoDefinicao(processoInstancia
							.getProcessDefinitionId()));
		}

		return processoInstanciaEntidade.getData();
	}

	public ProcessoInstancia getProcessosInstanciaId(String processInstanceId) {

		// Solucao temporaria
		String uri = MessageFormat.format(
				activitiProperties.getProperty("activiti.server.processo.instanciado.byid"),
				processInstanceId);
		
		ResponseEntity<String> response = restTemplate.getForEntity(uri,
				String.class);

		ProcessoInstancia processoInstancia = ProcessoInstancia
				.fromJsonToObject(response.getBody());
		
		List<Variaveis> listaVariaveis = this
				.getVariaveisAPIExplorer(processoInstancia.getId());
		
		processoInstancia.setVariables(listaVariaveis);

		return processoInstancia;
	}

	public List<ProcessoInstancia> getProcessosInstancia(
			String processDefinitionId) {
		/*
		 * TODO - Bloco de codigos abaixo não atende, pois o mesmo não retorna
		 * as variaveis de processo dentro de cada processo. Isso se dá devido a
		 * um bug presente na versão 5.13 do activiti.
		 * 
		 * http://forums.activiti.org/content/list-process-instances-rest-returns
		 * -empty-processvariables
		 */
		// String uri = properties.getProperty("activiti.server.processos");
		// ResponseEntity<String> response = restTemplate.getForEntity(uri,
		// String.class);
		// return response.getBody();

		// Solucao temporaria
		String uri = MessageFormat.format(activitiProperties
				.getProperty("activiti.server.processo.processDefinitionId"),
				processDefinitionId);
		ResponseEntity<String> response = restTemplate.getForEntity(uri,
				String.class);

		ProcessoInstanciaEntidade processoEntidade = ProcessoInstanciaEntidade
				.fromJsonToObject(response.getBody());

		for (Object obj : processoEntidade.getData()) {
			ProcessoInstancia processoInstancia = (ProcessoInstancia) obj;
			processoInstancia.setVariables(this
					.getVariaveisProcesso(processoInstancia.getId()));
		}

		return processoEntidade.getData();
	}

	public List<Variaveis> getVariaveisProcesso(String idProcesso) {
		String uri = MessageFormat.format(activitiProperties
				.getProperty("activiti.server.processo.variaveis"), idProcesso);
		ResponseEntity<String> response = restTemplate.getForEntity(uri,
				String.class);
		 return Variaveis.fromJsonArrayToVariaveis(response.getBody());
//		return Variaveis.fromJsonArrayToVariaveisList(response.getBody());
	}

	@Override
	public String atualizarVariaveis(String idProcesso, String json) {

		/*
		 * ESTRUTURA DO JSON EX:
		 * 
		 * [{\"name\" : \"nomeVariavel\", \"value\" : \"valorVariavel\"}]
		 */

		String uri = MessageFormat.format(activitiProperties
				.getProperty("activiti.server.processo.variaveis"), idProcesso);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", this.CONTENT_TYPE);

		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		restTemplate.put(uri, request);

		return HttpStatus.OK + "";
	}

	public String iniciarInstanciaProcesso(String json) {

		String uri = activitiProperties
				.getProperty("activiti.server.processos");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", this.CONTENT_TYPE);

		HttpEntity<String> request = new HttpEntity<String>(json, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(uri,
				request, String.class);

		return response.getBody();
	}

	public String iniciarInstanciaProcessoPorParametros(
			String processoDefinicaoId, String businessKey,
			Map<String, Object> paramsProcesso) {

		/* Diz a engine que o usuario autenticado e o fluxo */
		this.identityService.setAuthenticatedUserId(paramsProcesso.get(
				"solicitante").toString());

		ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey(processoDefinicaoId, businessKey,
						paramsProcesso);

		// ProcessoInstancia processoInstancia = new ProcessoInstancia();
		// processoInstancia.parseProcessInstanceActivitiToProcessoInstancia(processInstance);

		return (processInstance != null) ? processInstance.getId() : null;
	}

	// TODO atualizar para a nova versao, utilizando a API para realizar
	// pesquisa
	public List<ProcessoInstancia> getHistoricoProcessos(
			Map<String, Object> variables, String status) throws ParseException {

		StringBuffer sqlQuery = new StringBuffer(
				"SELECT a.id_, a.proc_inst_id_, a.business_key_, a.proc_def_id_, a.start_time_, a.end_time_, "
						+ " a.start_user_id_ , b.suspension_state_ duration_ FROM "
						+ managementService
								.getTableName(HistoricProcessInstance.class)
						+ " a "
						+ " left join act_ru_task b on a.id_ = b.execution_id_ WHERE a.proc_def_id_ like '%%'");

		if (status.equals("PENDENTE"))
			sqlQuery.append(" and b.suspension_state_ = 1 ");
		else if (status.equals("CANCELADO"))
			sqlQuery.append(" and b.suspension_state_ = 2 ");
		else if (status.equals("CONCLUÍDO"))
			sqlQuery.append(" and a.end_time_ is not null ");

		if (variables != null) {
			for (String s : variables.keySet()) {

				if (s.equals("solicitante")) {
					sqlQuery.append(" and start_user_id_ ='"
							+ variables.get("solicitante") + "'");
				}

				if (s.equals("tipoSolicitacao")) {
					sqlQuery.append(" and a.proc_def_id_ like '"
							+ variables.get("tipoSolicitacao") + "%'");
				}

				if (s.equals("protocolo")) {
					sqlQuery.append(" and a.business_key_ ='"
							+ variables.get("protocolo") + "'");
				}

			}
		}

		List<HistoricProcessInstance> resultHistoricProcess = historyService
				.createNativeHistoricProcessInstanceQuery()
				.sql(sqlQuery.toString()).list();

		List<Variaveis> listaVariaveis = null;
		List<ProcessoInstancia> listaProcessoInstancia = new ArrayList<ProcessoInstancia>();
		ProcessoInstancia processoInstancia = null;

		if (!resultHistoricProcess.isEmpty()) {

			for (HistoricProcessInstance process : resultHistoricProcess) {
				processoInstancia = new ProcessoInstancia();
				processoInstancia
						.parseHistoricProcessToProcessoInstancia(process);

				listaVariaveis = new ArrayList<Variaveis>();

				if (processoInstancia.getId() != null) {
					listaVariaveis = this
							.getVariaveisAPIExplorer(processoInstancia.getId());
				}

				processoInstancia.setVariables(listaVariaveis);
				listaProcessoInstancia.add(processoInstancia);
			}

		}

		return listaProcessoInstancia;

	}

	// TODO atualizar para a nova versao, utilizando a API para realizar
	// pesquisa
	public List<ProcessoInstancia> getHistoricoProcessos(
			Map<String, Object> variables, String status, String custoInicial,
			String custoFinal) throws ParseException {

		StringBuffer sqlQuery = new StringBuffer(
				"SELECT a.id_, a.proc_inst_id_, a.business_key_, a.proc_def_id_, a.start_time_, a.end_time_, "
						+ " a.start_user_id_ , b.suspension_state_ duration_ FROM "
						+ managementService
								.getTableName(HistoricProcessInstance.class)
						+ " a "
						+ " left join act_ru_task b on a.id_ = b.execution_id_ left join act_ru_variable c on c.execution_id_ = b.execution_id_ "
						+ " WHERE a.proc_def_id_ like '%%'");

		if (status.equals("PENDENTE"))
			sqlQuery.append(" and b.suspension_state_ = 1 ");
		else if (status.equals("CANCELADO"))
			sqlQuery.append(" and b.suspension_state_ = 2 ");
		else if (status.equals("CONCLUÍDO"))
			sqlQuery.append(" and b.suspension_state_ is null ");

		if (variables != null) {
			for (String s : variables.keySet()) {

				if (s.equals("solicitante")) {
					sqlQuery.append(" and start_user_id_ ='"
							+ variables.get("solicitante") + "'");
				}

				if (s.equals("tipoSolicitacao")) {
					sqlQuery.append(" and a.proc_def_id_ like '"
							+ variables.get("tipoSolicitacao") + "%'");
				}

				if (s.equals("protocolo")) {
					sqlQuery.append(" and a.business_key_ ='"
							+ variables.get("protocolo") + "'");
				}

			}
		}

		sqlQuery.append(" and c.name_ ='protocoloOrigem'");
		System.out.println(sqlQuery.toString());

		List<HistoricProcessInstance> resultHistoricProcess = historyService
				.createNativeHistoricProcessInstanceQuery()
				.sql(sqlQuery.toString()).list();

		List<Variaveis> listaVariaveis = null;
		List<ProcessoInstancia> listaProcessoInstancia = new ArrayList<ProcessoInstancia>();
		ProcessoInstancia processoInstancia = null;

		if (!resultHistoricProcess.isEmpty()) {

			for (HistoricProcessInstance process : resultHistoricProcess) {
				processoInstancia = new ProcessoInstancia();
				processoInstancia
						.parseHistoricProcessToProcessoInstancia(process);

				listaVariaveis = new ArrayList<Variaveis>();

				if (processoInstancia.getId() != null) {
					listaVariaveis = this
							.getVariaveisAPIExplorer(processoInstancia.getId());
				}

				processoInstancia.setVariables(listaVariaveis);
				listaProcessoInstancia.add(processoInstancia);
			}

		}

		return listaProcessoInstancia;

	}

	public List<ProcessoInstancia> getProcessosInstanciaFiltroVariaveis(
			Map<String, String> variables, String status) {

		ProcessInstanceQuery query = runtimeService
				.createProcessInstanceQuery();

		for (String s : variables.keySet()) {
			query.variableValueEquals(s, variables.get(s));
		}

		List<ProcessInstance> result = query.includeProcessVariables().list();
		List<ProcessoInstancia> listaProcessoInstancia = new ArrayList<ProcessoInstancia>();

		if (!result.isEmpty()) {

			ProcessoInstancia processoInstancia = null;

			for (ProcessInstance processInstance : result) {
				processoInstancia = new ProcessoInstancia();
				processoInstancia
						.parseExecutionEntityToProcessoInstancia((ExecutionEntity) processInstance);
				processoInstancia.setVariables(this
						.getVariaveisProcesso(processoInstancia.getId()));
				processoInstancia.setProcessoDefinicao(this
						.getProcessoDefinicao(processoInstancia
								.getProcessDefinitionId()));
				listaProcessoInstancia.add(processoInstancia);
			}
		}

		return listaProcessoInstancia;
	}

	public List<ProcessoInstancia> getHistoricoProcessosFiltroVariaveisOld(
			Map<String, Object> variables, String status) {
		HistoricProcessInstanceQuery query = historyService
				.createHistoricProcessInstanceQuery();
		List<Variaveis> listaVariaveis = null;

		// for (String s : variables.keySet()) {
		// query.variableValueEquals(s, variables.get(s));
		// }

		query.variableValueEquals("tipoSolicitacao", "PUBLICAR_DOCUMENTO");

		List<HistoricProcessInstance> resultHistoricProcessInstance = query
				.includeProcessVariables().list();

		List<ProcessoInstancia> listaProcessos = new ArrayList<ProcessoInstancia>();
		List<ProcessoInstancia> listaProcessosPendentes = new ArrayList<ProcessoInstancia>();
		List<ProcessoInstancia> listaProcessosConcluidos = new ArrayList<ProcessoInstancia>();

		if (!resultHistoricProcessInstance.isEmpty()) {

			ProcessoInstancia processoInstancia = null;

			for (HistoricProcessInstance historicProcess : resultHistoricProcessInstance) {
				processoInstancia = new ProcessoInstancia();
				processoInstancia
						.parseHistoricProcessToProcessoInstancia(historicProcess);

				listaVariaveis = this.getVariaveisAPIExplorer(processoInstancia
						.getId());
				processoInstancia.setVariables(listaVariaveis);
				listaProcessos.add(processoInstancia);

				if (historicProcess.getEndTime() != null) {
					listaProcessosConcluidos.add(processoInstancia);
				} else {
					listaProcessosPendentes.add(processoInstancia);
				}
			}
		}

		return listaProcessos;

		/*
		 * return (status.equals("PENDENTE")) ? listaProcessosPendentes :
		 * (status .equals("CONCLUÍDO")) ? listaProcessosConcluidos :
		 * listaProcessos;
		 */

	}

	public List<ProcessoInstancia> getHistoricoProcessosFiltroVariaveis(
			Map<String, Object> variables) {
		HistoricProcessInstanceQuery query = historyService
				.createHistoricProcessInstanceQuery();
		List<Variaveis> listaVariaveis = null;

		for (String s : variables.keySet()) {
			query.variableValueEquals(s, variables.get(s));
		}

		List<HistoricProcessInstance> resultHistoricProcessInstance = query
				.includeProcessVariables().list();

		List<ProcessoInstancia> listaProcessos = new ArrayList<ProcessoInstancia>();

		if (!resultHistoricProcessInstance.isEmpty()) {

			ProcessoInstancia processoInstancia = null;

			for (HistoricProcessInstance historicProcess : resultHistoricProcessInstance) {
				processoInstancia = new ProcessoInstancia();
				processoInstancia
						.parseHistoricProcessToProcessoInstancia(historicProcess);

				listaVariaveis = this.getVariaveisAPIExplorer(processoInstancia
						.getId());
				processoInstancia.setVariables(listaVariaveis);
				listaProcessos.add(processoInstancia);

			}
		}

		return listaProcessos;

	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getBinaryDataPorVariavelProcesso(
			String idProcesso, String variavelNome) throws IOException,
			ClassNotFoundException {
		String uri = MessageFormat.format(activitiProperties
				.getProperty("activiti.server.variaveis.processo.serializado"),
				idProcesso, variavelNome);

		ResponseEntity<byte[]> response = restTemplate.getForEntity(uri,
				byte[].class);
		InputStream is = new ByteArrayInputStream(response.getBody());
		ObjectInputStream in = new ObjectInputStream(is);
		Object o = in.readObject();
		return (Map<String, Object>) o;
	}

	/**
	 * Solucao temporaria para problema com URL responsavel por retornar as
	 * variaveis de um historic process A chamada via REST nao esta funcional
	 * para este caso.
	 * 
	 * @param variables
	 * @return
	 */
	private List<Variaveis> setVariavesFromHistoricVariables(
			List<HistoricVariableInstance> historicVariablesInstance) {
		List<Variaveis> variaveis = new ArrayList<Variaveis>();
		Variaveis var = null;
		for (HistoricVariableInstance hv : historicVariablesInstance) {

			try {
				if (hv.getValue() != null) {
					var = new Variaveis();
					var.setName(hv.getVariableName());
					var.setValue(hv.getValue());
					variaveis.add(var);
				}
			} catch (ActivitiException e) {
				System.out.println(e.getMessage());
			}
		}
		return variaveis;
	}

	@Override
	public String getProcessoDiagrama(String processInstanceId) {
		String uri = MessageFormat
				.format(activitiProperties
						.getProperty("activiti.server.processo.instanciado.diagrama.url"),
						processInstanceId);

		return uri;

	}

	public String iniciarInstanciaProcessoPorParametrosByKey(
			String processoDefinicaoKey, String businessKey,
			Map<String, Object> paramsProcesso) {

		this.identityService.setAuthenticatedUserId((String) paramsProcesso
				.get("solicitante"));
		ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey(processoDefinicaoKey, businessKey,
						paramsProcesso);

		return (processInstance != null) ? processInstance.getId() : null;
	}

	@Override
	public String cancelarProcessoInstaciado(String processInstanceId) {
		// TODO Auto-generated method stub
		String uri = MessageFormat.format(activitiProperties
				.getProperty("activiti.server.process.instance.suspend"),
				processInstanceId);

		String json = "{\"action\" : \"suspend\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", this.CONTENT_TYPE);

		HttpEntity<String> request = new HttpEntity<String>(json, headers);
		restTemplate.put(uri, request);

		return "200";
	}

	public List<ProcessoDefinicao> getProcessosDefinicaoPorQueryLastVersion() {
		ProcessDefinitionQuery query = repositoryService
				.createProcessDefinitionQuery();
		List<ProcessDefinition> result = query.latestVersion().list();

		return ProcessoDefinicao
				.exportProcessDefinitionInterfaceToProcessoDefinicaoBean(result);
	}

	public List<TarefaInstancia> getHistoricoTarefasPorVariaveis(
			Map<String, Object> variables, String assignee,
			String processDefinitionKey, Boolean isPendente, String idProcesso)
			throws ParseException {

		StringBuffer sqlQuery = new StringBuffer("SELECT distinct(T.*) FROM "
				+ managementService.getTableName(HistoricTaskInstance.class)
				+ " T,"
				+ "ACT_HI_VARINST V WHERE T.execution_id_ = V.execution_id_");

		if (idProcesso != null) {
			sqlQuery.append(" and T.proc_inst_id_ = '" + idProcesso + "'");
		}

		if (assignee != null) {
			sqlQuery.append(" and T.assignee_ = '" + assignee + "'");
		}

		if (processDefinitionKey != null) {
			sqlQuery.append(" and T.proc_def_id_ like '" + processDefinitionKey
					+ "%'");
		}

		if (isPendente != null) {
			if (isPendente) {
				sqlQuery.append(" and T.end_time_ is null ");
			} else {
				sqlQuery.append(" and T.end_time_ is not null ");
			}
		}

		List<HistoricTaskInstance> resultTask = historyService
				.createNativeHistoricTaskInstanceQuery()
				.sql(sqlQuery.toString()).list();

		List<Variaveis> listaVariaveis = null;
		List<TarefaInstancia> listaTarefaInstancia = new ArrayList<TarefaInstancia>();
		TarefaInstancia tarefaInstancia = null;

		if (!resultTask.isEmpty()) {

			for (HistoricTaskInstance task : resultTask) {
				tarefaInstancia = new TarefaInstancia();
				tarefaInstancia.exportHistoricTaskToTarefaInstanciaBean(task);

				listaVariaveis = new ArrayList<Variaveis>();

				if (tarefaInstancia.getProcessInstanceId() != null) {
					listaVariaveis = this
							.getVariaveisAPIExplorer(tarefaInstancia
									.getProcessInstanceId());
				}

				tarefaInstancia.setVariables(listaVariaveis);
				listaTarefaInstancia.add(tarefaInstancia);
			}

		}

		return listaTarefaInstancia;
	}

	public int incrementarVersaoDocumento(Map<String, Object> variables) {
		HistoricProcessInstanceQuery query = historyService
				.createHistoricProcessInstanceQuery();

		for (String s : variables.keySet()) {
			query.variableValueEquals(s, variables.get(s));
			System.out.println(s + "=" + variables.get(s));
		}

		List<HistoricProcessInstance> resultHistoricProcessInstance = query
				.includeProcessVariables().list();

		return resultHistoricProcessInstance.size();

	}

}