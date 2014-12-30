package br.fucapi.bpms.web.dominio;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "siem_pendencia")
public class Pendencia implements Serializable {

	private static final long serialVersionUID = 8666956092211205413L;

	@Id
	@Column(name = "pend_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "pend_process_instance_id", nullable = false, length = 100)
	private String processInstanceId;

	@Column(name = "pend_business_key", nullable = false, length = 15)
	private String businessKey;

	@Column(name = "pend_login", nullable = false, length = 30)
	private String login;

	@Column(name = "pend_data", nullable = false)
	private Date data;

	@Column(name = "pend_data_confirmacao")
	private Date dataConfirmacao;

	@Column(name = "pend_status")
	private Boolean status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getDataConfirmacao() {
		return dataConfirmacao;
	}

	public void setDataConfirmacao(Date dataConfirmacao) {
		this.dataConfirmacao = dataConfirmacao;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
}
