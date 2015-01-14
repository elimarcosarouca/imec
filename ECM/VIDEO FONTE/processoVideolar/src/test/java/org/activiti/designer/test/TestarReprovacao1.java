package org.activiti.designer.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

public class TestarReprovacao1 {

	private String filename = "C:\\Users\\claudemirferreira\\workspace\\processoVideolar\\src\\main\\resources\\diagrams\\publicarDocumento.bpmn";

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	@Test
	public void startProcess() throws Exception {

		RuntimeService runtimeService = activitiRule.getRuntimeService();
		TaskService taskService = activitiRule.getTaskService();

		List<ProcessInstance> lista = runtimeService
				.createProcessInstanceQuery().active().list();
		System.out.println("lista = " + lista.size());

		List<Task> tasks;

		tasks = taskService.createTaskQuery().processInstanceId("5905").list();

		for (Task task : tasks) {
			System.out.println(task.getId());
		}
		
		/*taskService.complete("5236");
		System.out.println("tarefa reprovadoa 5231");
		tasks = taskService.createTaskQuery().processInstanceId("5205").list();

		for (Task task : tasks) {
			System.out.println(task.getId());
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("aprovacaoOK", false);
			taskService.complete(task.getId(), variables);
			
			
		}*/

	}
}