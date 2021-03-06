package br.fucapi.wspoc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

import br.fucapi.wspoc.domain.Pessoa;
import br.fucapi.wspoc.domain.Usuario;
import br.fucapi.wspoc.service.PessoaServico;

@RequestMapping("/usuario")
@Controller
// @RooWebScaffold(path = "usuario", formBackingObject = Usuario.class)
// @RooWebJson(jsonObject = Usuario.class)
public class UsuarioController {

	@Autowired
	PessoaServico pessoaServico;

//	@Autowired
//	private AlfrescoServico alfrescoServico;
//
//	private br.fucapi.bpms.alfresco.dominio.Usuario usuario;

	@RequestMapping(method = RequestMethod.POST, value = "/logar", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> logar(
			@RequestParam(value = "login") String login,
			@RequestParam(value = "senha") String senha) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		Usuario usuario = new Usuario();

		try {
//			this.usuario = alfrescoServico.autenticarUsuario(login, senha);

			// usuario.setNome(this.usuario.getFirstName()
			// + this.usuario.getLastName());
			// usuario.setFuncao(this.usuario.getJobtitle());
			// usuario.setLogin(this.usuario.getUserName());
			usuario.setSenha(senha);

			System.out.println("Usuário e senha válidos");

		} catch (HttpClientErrorException e) {
			System.out.println("Usuário e senha inválidos");
			usuario = new Usuario();
			return new ResponseEntity<String>(usuario.toJson(), headers,
					HttpStatus.FORBIDDEN);

			// } catch (DocumentException e) {
			// e.printStackTrace();
			// return new ResponseEntity<String>(usuario.toJson(), headers,
			//			HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		}

		return new ResponseEntity<String>(usuario.toJson(), headers,
				HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/list", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> list() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<Pessoa> result = new ArrayList<Pessoa>();
		Pessoa p = new Pessoa();
		p.setNome("p1");
		p.setCpf(90l);

		result.add(p);
		return new ResponseEntity<String>(Pessoa.toJsonArray(result), headers,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
		Pessoa pessoa = pessoaServico.findPessoa(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		if (pessoa == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(pessoa.toJson(), headers,
				HttpStatus.OK);
	}

	@RequestMapping(headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		List<Pessoa> result = pessoaServico.findAllPessoas();
		return new ResponseEntity<String>(Pessoa.toJsonArray(result), headers,
				HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJson(@RequestBody String json) {
		Pessoa pessoa = Pessoa.fromJsonToPessoa(json);
		pessoaServico.savePessoa(pessoa);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
		for (Pessoa pessoa : Pessoa.fromJsonArrayToPessoas(json)) {
			pessoaServico.savePessoa(pessoa);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJson(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		Pessoa pessoa = Pessoa.fromJsonToPessoa(json);
		if (pessoaServico.updatePessoa(pessoa) == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (Pessoa pessoa : Pessoa.fromJsonArrayToPessoas(json)) {
			if (pessoaServico.updatePessoa(pessoa) == null) {
				return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
			}
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
		Pessoa pessoa = pessoaServico.findPessoa(id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		if (pessoa == null) {
			return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
		}
		pessoaServico.deletePessoa(pessoa);
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
}
