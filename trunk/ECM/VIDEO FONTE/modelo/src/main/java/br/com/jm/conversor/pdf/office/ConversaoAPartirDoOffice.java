package br.com.jm.conversor.pdf.office;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.ConnectException;

import br.com.jm.conversor.pdf.ConversaoParaPDF;
import br.com.jm.conversor.pdf.ImpossivelConverterDocumentoException;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFamily;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/*
 * Para deixar o openoffice aceitando tcp/ip:
 * soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;" -nofirststartwizard
 */
public abstract class ConversaoAPartirDoOffice implements ConversaoParaPDF {
	
    /* O host e a porta do open office estao fixos no codigo por 
       simplicidade, mas essa configuracao pode ser externalizada */
    private String host = "localhost";
    private int porta = 8100;
    
	public byte[] converterDocumento(byte[] documento) {
		try {
			return this.converterInternamente(documento);
		} catch (ConnectException e) {
			throw new ImpossivelConverterDocumentoException(
				"Servico do open office fora do ar! Conversoes para PDF a partir de documentos nao irao funcionar!");
		}
	}
	
	public byte[] converterInternamente(byte[] documento) throws ConnectException {
	    OpenOfficeConnection connection = new SocketOpenOfficeConnection(this.host, this.porta);
	    connection.connect();

	    try {
	        DocumentFormat doc = this.getTipoDeDocumentoParaConverter();
		
    		DocumentFormat pdf = new DocumentFormat("Portable Document Format", "application/pdf", "pdf");
            pdf.setExportFilter(DocumentFamily.TEXT, "writer_pdf_Export");
            pdf.setExportFilter(DocumentFamily.SPREADSHEET, "calc_pdf_Export");
    		
    		ByteArrayInputStream input = new ByteArrayInputStream(documento);
    		ByteArrayOutputStream output = new ByteArrayOutputStream();
		
		    DocumentConverter documentConverter = new OpenOfficeDocumentConverter(connection);
		    documentConverter.convert(input, doc, output, pdf);
		    
		    return output.toByteArray();
		} finally {
		    connection.disconnect();
		}
	}

	//para estender nas subclasses - define qual tipo de documento do office ira converter
    protected abstract DocumentFormat getTipoDeDocumentoParaConverter();

}
