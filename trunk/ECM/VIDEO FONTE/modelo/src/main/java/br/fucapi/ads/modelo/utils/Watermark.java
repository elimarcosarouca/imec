package br.fucapi.ads.modelo.utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class Watermark extends PdfPageEventHelper {

	 protected Phrase watermark = new Phrase("CÓPIA NÃO CONTROLADA", new Font(FontFamily.HELVETICA, 60, Font.NORMAL, BaseColor.LIGHT_GRAY));
	 
     @Override
     public void onEndPage(PdfWriter writer, Document document) {
         PdfContentByte canvas = writer.getDirectContentUnder();
         ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, watermark, 298, 421, 45);
     }
}
