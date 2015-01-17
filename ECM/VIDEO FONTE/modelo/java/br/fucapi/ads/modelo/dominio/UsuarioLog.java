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

import br.fucapi.bpms.alfresco.dominio.Usuario;

@Entity
@Table(name = "ECM_USUARIO_LOG")
public class UsuarioLog extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 2394474225356759395L;

	@Id
	@GeneratedValue(generator = "SEQ_ECM_USUARIO_LOG", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_ECM_USUARIO_LOG", name = "SEQ_ECM_USUARIO_LOG")
	@Column(name = "ID_ECM_USUARIO_LOG")
	private Long id;

	@Column(nullable = false, length = 60)
	private String firstName;

	@Column( nullable = false, length = 60)
	private String lastName;

	@Column( nullable = false, length = 30)
	private String login;

	@Column(nullable = false, length = 60)
	private String email;

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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public UsuarioLog(String firstName, String lastName, String login,
			String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.login = login;
		this.email = email;
	}

	public UsuarioLog conveterUsuarioToUsuarioLog(Usuario usuario) {
		return new UsuarioLog(usuario.getFirstName(), usuario.getLastName(),
				usuario.getUserName(), usuario.getEmail());

	}

}
