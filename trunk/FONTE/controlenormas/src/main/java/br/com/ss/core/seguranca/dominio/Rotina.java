package br.com.ss.core.seguranca.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * @author altitdb
 */
@Entity
@Table(name = "saa_rotina")
public class Rotina extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 55549693990924773L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idRotina;

	@Column(length = 60)
	private String imagem;

	@Column(length = 100, nullable = false)
	private String nome;

	@Column(length = 100, nullable = false)
	private String acao;
	
	@Column(length = 1, nullable = false)
	private byte status;

	@ManyToOne
	@JoinColumn(name = "id_sistema")
	private Sistema sistema;

	@Transient
	private boolean checked;

	@Override
	public Long getId() {
		return idRotina;
	}

	public Long getIdRotina() {
		return idRotina;
	}

	public void setIdRotina(Long idRotina) {
		this.idRotina = idRotina;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}
}