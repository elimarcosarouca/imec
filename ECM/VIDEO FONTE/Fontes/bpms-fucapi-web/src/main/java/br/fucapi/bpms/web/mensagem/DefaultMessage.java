package br.fucapi.bpms.web.mensagem;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public final class DefaultMessage {
	

	/** Msg de sucesso. */
	public static final String MSG_SUCESSO = "Operacao realizada com sucesso.";

	/** Msg de sucesso. */
	public static final String MSG_ERRO = "Ocorreu um erro! N�o foi possível realizar a operação.";
	
	/** Msg de sucesso. */
	public static final String MSG_CONSTRAINT_VIOLATION = "O registro não pode ser excluído pois o mesmo é referenciado em outras partes do sistema.";
	
	
	
	/** Alias para redirecionamento apos uma operacao de SUCESSO. Mapear o mesmo no page.xml. */
	public static final String SUCESSO = "sucesso";

	/**Alias para redirecionamento apos uma operacao com ERRO. Mapear o mesmo no page.xml. */
	public static final String ERRO = "erro";	
	

	public static void sendMessageToUser(Severity severity, String message) {
		FacesContext context = getContext();
		context.addMessage(null, new FacesMessage(severity, message, message));
	}

	public static void sendInfoMessageToUser(String message) {
		sendMessageToUser(FacesMessage.SEVERITY_INFO, message);
	}

	public static void sendWarnMessageToUser(String message) {
		sendMessageToUser(FacesMessage.SEVERITY_WARN, message);
	}

	public static void sendErrorMessageToUser(String message) {
		sendMessageToUser(FacesMessage.SEVERITY_ERROR, message);
	}

	public static FacesContext getContext() {
		return FacesContext.getCurrentInstance();
	}

}
