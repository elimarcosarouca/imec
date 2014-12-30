package br.fucapi.ads.assinaturadigital.applet;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import br.fucapi.ads.assinaturadigital.applet.util.Constants;

/**
 * 
 * Classe abstrata que contem metodos comuns aos applets usados no sistema
 * 
 * @author Natanael Fonseca
 */
public abstract class AbstractComponentSigner extends JApplet {

	private static final long serialVersionUID = 8931600942288423187L;

	// Referencias aos parametros
	private HashMap<Constants, String> params;

	private URL documentURL;

	public AbstractComponentSigner() {

		// Look and Feel
		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);

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

	/**
	 * Metodo utilizado para receber a classe concreta que representa o applet
	 * para so entao, criar uma referencia ao log correspondente
	 * 
	 * @return
	 */
	public abstract Class<VistoriaFisicaSigner> getLogInstance();

	/* Metodos sets and gets */

	public URL getDocumentURL() {
		return documentURL;
	}

	public void setDocumentURL(URL documentURL) {
		this.documentURL = documentURL;
	}

	public HashMap<Constants, String> getParams() {
		return params;
	}

	public void setParams(HashMap<Constants, String> params) {
		this.params = params;
	}

	public void cleanUp() throws IOException {
		
//			HTTPConnectionHandler.removeDocVirtual(
//					this.getCodeBase(), 
//					this.getParams().get(Constants.PARAM_JSESSION_ID));
			
	}
}