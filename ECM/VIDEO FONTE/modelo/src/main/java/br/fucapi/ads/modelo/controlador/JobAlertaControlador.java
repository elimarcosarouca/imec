package br.fucapi.ads.modelo.controlador;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import br.fucapi.ads.modelo.dominio.Alerta;
import br.fucapi.ads.modelo.servico.AlertaServico;
import br.fucapi.bpms.activiti.dominio.TarefaInstancia;
import br.fucapi.bpms.activiti.servico.ActivitiServico;
import br.fucapi.bpms.alfresco.servico.AlfrescoServico;

public class JobAlertaControlador {

	private List<TarefaInstancia> listaTarefas;

	private List<Alerta> listaAlerta;

	@Autowired
	private AlertaServico alertaServico;

	@Autowired
	private ActivitiServico activitiServico;

	@Autowired
	private Properties adsProperties;

	@Autowired
	private AlfrescoServico alfrescoServico;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private VelocityEngine velocityEngine;

	public void notificarTarefasPendentes() throws Exception {

		System.out.println("Executando Quartz em: "
				+ new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(
						(System.currentTimeMillis()))));

		Alerta alerta = new Alerta();
//		alerta.setChecked(false);
		alerta.setDataAlerta(new Date());

		System.out
				.println("alertaServico ============== " + alertaServico);
		this.listaAlerta = alertaServico.pesquisar(alerta);

		for (Alerta alertaVencimento : listaAlerta) {
			enviarEmailAlerta(alertaVencimento);
		}
	}

	/**
	 * Metodo utilizado para enviar email no caso de alteracao de usuario
	 * executor de tarefas
	 * 
	 * @param tarefa
	 * @throws Exception
	 */
	public void enviarEmailAlerta(final Alerta alerta)
			throws Exception {

		final String emailDestino = "elimarcos.arouca@gmail.com";

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(emailDestino);
				message.setFrom(adsProperties.getProperty("mail.sender"));

				Map<String, String> model = new HashMap<String, String>();
				model.put(
						"numeroProtocolo",alerta.getProtocolo());

				message.setSubject(adsProperties
						.getProperty("send.subject.alertapendente"));

				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, "mail/alerta_pendente.vm", model);
				message.setText(text, true);

			}
		};

		this.mailSender.send(preparator);
	}

	public void sendmail() throws Exception {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo("claudemirramosferreira@gmail.com");
				message.setFrom(adsProperties.getProperty("mail.sender"));
				Map<String, String> model = new HashMap<String, String>();
				message.setSubject(adsProperties.getProperty("send.subject"));
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, "tarefa_pendente.vm", model);
				message.setText(text, true);
			}

		};
		this.mailSender.send(preparator);
	}
}