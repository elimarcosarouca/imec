package br.com.jm.conversor.pdf;

public class ConversaoPronta implements ConversaoParaPDF {

	public byte[] converterDocumento(byte[] documento) throws ImpossivelConverterDocumentoException {
		return documento;
	}

}
