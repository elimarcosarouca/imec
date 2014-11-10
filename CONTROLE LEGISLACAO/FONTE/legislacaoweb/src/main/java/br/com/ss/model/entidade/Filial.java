package br.com.ss.model.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the iansa_curso database table.
 */
@Entity
@Table(name = "LEGIS_FILIAL")
public class Filial extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -1913803606529669042L;

	@Id
	@GeneratedValue(generator = "SEQ_LEGIS_FILIAL", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_LEGIS_FILIAL", name = "SEQ_LEGIS_FILIAL")
	@Column(name = "ID_LEGIS_FILIAL")
	private Long id;

	@Column(nullable = false, length = 30, unique = true)
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