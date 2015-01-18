package br.fucapi.ads.modelo.controlador;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class LoginStatus implements Serializable{

	private static final long serialVersionUID = 2437881738814327976L;
	
	private boolean showMsgErro;
	
	public boolean isShowMsgErro() {
		return showMsgErro;
	}

	public void setShowMsgErro(boolean showMsgErro) {
		this.showMsgErro = showMsgErro;
	}
}
