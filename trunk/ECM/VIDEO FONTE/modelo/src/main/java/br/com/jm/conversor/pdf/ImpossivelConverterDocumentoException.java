package br.com.jm.conversor.pdf;

public class ImpossivelConverterDocumentoException extends RuntimeException {
	
	public ImpossivelConverterDocumentoException(String msg) {
		super(msg);
	}
	
	public ImpossivelConverterDocumentoException(Throwable e) {
		super(null, e);
	}
	
}
