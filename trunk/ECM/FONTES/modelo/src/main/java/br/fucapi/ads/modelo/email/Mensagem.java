package br.fucapi.ads.modelo.email;

public class Mensagem {

	private String destino;
	private String titulo;
	private String mensagem;

	private String link;

	// Inicializa Velocity
	// VelocityEngine ve = new VelocityEngine();

	// Criando contexto que liga java ao teamplate

	// VelocityContext context = new VelocityContext();

	// Escolhendo o teamplate
	// Template t = ve.getTemplate("redefinicao_de_senha.vm");

	// String text = VelocityEngineUtils.mergeTemplateIntoString(ve,
	// "redefinicao_de_senha.vm", null);

	// private void getTeamPlateEmail() throws Exception {
	//
	// MimeMessageHelper temp;
	//
	// MimeMessagePreparator preparator = new MimeMessagePreparator() {
	//
	// @Override
	// public void prepare(MimeMessage mimeMessage) throws Exception {
	// MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
	//
	// Map<String, String> model = new HashMap<String, String>();
	// model.put(link,
	// "http://170.10.200.224:9090/modelo/lembrarsenha.xhtml");
	//
	// String text = VelocityEngineUtils.mergeTemplateIntoString(ve,
	// "emailBody.vm", model);
	// message.setText(text, true);
	//
	// // temp = message;
	//
	// }
	//
	// };
	//
	// // EmailUtils.enviaEmail(mensagem);
	//
	// }

	public void enviarEmail() {

		// EmailUtils.enviaEmail(mensagem);
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
