package br.fucapi.ads.modelo.dominio;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fuc_usuario_grupo_log")
public class UsuarioGrupoLog implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 60)
	private String grupo;

	@Column(nullable = false, length = 30)
	private String login;

	@Column(nullable = false, length = 30)
	private String loginAdm;

	@Column(nullable = false)
	private Date data;

	@Column(nullable = false, length = 1)
	private String operacao;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLoginAdm() {
		return loginAdm;
	}

	public void setLoginAdm(String loginAdm) {
		this.loginAdm = loginAdm;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public UsuarioGrupoLog(String grupo, String login, String loginAdm, Date data, String operacao) {
		this.grupo = grupo;
		this.login = login;
		this.loginAdm = loginAdm;
		this.data = data;
		this.operacao = operacao;
	}
	
	public UsuarioGrupoLog(String grupo, String login, String loginAdm, Date data) {
		this.grupo = grupo;
		this.login = login;
		this.data = data;
		this.loginAdm = loginAdm;
	}

}
