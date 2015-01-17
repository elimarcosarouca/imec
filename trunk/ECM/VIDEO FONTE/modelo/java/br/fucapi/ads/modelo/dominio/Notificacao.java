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
@Table(name = "ECM_NOTIFICACAO")
public class Notificacao extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 8937597170343910319L;

	@Id
	@GeneratedValue(generator = "SEQ_ECM_NOTIFICACAO", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_ECM_NOTIFICACAO", name = "SEQ_ECM_NOTIFICACAO")
	@Column(name = "ID_ECM_NOTIFICACAO")
	private Long id;
	
	@Column(nullable = false, length =30)
	private String protocolo;
	
	@Column( nullable = false, length =100)
	private String descricao;
	
	@Column( nullable = false, length =30)
	private String login;
	
	@Column( nullable = false)
	private Date data;
	
	@Column( nullable = true)
	private Date dataLeitura;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getDataLeitura() {
		return dataLeitura;
	}

	public void setDataLeitura(Date dataLeitura) {
		this.dataLeitura = dataLeitura;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}