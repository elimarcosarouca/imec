package br.fucapi.ads.assinaturadigital.applet.listeners;

import java.applet.AppletContext;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

public class FinalizarComponente implements ActionListener{
	
	private AppletContext appletContext;

	public FinalizarComponente(AppletContext appletContext) {
		this.appletContext = appletContext;
	}

	public void actionPerformed(ActionEvent e) {

	    try {
	        appletContext.showDocument(new URL("javascript:closePopUp()"));
	    } catch (MalformedURLException me) {}

	}

}	