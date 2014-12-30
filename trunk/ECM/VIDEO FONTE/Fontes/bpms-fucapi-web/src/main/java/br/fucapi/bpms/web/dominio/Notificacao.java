package br.fucapi.bpms.web.dominio;
import java.util.Date;

import br.fucapi.bpms.alfresco.dominio.Usuario;

public class Notificacao {

    private Usuario usuario;
    private Date dataInicial;
    private Date dataFinal;
    private Status status;
    
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Date getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
	public Date getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
}
