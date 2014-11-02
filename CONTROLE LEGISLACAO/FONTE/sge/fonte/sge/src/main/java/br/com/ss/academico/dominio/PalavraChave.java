package br.com.ss.academico.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.ss.core.seguranca.dominio.AbstractEntity;

/**
 * The persistent class for the iansa_curso database table.
 */
@Entity
@Table(name = "norm_palavra_chave")
public class PalavraChave extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -1913803606529669042L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_norm_palavra_chave")
	private Long id;

	@Column(nullable = false, length = 60, unique = true)
	private String nome;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}