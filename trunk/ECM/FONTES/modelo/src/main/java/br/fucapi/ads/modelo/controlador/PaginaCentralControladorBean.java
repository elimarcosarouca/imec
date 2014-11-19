package br.fucapi.ads.modelo.controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class PaginaCentralControladorBean {

	private String paginaCentral;
	
	public PaginaCentralControladorBean() {
		setPaginaCentral("paginas/tarefa/pesquisatarefapendente.xhtml");
	}

	public String getPaginaCentral() {
		return paginaCentral;
	}

	public void setPaginaCentral(String paginaCentral) {
		this.paginaCentral = paginaCentral;
	}
	
	public String telaCadastro() {
		setPaginaCentral("paginas/grupo/cadastro.xhtml");
		return "";
	}
}
