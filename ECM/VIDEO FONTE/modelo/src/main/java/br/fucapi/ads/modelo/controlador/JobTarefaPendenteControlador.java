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

import br.fucapi.ads.modelo.dominio.VariavelPublicarDocumento;
import br.fucapi.bpms.activiti.dominio.TarefaInstancia;
import br.fucapi.bpms.activiti.servico.ActivitiServico;
import br.fucapi.bpms.alfresco.dominio.Usuario;
import br.fucapi.bpms.alfresco.servico.AlfrescoServico;

public class JobTarefaPendenteControlador {

	private List<TarefaInstancia> listaTarefas;

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

		Map<String, Object> filtro = new HashMap<String, Object>();

		filtro.put("tipoSolicitacao", "PUBLICAR_DOCUMENTO");

		System.out
				.println("####################################################################33");
		System.out.println("Executando Quartz em: "
				+ new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(
						(System.currentTimeMillis()))));

		System.out.println(activitiServico);

		listaTarefas = activitiServico.getHistoricoTarefasPorVariaveis(filtro,
				null, "PUBLICAR_DOCUMENTO", true, null);

		for (TarefaInstancia tarefaInstancia : listaTarefas) {
			System.out.println(tarefaInstancia.getAssignee());
			VariavelPublicarDocumento variavelPublicarDocumento = new VariavelPublicarDocumento();
			variavelPublicarDocumento.converterListaVariaveis(tarefaInstancia
					.getVariables());
			tarefaInstancia.setVariaveis(variavelPublicarDocumento);
			enviarEmailNovoExecutor(tarefaInstancia);
		}
	}

	/**
	 * Metodo utilizado para enviar email no caso de alteracao de usuario
	 * executor de tarefas
	 * 
	 * @param tarefa
	 * @throws Exception
	 */
	public void enviarEmailNovoExecutor(final TarefaInstancia tarefa)
			throws Exception {

		Usuario usuario = this.alfrescoServico.listarUsuarioNome(tarefa
				.getAssignee());

		final String emailDestino = usuario.getEmail();

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(emailDestino);
				message.setFrom(adsProperties.getProperty("mail.sender"));

				Map<String, String> model = new HashMap<String, String>();
				model.put("nomeAtividade", tarefa.getName());
				model.put(
						"numeroProtocolo",
						((VariavelPublicarDocumento) tarefa.getVariaveis())
								.getAno()
								+ ""
								+ ((VariavelPublicarDocumento) tarefa
										.getVariaveis()).getSequencial());
				model.put("tipoSolicitacao",
						((VariavelPublicarDocumento) tarefa.getVariaveis())
								.getTipoSolicitacao());

				message.setSubject(adsProperties
						.getProperty("send.subject.tarefapendente"));

				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, "mail/tarefa_pendente.vm", model);
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