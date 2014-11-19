package br.fucapi.ads.modelo.email;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EmailUtils {

	private static final String HOSTNAME = "mail.fucapi.br";
	private static final String USERNAME = "suporteads";
	private static final String PASSWORD = "fucapi2013";
	private static final String EMAILORIGEM = "suporte.ads@fucapi.br";

	public static Email conectaEmail() throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName(HOSTNAME);
		//email.setSmtpPort(465);
		email.setSmtpPort(25);
		email.setAuthenticator(new DefaultAuthenticator(USERNAME, PASSWORD));
		email.setTLS(true);
		email.setSSL(true);
		email.setFrom(EMAILORIGEM);
		return email;
	}

	public static void enviaEmail(Mensagem mensagem) throws EmailException {
		Email email = new SimpleEmail();
		email = conectaEmail();
		email.setSubject(mensagem.getTitulo());

		System.out.println(mensagem.getTitulo());
		
		email.setMsg(mensagem.getMensagem());
		email.addTo(mensagem.getDestino());

		String resposta = email.send();
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"E-mail enviado com sucesso para: "
								+ mensagem.getDestino(), "Informação"));
	}

}
