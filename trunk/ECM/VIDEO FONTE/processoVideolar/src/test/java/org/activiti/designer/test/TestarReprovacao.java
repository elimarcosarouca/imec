package org.activiti.designer.test;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

public class TestarReprovacao {

	private String filename = "C:\\Users\\claudemirferreira\\workspace\\processoVideolar\\src\\main\\resources\\diagrams\\publicarDocumento.bpmn";

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	@Test
	public void startProcess() throws Exception {

		ProcessEngine processEngine = activitiRule.getProcessEngine();
		RepositoryService repositoryService = activitiRule
				.getRepositoryService();
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		TaskService taskService = activitiRule.getTaskService();

		List<ProcessInstance> lista = runtimeService
				.createProcessInstanceQuery().active().list();
		System.out.println("lista = " + lista.size());

		List<Task> tasks;

		for (ProcessInstance processInstance : lista) {
			System.out.println("getProcessInstanceId = "
					+ processInstance.getId());
			tasks = taskService
					.createTaskQuery()
					.processInstanceId(processInstance.getId())
					.list();

			for (Task task : tasks) {
				System.out.println(task.getId());
			}
		}

	}
}