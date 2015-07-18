package br.com.ss;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.IOException;

import org.apache.log4j.Logger;
 
/**
 * Adds page number to al the pages except the first.
 */
public class PageStamper extends PdfPageEventHelper {
    private static final Logger logger = Logger.getLogger(PageStamper.class);
 
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        final int currentPageNumber = writer.getCurrentPageNumber();
 
        /*if (currentPageNumber == 1) {
            return;
        }*/
 
        /*try {
            final Rectangle pageSize = document.getPageSize();
            final PdfContentByte directContent = writer.getDirectContent();
 
            directContent.setColorFill(BaseColor.GRAY);
            directContent.setFontAndSize(BaseFont.createFont(), 10);
 
            directContent.setTextMatrix(pageSize.getRight(40), pageSize.getBottom(30));
            directContent.showText(String.valueOf(currentPageNumber));
 
        } catch (DocumentException | IOException e) {
            logger.error("PDF generation error", e);
        }*/
    }
}