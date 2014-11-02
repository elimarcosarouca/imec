package br.com.ss.academico.dominio;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ss.core.seguranca.dominio.AbstractEntity;

/**
 * The persistent class for the iansa_curso database table.
 */
@Entity
@Table(name = "norm_requisito")
public class Requisito extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -1913803606529669042L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_norm_requisito")
	private Long id;

	@Column(nullable = false, length = 200)
	private String descricao;

	@Column(length = 200)
	private String resposta;

	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataValidade;

	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;

	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataAlerta;

	@Column(length = 200)
	private String secaoAtendida;

	@Column(length = 200)
	private String pathFile;

	@ManyToOne
	@JoinColumn(name = "id_legislacao")
	private Legislacao legislacao;

	@ManyToOne
	@JoinColumn(name = "id_filial")
	private Filial filial;

	@ManyToOne
	@JoinColumn(name = "id_responsavel")
	private Responsavel responsavel;

	/*@ManyToOne
	@JoinColumn(name = "id_empresa")
	private Empresa empresa;*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public Date getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataAlerta() {
		return dataAlerta;
	}

	public void setDataAlerta(Date dataAlerta) {
		this.dataAlerta = dataAlerta;
	}

	public String getSecaoAtendida() {
		return secaoAtendida;
	}

	public void setSecaoAtendida(String secaoAtendida) {
		this.secaoAtendida = secaoAtendida;
	}

	public String getPathFile() {
		return pathFile;
	}

	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}

}