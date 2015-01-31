package br.com.jm.conversor.pdf.txt;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import br.com.jm.conversor.pdf.ConversaoParaPDF;
import br.com.jm.conversor.pdf.ImpossivelConverterDocumentoException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class ConversaoAPartirDeTexto implements ConversaoParaPDF {
	
	public byte[] converterDocumento(byte[] documento) throws ImpossivelConverterDocumentoException {
		try {
			return this.converterInternamente(documento);
		} catch (DocumentException e) {
			throw new ImpossivelConverterDocumentoException(e);
		} catch (IOException e) {
			throw new ImpossivelConverterDocumentoException(e);
		}
	}
	
	private byte[] converterInternamente(byte[] documento) throws DocumentException, IOException {
		Document document = new Document();
    	
    	ByteArrayOutputStream bytesDoPdfResultado = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, bytesDoPdfResultado);
        
        document.open();
        
        BufferedReader reader = new BufferedReader( new InputStreamReader( new ByteArrayInputStream(documento) ) );
        
        String line = "";
        while ((line = reader.readLine()) != null) {
        	if ("".equals(line.trim())) {
        		line = "\n"; //linha em branco
        	}
        	Font fonteDefault = new Font(Font.COURIER, 10);
			Paragraph paragraph = new Paragraph(line, fonteDefault);
			document.add(paragraph);
        }
        
        reader.close();
        
        document.close();
        
        return bytesDoPdfResultado.toByteArray();
	}

}
