package br.fucapi.ads.modelo.utils;

import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class Watermark {

	public static File inserirTarja(byte[] bytes, String tipoCopia,
			String pathMarcaDagua) {
		PdfStamper stamp = null;
		String pathFile = null;
		try {
			PdfReader watermark = new PdfReader(bytes);
			int number_of_pages = watermark.getNumberOfPages();
			int i = 0;
			Image image;

			if ("copiacontrolado".equals(tipoCopia)) {
				pathFile = pathMarcaDagua + "copiacontrolada.pdf";
				stamp = new PdfStamper(watermark,
						new FileOutputStream(pathFile));
				image = Image.getInstance(pathMarcaDagua
						+ "copiacontrolada.png");
			
			} else if ("copianaocontrolado".equals(tipoCopia)){
				pathFile = pathMarcaDagua + "copianaocontrolada.pdf";
				stamp = new PdfStamper(watermark,
						new FileOutputStream(pathFile));
				image = Image.getInstance(pathMarcaDagua
						+ "copianaocontrolada.png");
			
			} else if ("copia-arquivo-obsoleto".equals(tipoCopia)){
				pathFile = pathMarcaDagua + "capiaobsoleta.pdf";
				stamp = new PdfStamper(watermark,
						new FileOutputStream(pathFile));
				image = Image.getInstance(pathMarcaDagua
						+ "capiaobsoleta.png");
			} else {
				pathFile = pathMarcaDagua + "copiacancelada.pdf";
				stamp = new PdfStamper(watermark,
						new FileOutputStream(pathFile));
				image = Image.getInstance(pathMarcaDagua
						+ "copiacancelada.png");
			}
			
			image.setAbsolutePosition(10, 200);
			PdfContentByte add_watermark;
			while (i < number_of_pages) {
				i++;
				add_watermark = stamp.getUnderContent(i);
				add_watermark.addImage(image);
			}
			stamp.close();

		} catch (Exception i1) {
			i1.printStackTrace();
		}

		return new File(pathFile);
	}
}