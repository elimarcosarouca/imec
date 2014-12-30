package br.fucapi.ads.modelo.dominio;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fuca_notificacao")
public class Notificacao implements Serializable {

	private static final long serialVersionUID = 8937597170343910319L;

	@Id
	@Column(name = "noti_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "noti_protocolo", nullable = false, length =30)
	private String protocolo;
	
	@Column(name = "noti_descricao", nullable = false, length =100)
	private String descricao;
	
	@Column(name = "noti_login", nullable = false, length =30)
	private String login;
	
	@Column(name = "noti_data", nullable = false)
	private Date data;
	
	@Column(name = "noti_data_leitura", nullable = true)
	private Date dataLeitura;

	public int getId() {
		return id;
	}

	public void setId(int id) {
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