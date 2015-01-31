package br.com.jm.conversor.pdf.office;

import com.artofsolving.jodconverter.DocumentFamily;
import com.artofsolving.jodconverter.DocumentFormat;

public class ConversaoAPartirDeTextoOffice extends ConversaoAPartirDoOffice {

    protected DocumentFormat getTipoDeDocumentoParaConverter() {
        DocumentFormat doc = new DocumentFormat("Microsoft Word", DocumentFamily.TEXT, "application/msword", "doc");
        doc.setExportFilter(DocumentFamily.TEXT, "MS Word 97");
        return doc;
    }

}
