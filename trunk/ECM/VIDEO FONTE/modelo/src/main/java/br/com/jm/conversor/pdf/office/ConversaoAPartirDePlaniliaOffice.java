package br.com.jm.conversor.pdf.office;

import com.artofsolving.jodconverter.DocumentFamily;
import com.artofsolving.jodconverter.DocumentFormat;

public class ConversaoAPartirDePlaniliaOffice extends ConversaoAPartirDoOffice {

    protected DocumentFormat getTipoDeDocumentoParaConverter() {
        DocumentFormat doc = new DocumentFormat("Microsoft Excel", DocumentFamily.SPREADSHEET, "application/msexcel", "xls");
        doc.setExportFilter(DocumentFamily.SPREADSHEET, "MS Excel 97");
        return doc;
    }

}
