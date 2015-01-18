package br.fucapi.ads.modelo.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

//import org.apache.poi.hwpf.extractor.WordExtractor;
//
//import com.itextpdf.text.Font;
//import com.itextpdf.text.Font.FontFamily;
//import com.itextpdf.text.Phrase;
////import com.lowagie.text.Document;
////import com.lowagie.text.DocumentException;
////import com.lowagie.text.Paragraph;
////import com.lowagie.text.Phrase;
////import com.lowagie.text.pdf.PdfWriter;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.PdfWriter;
//import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;

public class GeneratePDF {

//	private static String PDF_PATH = "C:/";
//
//	/** Path to the resulting PDF file. */
//	public static final String RESULT = "E:/hello.pdf";
//
//	protected Phrase watermark = new Phrase("WATERMARK", new Font(
//			FontFamily.HELVETICA, 60, Font.NORMAL, BaseColor.LIGHT_GRAY));
//
//	public static void main(String[] args) throws DocumentException,
//			IOException {
//
//		GeneratePDF gPDF = new GeneratePDF();
//
//		String texto = gPDF.extractor(new BufferedInputStream(
//				new FileInputStream(PDF_PATH + "PROJETOS.doc")));
//
//		gPDF.createPdf(RESULT, texto);
//	}
//
//	public String extractor(InputStream is) throws DocumentException {
//		try {
//			WordExtractor wd = new WordExtractor(is);
//
//			return wd.getText();
//
//		} catch (FileNotFoundException e1) {
//			System.out.println("File does not exist.");
//		} catch (IOException ioe) {
//			System.out.println("IO Exception");
//		}
//
//		return null;
//	}
//
//	/**
//	 * Creates a PDF document.
//	 * 
//	 * @param filename
//	 *            the path to the new PDF document
//	 * @throws DocumentException
//	 * @throws IOException
//	 */
//	public void createPdf(String filename, String texto)
//			throws DocumentException, IOException {
//		// step 1
//		Document document = new Document();
//		// step 2
//		PdfWriter writer = PdfWriter.getInstance(document,
//				new FileOutputStream(filename));
//
//		// step 2.1
//		writer.setPageEvent(new Watermark());
//		// step 3
//		document.open();
//		// step 4
//		document.add(new Paragraph(texto));
//		// step 5
//		document.close();
//	}
}
