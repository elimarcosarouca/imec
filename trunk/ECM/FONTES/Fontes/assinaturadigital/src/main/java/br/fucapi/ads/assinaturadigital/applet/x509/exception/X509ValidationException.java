package br.fucapi.ads.assinaturadigital.applet.x509.exception;

public class X509ValidationException extends Exception {

	private static final long serialVersionUID = 6536923378908134017L;

	public X509ValidationException(String msg){
		super(msg);
	}

	public X509ValidationException(String errorMsg, Throwable e) {
		super(errorMsg,e);
	}
}
