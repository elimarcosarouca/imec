package org.activiti.designer.test;

import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

public class ProcessTestPUBLICARDOCUMENTO {

	private String filename = "C:\\Users\\claudemirferreira\\workspace\\processoVideolar\\src\\main\\resources\\diagrams\\publicarDocumento.bpmn";

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	@Test
	public void startProcess() throws Exception {
		RepositoryService repositoryService = activitiRule
				.getRepositoryService();
		repositoryService
				.createDeployment()
				.addInputStream("PUBLICAR_DOCUMENTO.bpmn20.xml",
						new FileInputStream(filename)).deploy();
		/*RuntimeService runtimeService = activitiRule.getRuntimeService();
		
		List<String> aprovadores = new ArrayList<String>();
		aprovadores.add("admin");
		aprovadores.add("admin");

		Boolean publicacaoAutomatica = false;
		
		Boolean enviarConcensao = true;

		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("solicitante", "admin");
		variableMap.put("emailAprovador", "claudemirramosferreira@gmail.com");
		variableMap.put("tipoSolicitacao", "PUBLICAR_DOCUMENTO");

		variableMap.put("aprovadores", aprovadores);
		variableMap.put("concesos", aprovadores);
		variableMap.put("publicacaoAutomatica", publicacaoAutomatica);
		variableMap.put("enviarConcensao", enviarConcensao);
		variableMap.put("statusProcesso", "PENDENTE");

		ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey("PUBLICAR_DOCUMENTO", variableMap);
		assertNotNull(processInstance.getId());
		System.out.println("id " + processInstance.getId() + " "
				+ processInstance.getProcessDefinitionId());*/

	}
}