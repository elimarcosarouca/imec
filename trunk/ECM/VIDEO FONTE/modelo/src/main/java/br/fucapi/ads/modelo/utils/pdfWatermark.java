package br.fucapi.ads.modelo.utils;

import java.io.FileOutputStream;

import com.lowagie.text.Image;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

class pdfWatermark {
	public static void main(String args[]) {
		try {
			PdfReader reader = new PdfReader("C:\\Users\\claudemirferreira\\Documents\\exemplo\\vai.pdf");
			int n = reader.getNumberOfPages();

			// Create a stamper that will copy the document to a new file
			PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(
					"C:\\Users\\claudemirferreira\\Documents\\exemplo\\text1.pdf"));
			int i = 1;
			PdfContentByte under;
			PdfContentByte over;

			Image img = Image.getInstance("C:\\Users\\claudemirferreira\\Documents\\exemplo\\kermit.jpg");
			BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA,
					BaseFont.WINANSI, BaseFont.EMBEDDED);

			img.setAbsolutePosition(400, 600);

			while (i < n) {
				// Watermark under the existing page
				under = stamp.getUnderContent(i);
				under.addImage(img);

				// Text over the existing page
				over = stamp.getOverContent(i);
				over.beginText();
				over.setFontAndSize(bf, 18);
				over.showText("page " + i);
				over.endText();

				i++;
			}

			stamp.close();

		} catch (Exception de) {
		}
	}
}