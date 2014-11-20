package br.fucapi.ads.assinaturadigital.applet.x509;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.cert.Certificate;

import br.fucapi.ads.assinaturadigital.applet.x509.exception.X509PDFSignerException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;

/**
 * 
 * Classe responsavel por abstrair as operacoes de assinatura digitial em pdf utilizando IText
 * 
 * @author Natanael Fonseca
 *
 */
public class PDFSignerX509 {
	
	private PrivateKey privateKey;
	private Certificate[] chain;
	
	public PDFSignerX509(PrivateKey privateKey, Certificate[] chain) {
		this.privateKey = privateKey;
		this.chain = chain;
	}
	
	public byte[] assinarPDF(byte[] pdfAssinado) throws X509PDFSignerException{
		
		String errorMsg = "Nao foi possivel assinar o documento PDF!";
		
		try{
			
			ByteArrayOutputStream bout = new ByteArrayOutputStream( );
			
			PdfReader reader = new PdfReader( pdfAssinado );
			
			PdfStamper stp = PdfStamper.createSignature(reader, bout, '\0', null, true);
			
			/*Detalhes da assinatura digital*/
			PdfSignatureAppearance sap = stp.getSignatureAppearance();

	        sap.setCrypto(privateKey, chain, null, PdfSignatureAppearance.WINCER_SIGNED);


			/*Seta como vazio a razao e a localizacao*/
			sap.setReason(" ");			
			sap.setLocation(" ");			
			stp.close();
			
			return bout.toByteArray();
		
		} catch (FileNotFoundException e) {			
    		throw new X509PDFSignerException( errorMsg, e );
		} catch (DocumentException e) {			
			throw new X509PDFSignerException( errorMsg, e );			
		} catch (IOException e) { 
			throw new X509PDFSignerException( errorMsg, e );
		}
		
	}
	
}
