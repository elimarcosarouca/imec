package br.fucapi.ads.modelo.regranegocio;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.fucapi.ads.modelo.dominio.VariaveisTarefa;
import br.fucapi.ads.modelo.dominio.VariaveisTreinamento;
import br.fucapi.ads.modelo.servico.VariaveisTarefaServico;
import br.fucapi.bpms.activiti.dominio.TarefaInstancia;
import br.fucapi.bpms.activiti.dominio.Variaveis;
import br.fucapi.bpms.activiti.servico.ActivitiServico;
import br.fucapi.bpms.alfresco.dominio.Usuario;
import br.fucapi.bpms.alfresco.servico.AlfrescoServico;

//@Component
public class TreinamentoRN {

	@Autowired
	private AlfrescoServico alfrescoServico;
	
	@Autowired
	private ActivitiServico activitiServico;
	
	@Autowired
	private VariaveisTarefaServico variaveisTarefaServico;
	
	@Value("${alfresco.grupo.coordenacao}")
	private String grupoCoordenacao;
	
	@Value("${alfresco.grupo.diretoria}")
	private String grupoDiretoria;
	
	@Value("${alfresco.grupo.rh}")
	private String grupoRh;
	
	/**
	 * Metodo reposavel por inserir o parecer da atividade
	 * @param parecer parecer informado
	 * @param idTarefa identificador da tarefa
	 */
	public void salvarVariaveisTarefa(TarefaInstancia tarefaInstancia){
		List<Variaveis> variaveis = activitiServico.getVariaveisAPIExplorer(tarefaInstancia.getProcessInstanceId());
		
		VariaveisTreinamento variaveisTreinamento = new VariaveisTreinamento();
		variaveisTreinamento.converterListaVariaveisParaVariaveisProcesso(variaveis);
		
		VariaveisTarefa variaveisTarefaTemp = new VariaveisTarefa();
		
		variaveisTarefaTemp.setParecer(variaveisTreinamento.getParecer());
		variaveisTarefaTemp.setId(Long.valueOf(tarefaInstancia.getId()));
		
		variaveisTarefaServico.save(variaveisTarefaTemp);
	}
	
	public VariaveisTreinamento completarTarefa(
			VariaveisTreinamento variaveisProcessoTreinamento)
			throws RemoteException {
		
		return variaveisProcessoTreinamento;
	}

	public AlfrescoServico getAlfrescoServico() {
		return alfrescoServico;
	}

	public void setAlfrescoServico(AlfrescoServico alfrescoServico) {
		this.alfrescoServico = alfrescoServico;
	}

	public VariaveisTarefaServico getVariaveisTarefaServico() {
		return variaveisTarefaServico;
	}

	public void setVariaveisTarefaServico(
			VariaveisTarefaServico variaveisTarefaServico) {
		this.variaveisTarefaServico = variaveisTarefaServico;
	}

}
