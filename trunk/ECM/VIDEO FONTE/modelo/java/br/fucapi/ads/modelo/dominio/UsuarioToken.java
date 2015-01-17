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
@Table(name = "ECM_USUARIO_TOKEN")
public class UsuarioToken extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -3197181642608086108L;

	@Id
	@GeneratedValue(generator = "SEQ_ECM_USUARIO_TOKEN", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_ECM_USUARIO_TOKEN", name = "SEQ_ECM_USUARIO_TOKEN")
	@Column(name = "ID_ECM_USUARIO_TOKEN")
	private Long id;

	@Column(name = "token", nullable = false)
    private String token;

    @Column(name = "data_geracao")
    private Date dataGeracao;

    @Column(name = "username")
    private String userName;

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "utilizado")
    private Boolean utilizado;

    @Column(name = "data_utilizacao")
    private Date dataUtilizacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getDataGeracao() {
		return dataGeracao;
	}

	public void setDataGeracao(Date dataGeracao) {
		this.dataGeracao = dataGeracao;
	}

	public Boolean getUtilizado() {
		return utilizado;
	}

	public void setUtilizado(Boolean utilizado) {
		this.utilizado = utilizado;
	}

	public Date getDataUtilizacao() {
		return dataUtilizacao;
	}

	public void setDataUtilizacao(Date dataUtilizacao) {
		this.dataUtilizacao = dataUtilizacao;
	}
	
}
