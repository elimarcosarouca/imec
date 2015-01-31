package br.com.jm.conversor.pdf;

public interface ConversaoParaPDF {
	
	public byte[] converterDocumento(byte[] documentoParaConverter) throws ImpossivelConverterDocumentoException;
	
}