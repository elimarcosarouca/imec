package br.fucapi.ads.modelo.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author claudemirferreira Armazenar os dados tarefa
 */

@Entity
@Table(name = "ECM_VARIAVEIS_TAREFA")
public class VariaveisTarefa extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 2934198621805545061L;

	@Id
	@GeneratedValue(generator = "SEQ_ECM_VARIAVEIS_TAREFA", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_ECM_VARIAVEIS_TAREFA", name = "SEQ_ECM_VARIAVEIS_TAREFA")
	@Column(name = "ID_ECM_VARIAVEIS_TAREFA")
	private Long id;

	@Column(nullable = false)
	private Long idProcesso;

	@Column(nullable = false, unique = true)
	private Long idTarefa;

	@Column(nullable = false, length = 300)
	private String parecer;

	@Column(nullable = false)
	private boolean status;
	
	@Column(nullable = false, length = 30)
	private String login;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParecer() {
		return parecer;
	}

	public void setParecer(String parecer) {
		this.parecer = parecer;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Long getIdProcesso() {
		return idProcesso;
	}

	public void setIdProcesso(Long idProcesso) {
		this.idProcesso = idProcesso;
	}

	public Long getIdTarefa() {
		return idTarefa;
	}

	public void setIdTarefa(Long idTarefa) {
		this.idTarefa = idTarefa;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}