package br.fucapi.ads.modelo.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author claudemirferreira
 * Armazenar os dados tarefa
 */

@Entity
@Table(name = "variaveis_tarefa")
public class VariaveisTarefa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idVariaveisTarefa;

	@Column(nullable = false)
	private Long id;

	@Column(nullable = false, length = 300)
	private String parecer;
	
	@Column(nullable = false, length = 300)
	private String acao;

	public Long getIdVariaveisTarefa() {
		return idVariaveisTarefa;
	}

	public void setIdVariaveisTarefa(Long idVariaveisTarefa) {
		this.idVariaveisTarefa = idVariaveisTarefa;
	}

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

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

}
