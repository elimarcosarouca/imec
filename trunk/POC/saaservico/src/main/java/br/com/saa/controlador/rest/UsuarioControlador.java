package br.com.saa.controlador.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.saa.modelo.entidade.Usuario;

//@Controller
//@RequestMapping("/saaservico/usuarios")
public class UsuarioControlador {

//	@Autowired
//	private UsuarioServico usuarioServico;
//
//	public UsuarioServico getUsuarioServico() {
//		return usuarioServico;
//	}

//	public void setUsuarioServico(UsuarioServico usuarioServico) {
//		this.usuarioServico = usuarioServico;
//	}

	@RequestMapping(value = "/{nome}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listJson(@PathVariable("nome") String nome) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		Usuario usuario = new Usuario();

//		if (nome != null) {
//			usuario = usuarioServico.findByLoginAndSenha("", "");
//
//			if (usuario == null)
//				usuario = new Usuario();
//		}

		return new ResponseEntity<String>(usuario.toJson(), headers,
				HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> listAll() {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		List<Usuario> usuarios =null;// usuarioServico.listarTodos();

		return new ResponseEntity<String>(Usuario.toJsonArray(usuarios),
				headers, HttpStatus.OK);
	}
}