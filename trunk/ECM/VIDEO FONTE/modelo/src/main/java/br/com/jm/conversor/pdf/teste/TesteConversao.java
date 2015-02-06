package br.com.jm.conversor.pdf.teste;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.io.IOUtils;

import br.com.jm.conversor.pdf.ConversaoParaPDF;
import br.com.jm.conversor.pdf.office.ConversaoAPartirDeTextoOffice;

public class TesteConversao {

	private static byte[] obterArquivosEmBytes(String arquivo)
			throws IOException {
		return IOUtils.toByteArray(TesteConversao.class
				.getResourceAsStream(arquivo));
	}

	private static void escreverArquivosEmBytes(byte[] arquivo, String nome)
			throws IOException {
		IOUtils.write(arquivo, new FileOutputStream(nome));
	}

	// apenas mude o algoritmo e o caminho do arquivo para testar as conversoes
	public static void main(String args[]) throws IOException {
		ConversaoParaPDF algoritmo = new ConversaoAPartirDeTextoOffice();
		byte[] arquivo = obterArquivosEmBytes("documento.doc");
		byte[] pdf = algoritmo.converterDocumento(arquivo);
		escreverArquivosEmBytes(pdf, "documento1.pdf");
		
	}
	
}
