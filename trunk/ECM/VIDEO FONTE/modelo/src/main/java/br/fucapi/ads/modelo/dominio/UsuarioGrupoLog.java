package br.fucapi.ads.modelo.dominio;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ECM_USUARIO_GRUPO_LOG")
public class UsuarioGrupoLog extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -2068862972554281902L;

	@Id
	@GeneratedValue(generator = "SEQ_ECM_USUARIO_GRUPO_LOG", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_ECM_USUARIO_GRUPO_LOG", name = "SEQ_ECM_USUARIO_GRUPO_LOG")
	@Column(name = "ID_ECM_USUARIO_GRUPO_LOG")
	private Long id;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public UsuarioGrupoLog() {
		super();
		// TODO Auto-generated constructor stub
	}

}
