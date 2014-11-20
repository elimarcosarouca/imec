package br.com.ss.util;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.ss.enumerado.Constants;

public class PageUtils {
	
	/**
	 * Adiciona o redirect para o url informada.
	 * ex: paginas/rotina/lista.xhtml?faces-redirect=true
	 * @param url
	 * @return
	 */
	public static String returnUrlRedirect(String url) {
		return url + Constants.REDIRECT;
	}
	
	/**
	 * Redireciona para a url informada.
	 * @param url
	 */
	public static void redirectForUrl(String url) {
	    try {
	    	ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(ec.getRequestContextPath() + "/" + url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retorna a url completa da página inicial.
	 * Ex: http://localhost:9090/sge/index.xhtml?faces-redirect=true
	 * @return
	 */
	public static String getFullUrlHome() {
		HttpServletRequest request = FacesUtils.getRequest();	
		StringBuffer requestURL = request.getRequestURL();
		
		int indexOfPath = requestURL.indexOf(request.getContextPath());
		String address = requestURL.substring(0, indexOfPath)
						+ request.getContextPath() + Constants.BARRA
						+ Constants.INDEX_REDIRECT;
		return address;
	}
	
}