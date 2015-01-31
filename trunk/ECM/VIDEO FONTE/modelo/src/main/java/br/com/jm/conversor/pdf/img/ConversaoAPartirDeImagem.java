package br.com.jm.conversor.pdf.img;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import br.com.jm.conversor.pdf.ConversaoParaPDF;
import br.com.jm.conversor.pdf.ImpossivelConverterDocumentoException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;

public class ConversaoAPartirDeImagem implements ConversaoParaPDF {

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
        
        Image imagemDoDocumento = Image.getInstance(documento);
		
		float widthImagem = imagemDoDocumento.getWidth();
		float heightImagem = imagemDoDocumento.getHeight();
		
        float widthPagina = document.getPageSize().getWidth();
		float heightPagina = document.getPageSize().getHeight();
		
		float width = widthImagem > widthPagina ? widthPagina : widthImagem;
		float height = heightImagem > heightPagina ? heightPagina : heightImagem;
		
		imagemDoDocumento.setAbsolutePosition(0, heightPagina - height);
		imagemDoDocumento.scaleAbsolute(width, height);
        document.add(imagemDoDocumento);
        
        document.close();
        
        return bytesDoPdfResultado.toByteArray();
	}

}
