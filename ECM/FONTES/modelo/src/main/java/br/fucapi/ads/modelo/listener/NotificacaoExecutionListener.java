package br.fucapi.ads.modelo.listener;

import java.util.Date;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.el.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import br.fucapi.ads.modelo.dominio.Notificacao;
import br.fucapi.ads.modelo.servico.NotificacaoServico;

@Service
public class NotificacaoExecutionListener implements ExecutionListener {

	private Expression fixedValue;

	private Expression dynamicValue;
	
	@Autowired
	private NotificacaoServico notificacaoServico;

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		System.out.println("entrou no NotificacaoExecutionListener");
		execution.setVariable("eventReceived", execution.getEventName());

		execution.setVariable("var", fixedValue.getValue(execution).toString()
				+ dynamicValue.getValue(execution).toString());

		System.out.println("fixedValue = "
				+ fixedValue.getValue(execution).toString());
		
		System.out.println("dynamicValue = "
				+ dynamicValue.getValue(execution).toString());
//		
//		Notificacao notificacao= new Notificacao();
//		
//		notificacao.setData(new Date());
//		notificacao.setDescricao("teste 1");
//		notificacao.setLogin("admin");
//		notificacao.setProtocolo(dynamicValue.getValue(execution).toString());
//		
//		System.out.println("notificacaoServico" + notificacaoServico);
		
//		notificacaoServico.salvar(notificacao);

	}
}
