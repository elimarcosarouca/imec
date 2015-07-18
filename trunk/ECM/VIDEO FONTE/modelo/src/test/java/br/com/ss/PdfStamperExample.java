package br.com.ss;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class PdfStamperExample {

	public static void main(String[] args) {
		try {
			PdfReader pdfReader = new PdfReader("C:\\Users\\claudemir\\Documents\\out.pdf");

			PdfStamper pdfStamper = new PdfStamper(pdfReader,
					new FileOutputStream("C:\\Users\\claudemir\\Documents\\HelloWorld-Stamped.pdf"));

			Image image = Image.getInstance("C:\\Users\\claudemir\\Documents\\logo_7bit.png");

			for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {

				PdfContentByte content = pdfStamper.getUnderContent(i);

				image.setAbsolutePosition(100f, 700f);

				content.addImage(image);
			}

			pdfStamper.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
