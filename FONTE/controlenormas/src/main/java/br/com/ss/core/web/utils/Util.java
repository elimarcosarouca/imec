package br.com.ss.core.web.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

// FIXME #Peninha: alterar o nome da classe para um nome mais intuitivo
public class Util {

	public static int definirTamanhoColuna(int tamanho) {

		if (tamanho < 5) {
			return tamanho;
		} else {
			double col = Math.sqrt(tamanho);
			int colunas = (int) col;
			if (colunas < col)
				colunas++;
			return colunas;
		}

	}
	
	public static byte[] getBytes(InputStream is) throws IOException {
		int len;
		int size = 1024;
		byte[] buf;

		if (is instanceof ByteArrayInputStream) {
			size = is.available();
			buf = new byte[size];
			len = is.read(buf, 0, size);
		} else {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			buf = new byte[size];
			while ((len = is.read(buf, 0, size)) != -1)
				bos.write(buf, 0, len);
			buf = bos.toByteArray();
		}
		return buf;
	}

	public static StreamedContent converterBytesToStreamedContent(byte[] bytes) {
		if (bytes != null) {
			InputStream is = new ByteArrayInputStream(bytes);
			System.out.println("size file : " + bytes.length);
			StreamedContent image = new DefaultStreamedContent(is);
			System.out.println("dans le convertisseur : "
					+ image.getContentType());
			return image;
		} else
			return null;
	}
	
	public static List<Integer> pegarAnos() {
		List<Integer> anos = new ArrayList<Integer>();
		Calendar cal = Calendar.getInstance();
		anos.add(cal.get(Calendar.YEAR) + 1);
		anos.add(cal.get(Calendar.YEAR));
		return anos;
	}
	
	public static List<Double> pegarNotas() {
		List<Double> notas = new ArrayList<Double>();
		Double valor = 10.0;
		for (int i = 20; i > -1; i--) {
			System.out.println(valor);
			notas.add(valor);
			valor = valor - 0.5;
		}
		
		return notas;
	}
	
	public static void main(String[] args) {
		pegarNotas();
	}
	
}
