package br.com.ss.academico.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.ss.core.seguranca.dominio.AbstractEntity;

@Entity
@Table(name = "acad_configuracao")
public class Configuracao extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1620639627899690081L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idConfiguracao;

	@Column(nullable = false)
	private Integer diaVencimento;

	@Column( length = 20)
	private String tema;

	@Column(nullable = false)
	private Float mediaEscolar;
	
	
	
	
	@Override
	public Long getId() {
		return idConfiguracao;
	}

	public Long getIdConfiguracao() {
		return idConfiguracao;
	}

	public void setIdConfiguracao(Long idConfiguracao) {
		this.idConfiguracao = idConfiguracao;
	}

	public Integer getDiaVencimento() {
		return diaVencimento;
	}

	public void setDiaVencimento(Integer diaVencimento) {
		this.diaVencimento = diaVencimento;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public Float getMediaEscolar() {
		return mediaEscolar;
	}

	public void setMediaEscolar(Float mediaEscolar) {
		this.mediaEscolar = mediaEscolar;
	}

	
}
