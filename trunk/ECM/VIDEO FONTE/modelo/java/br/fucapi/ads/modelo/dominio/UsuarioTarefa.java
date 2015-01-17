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
@Table( name="ECM_USUARIO_TAREFA")
public class UsuarioTarefa extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 9145682138419512253L;

	@Id
	@GeneratedValue(generator = "SEQ_ECM_USUARIO_TAREFA", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_ECM_USUARIO_TAREFA", name = "SEQ_ECM_USUARIO_TAREFA")
	@Column(name = "ID_ECM_USUARIO_TAREFA")
	private Long id;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
