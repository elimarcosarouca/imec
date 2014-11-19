package br.fucapi.ads.assinaturadigital.applet.util;

import java.io.PrintStream;
import java.io.PrintWriter;


/**
 *  Representa erros da infra-estrutura do sistema. 
 */
public class SystemException extends RuntimeException implements MessageException {
	
	/** serial id  */
	private static final long serialVersionUID = -8101062656779336542L;

	private String messageKey = "geral.erro";

	public static final int CONCURRENT_ERROR = 0;
	public static final int DATABASE_VIOLATION_ERROR = 1;

	private int type;
	
	/**
	 * @return Returns the type.
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type The type to set.
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * Representa Excecao aninhada 
	 */
	private Exception exception;

	public SystemException() {
		super();
	}
	
	public SystemException(Exception e){
		super( e.getMessage() );
		this.exception = e;
	}
	
	public SystemException(String messageKey){
		setKey(messageKey);
	}
	
	public SystemException(String messageKey, Exception e){
	    setKey(messageKey);
	    this.exception = e;
	}
		
	public void printStackTrace(PrintStream p) {
		if (exception != null) {
			exception.printStackTrace(p);
		} else {
			super.printStackTrace(p);
		}
	}

	public void printStackTrace(PrintWriter p) {
		if (exception != null) {
			exception.printStackTrace(p);
		} else {
			super.printStackTrace(p);
		}
	}

	public void printStackTrace() {
		if (exception != null) {
			exception.printStackTrace();
		} else {
			super.printStackTrace();
		}
	}
	
    public String getKey() {
        return messageKey;
    }

    public SystemException setKey(String messageKey) {
        this.messageKey = messageKey;
        return this;
    }
	

}
