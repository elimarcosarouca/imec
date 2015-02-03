package br.fucapi.ads.modelo.utils;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public final class FacesUtils {

	private FacesUtils() { }
	
	/**
	 * Retorna a instancia de HttpServletRequest.
	 */
	public static HttpServletRequest getRequest() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return request;
	}
	
	public static void addMessage(String msg, String detail, Severity severityInfo) {
		FacesMessage facesMessage = new FacesMessage();
		facesMessage.setSeverity(severityInfo);
		facesMessage.setSummary(msg);
		facesMessage.setDetail(detail);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	
	
	/**
	 * Retorna o objeto associado ao parametro (map) no context de Aplicacao.
	 * @param param
	 * @return
	 */
	public static Object getApplicationParam(String param) {
		return FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get(param);
	}
	
	
	
}
