package br.fucapi.ads.modelo.utils;

import java.io.FileOutputStream;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class Watermark {
	public static void main(String[] args) {
		try {
			PdfReader Read_PDF_To_Watermark = new PdfReader(
					"C:\\Users\\claudemirferreira\\Documents\\exemplo\\vai.pdf");
			int number_of_pages = Read_PDF_To_Watermark.getNumberOfPages();
			PdfStamper stamp = new PdfStamper(
					Read_PDF_To_Watermark,
					new FileOutputStream(
							"C:\\Users\\claudemirferreira\\Documents\\exemplo\\novo.pdf"));
			int i = 0;
			Image watermark_image = Image
					.getInstance("C:\\Users\\claudemirferreira\\Documents\\exemplo\\controlado.jpg");
			watermark_image.setAbsolutePosition(10, 200);
			PdfContentByte add_watermark;
			while (i < number_of_pages) {
				i++;
				add_watermark = stamp.getUnderContent(i);
				add_watermark.addImage(watermark_image);
			}
			stamp.close();
		} catch (Exception i1) {
			i1.printStackTrace();
		}
	}
}