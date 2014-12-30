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
@Table( name="usuario_tarefa")
public class UsuarioTarefa implements Serializable {

	private static final long serialVersionUID = -4799348218025777240L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(nullable = false)
	private String idTarefa;

	@Column(nullable = false)
	private String loginAnterior;

	@Column(nullable = false)
	private String loginNovo;

	@Column(nullable = false)
	private String loginAdm;

	@Column(nullable = false)
	private Date dataAlteracao;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdTarefa() {
		return idTarefa;
	}

	public void setIdTarefa(String idTarefa) {
		this.idTarefa = idTarefa;
	}

	public String getLoginAnterior() {
		return loginAnterior;
	}

	public void setLoginAnterior(String loginAnterior) {
		this.loginAnterior = loginAnterior;
	}

	public String getLoginNovo() {
		return loginNovo;
	}

	public void setLoginNovo(String loginNovo) {
		this.loginNovo = loginNovo;
	}

	public String getLoginAdm() {
		return loginAdm;
	}

	public void setLoginAdm(String loginAdm) {
		this.loginAdm = loginAdm;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

}
