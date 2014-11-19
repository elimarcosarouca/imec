package br.fucapi.ads.assinaturadigital.applet;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;

import javax.swing.UnsupportedLookAndFeelException;

import br.fucapi.ads.assinaturadigital.applet.util.Constants;

import com.itextpdf.text.DocumentException;


/**
 * 
 * Applet que tem como objetivo assinatura digital
 * 
 * @author natanaelfonseca
 *
 */
public class VistoriaFisicaSigner extends AbstractComponentSigner {	

	private static final long serialVersionUID = 8120519578800566324L;

	public void init() {
		
		try {
			
			javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
				
				public void run() {
					
					try {
						
						loadParams();						
						createGUI();
						
					} catch (UnrecoverableKeyException e) {
						e.printStackTrace();
					} catch (KeyStoreException e) {
						e.printStackTrace();
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					} catch (CertificateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (DocumentException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					}					
				}
			});			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start() {
		super.start();
	}
	
	public void loadParams(){

		HashMap<Constants, String> params = new HashMap<Constants, String>();
		
		params.put( Constants.PARAM_ID_MANIF_SUF,	this.getParameter( Constants.PARAM_ID_MANIF_SUF.toString() ) );
		
		params.put( Constants.PARAM_COOKIES,	this.getParameter( Constants.PARAM_COOKIES.toString() ) );
		
		this.setParams( params );
	}
	
	@Override
	public void stop() {
		System.exit( 0 );
	}
	
	@Override
	public void destroy() {
		System.exit( 0 );
	}
	
	
	private void createGUI() throws KeyStoreException,
									NoSuchAlgorithmException,
									CertificateException,
									IOException,
									UnrecoverableKeyException,
									DocumentException,
									ClassNotFoundException,
									InstantiationException,
									IllegalAccessException,
									UnsupportedLookAndFeelException {
		
		SwingSigner signer = new SwingSigner( this, false );
		this.setContentPane( signer.getContentPane() );
	}	

	public Class<VistoriaFisicaSigner> getLogInstance() {
		return VistoriaFisicaSigner.class;
	}
	
	public static void main(String[] args) {
		VistoriaFisicaSigner signer = new VistoriaFisicaSigner();
		signer.init();
	}
    
}