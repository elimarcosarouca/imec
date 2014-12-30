package br.fucapi.ads.assinaturadigital.applet.x509.exception;


public class X509PDFSignerException extends Exception {

	private static final long serialVersionUID = -4063413116749118959L;

	public X509PDFSignerException(String msg){
		super(msg);
	}

	public X509PDFSignerException(String errorMsg, Throwable e) {
		super(errorMsg,e);
	}

}