package br.fucapi.bpms.activiti.dominio;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.fucapi.bpms.activiti.dao.VariavelDAO;
import br.fucapi.bpms.activiti.enumerated.NomeVariavel;

public class MainApp {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:META-INF/spring/applicationContext.xml");
		VariavelDAO variavelDAO = (VariavelDAO) context.getBean("variavelDAO");
		variavelDAO.atualizarVariavelProcesso("1101", NomeVariavel.STATUS_PROCESSO, "teste");
		
	}
}