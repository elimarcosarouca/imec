package br.fucapi.ads.assinaturadigital.applet.x509.exception;

public final class FinalizacaoDocumentosAssinadosException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8741273192929220014L;

	public FinalizacaoDocumentosAssinadosException(String errorMsg, Throwable e) {
		super(errorMsg,e);
	}

	public FinalizacaoDocumentosAssinadosException(String errorMsg) {
		super(errorMsg);
	}
}