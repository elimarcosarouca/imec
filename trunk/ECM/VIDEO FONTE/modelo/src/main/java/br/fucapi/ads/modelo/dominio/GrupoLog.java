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
@Table(name = "fuc_grupo_log")
public class GrupoLog implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 60)
	private String grupo;
	
	@Column(nullable = false)
	private Date data;

	@Column(nullable = false, length = 1)
	private String operacao;
	
	@Column(nullable = false, length = 30)
	private String loginAdm;


	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
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
	
	
	public String getLoginAdm() {
		return loginAdm;
	}

	public void setLoginAdm(String loginAdm) {
		this.loginAdm = loginAdm;
	}

	public GrupoLog(String grupo,String loginAdm, Date data, String operacao) {
		this.grupo = grupo;
		this.loginAdm = loginAdm;
		this.data = data;
		this.operacao = operacao;
		
	}
	
}