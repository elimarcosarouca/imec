package br.fucapi.ads.modelo.controlador;

import org.springframework.stereotype.Component;

@Component
public class LoginStatus {

	private boolean showMsgErro;
	
	public boolean isShowMsgErro() {
		return showMsgErro;
	}

	public void setShowMsgErro(boolean showMsgErro) {
		this.showMsgErro = showMsgErro;
	}
}
