package br.fucapi.ads.modelo.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

public class Watermark {

	public static File inserirTarja(byte[] bytes, String tipoCopia,
			String pathMarcaDagua) {
		PdfStamper stamp = null;
		String pathFile = null;
		try {
			PdfReader watermark = new PdfReader(bytes);

//			watermark = inserirFooter(watermark);

			int number_of_pages = watermark.getNumberOfPages();
			int i = 0;
			Image image;

			if ("copiacontrolado".equals(tipoCopia)) {
				pathFile = pathMarcaDagua + "copiacontrolada.pdf";
				stamp = new PdfStamper(watermark,
						new FileOutputStream(pathFile));
				image = Image.getInstance(pathMarcaDagua
						+ "copiacontrolada.png");

			} else if ("copianaocontrolado".equals(tipoCopia)) {
				pathFile = pathMarcaDagua + "copianaocontrolada.pdf";
				stamp = new PdfStamper(watermark,
						new FileOutputStream(pathFile));
				image = Image.getInstance(pathMarcaDagua
						+ "copianaocontrolada.png");

			} else if ("copia-arquivo-obsoleto".equals(tipoCopia)) {
				pathFile = pathMarcaDagua + "capiaobsoleta.pdf";
				stamp = new PdfStamper(watermark,
						new FileOutputStream(pathFile));
				image = Image.getInstance(pathMarcaDagua + "capiaobsoleta.png");
			} else {
				pathFile = pathMarcaDagua + "copiacancelada.pdf";
				stamp = new PdfStamper(watermark,
						new FileOutputStream(pathFile));
				image = Image
						.getInstance(pathMarcaDagua + "copiacancelada.png");
			}

			image.setAbsolutePosition(10, 200);
			PdfContentByte add_watermark;
			while (i < number_of_pages) {
				i++;
				add_watermark = stamp.getUnderContent(i);
				// stamp.
				add_watermark.addImage(image);
			}
			// add_watermark.
			stamp.close();

		} catch (Exception i1) {
			i1.printStackTrace();
		}

		return new File(pathFile);
	}

	public static PdfReader inserirFooter(PdfReader reader) throws IOException {
		PdfWriter writer;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		;
		try {

			// n recebe o numero total de páginas
			int n = reader.getNumberOfPages();
			// tamanho da primeira página
			Rectangle psize = reader.getPageSize(1);
			float width = psize.getHeight();
			float height = psize.getWidth();

			System.out.println(width);
			System.out.println(height);

			Document document = new Document(new Rectangle(width, height));
			writer = PdfWriter.getInstance(document, baos);

			document.open();

			PdfContentByte contentByte = writer.getDirectContent();

			// adiciona conteúdo ao segundo pdf
			PdfContentByte cb = writer.getDirectContent();
			int i = 0;
			int p = 0;
			while (i < n) {
				document.newPage();
				p++;
				i++;
				PdfImportedPage page1 = writer.getImportedPage(reader, i);
				cb.addTemplate(page1, .5f, 0, 0, .5f, 60, 120);
				// cb.addTemplate(page1,0,50);
				// cb.addTemplate(page1, -0.5f, 0f, 0f, -0.5f,
				// PageSize.A4.getWidth() / 2, PageSize.A4.getHeight());

				if (i < n) {
					i++;
					PdfImportedPage page2 = writer.getImportedPage(reader, i);
					cb.addTemplate(page2, .5f, 0, 0, .5f, width / 2 + 60, 120);
				}
				BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA,
						BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
				cb.beginText();
				cb.setFontAndSize(bf, 14);
				cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "page " + p
						+ " of " + ((n / 2) + (n % 2 > 0 ? 1 : 0)), width / 2,
						40, 0);
				cb.endText();
			}
			document.close();
		} catch (Exception de) {
			de.printStackTrace();
		}

		return new PdfReader(baos.toByteArray());
	}
}