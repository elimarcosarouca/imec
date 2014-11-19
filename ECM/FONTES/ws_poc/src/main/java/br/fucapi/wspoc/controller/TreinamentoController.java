package br.fucapi.wspoc.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.fucapi.bpms.activiti.dominio.TarefaInstancia;
import br.fucapi.bpms.activiti.servico.ActivitiServico;
import br.fucapi.bpms.alfresco.servico.AlfrescoServico;
import br.fucapi.wspoc.domain.Status;
import br.fucapi.wspoc.domain.TipoTarefa;
import br.fucapi.wspoc.domain.Treinamento;
import br.fucapi.wspoc.domain.Usuario;
import br.fucapi.wspoc.service.PessoaServico;

@RequestMapping("/treinamentos")
@Controller
@RooWebScaffold(path = "treinamentos", formBackingObject = Treinamento.class)
@RooWebJson(jsonObject = Treinamento.class)
public class TreinamentoController {

	@Autowired
	PessoaServico pessoaServico;

	@Autowired
	private ActivitiServico activitiServico;

	@Autowired
	private AlfrescoServico alfrescoServico;

	private br.fucapi.bpms.alfresco.dominio.Usuario usuario;

	@RequestMapping(method = RequestMethod.GET, value = "/listTreinamento/{login}")
	@ResponseBody
	public ResponseEntity<String> listTreinamento(
			@PathVariable("login") String login) {

		List<TarefaInstancia> tarefas = activitiServico
				.getTarefasUsuario(login);
		List<Treinamento> result = new ArrayList<Treinamento>();
		Treinamento treinamento = null;

		for (TarefaInstancia tarefa : tarefas) {
			treinamento = new Treinamento();

			treinamento.setId(tarefa.getId());
			treinamento.setNome(tarefa.getName());
			treinamento.setStatus(Status.PENDENTE);
			treinamento.setTipo(TipoTarefa.APROVAR);

			Calendar dataInicio1 = Calendar.getInstance();
			dataInicio1.set(2013, Calendar.NOVEMBER, 6);

			Calendar dataFim1 = Calendar.getInstance();
			dataFim1.set(2013, Calendar.NOVEMBER, 8);

			treinamento.setDataInicial(tarefa.getCreateTime());
			treinamento.setDataFinal(new Date());

			System.out.println(treinamento.getDataInicial());

			Usuario colaborador = new Usuario();

			colaborador.setNome("Lauro Uchoa");
			colaborador.setFuncao("Desenvolvedor");

			Usuario responsavel = new Usuario();
			responsavel.setNome(tarefa.getAssignee());

			responsavel.setNome("Carlos Fernandes");

			treinamento.setResponsavel(responsavel);
			treinamento.setColaborador(colaborador);
			treinamento.setCustoEstimado(2000f);
			treinamento.setDescricao(tarefa.getDescription());

			result.add(treinamento);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		return new ResponseEntity<String>(Treinamento.toJsonArray(result),
				headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/aprovar", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> aprovar(
			@RequestParam(value = "id") String id,
			@RequestParam(value = "status") Boolean status,
			@RequestParam(value = "parecer") String parecer) {

		String json;

		if (status)
			json = "{\"name\":\"status\", \"value\":true},"
					+ "{\"name\":\"parecer\", \"value\":\"" + parecer + "\"}";
		else
			json = "{\"name\":\"status\", \"value\":false},"
					+ "{\"name\":\"parecer\", \"value\":\"" + parecer + "\"}";

		System.out.println(id);
		System.out.println(status);
		System.out.println(parecer);
		System.out.println(json);
		// String result = "200";
		String result = activitiServico.completarTarefa(id, json);
		System.out.println(result);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		if (result.equals("200")){
			Treinamento treinamento = new Treinamento();
			treinamento.setId(id);
			System.out.println(treinamento.toJson());
			return new ResponseEntity<String>(treinamento.toJson(),
					headers, HttpStatus.OK);
			
		}
			
		else {
			Treinamento treinamento = new Treinamento();
			treinamento.setId(id);
			return new ResponseEntity<String>(Treinamento.toJsonArray(null),
					headers, HttpStatus.METHOD_FAILURE);
		}
	}

}