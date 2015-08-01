package br.fucapi.ads.modelo.controlador;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.primefaces.context.RequestContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.client.HttpClientErrorException;

import br.fucapi.ads.modelo.dominio.UsuarioToken;
import br.fucapi.ads.modelo.dominio.VariaveisProcesso;
import br.fucapi.ads.modelo.servico.UsuarioTokenServico;
import br.fucapi.bpms.activiti.dominio.TarefaInstancia;
import br.fucapi.bpms.alfresco.dominio.Usuario;
import br.fucapi.bpms.alfresco.servico.AlfrescoServico;

@ManagedBean
@SessionScoped
public class EmailControlador {

	private Usuario usuario;

	private String destino;

	private String urlSenha;
	
	private String novoUsuario;
	
	@ManagedProperty(value = "#{adsProperties}")
	private Properties adsProperties;

	@ManagedProperty(value = "#{alfrescoServicoImpl}")
	private AlfrescoServico alfrescoServico;

	@ManagedProperty(value = "#{mailSender}")
	private JavaMailSender mailSender;

	@ManagedProperty(value = "#{velocityEngine}")
	private VelocityEngine velocityEngine;
	
	@ManagedProperty (value = "#{usuarioTokenServicoImpl}" )
	private UsuarioTokenServico usuarioTokenServico;

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	
	@PostConstruct
	public void init() {
		this.usuario = new Usuario();		
	}
	
	/**
	 * Metodo utilizado para enviar email caso o usuario realize a solicitacao na tela de login
	 * @throws Exception
	 */
	
	public void enviarEmail() throws Exception {

		try {
		
			this.usuario = this.alfrescoServico.listarUsuarioNome(this.usuario.getUserName());
		
		} catch (HttpClientErrorException e) {
			
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Login de usuário inválido!",
					"");
			FacesContext.getCurrentInstance().addMessage("msg", message);
			
			e.printStackTrace();
			
			return;
		}
		
		destino = this.usuario.getEmail();		
		String token = UUID.randomUUID().toString();
		
		urlSenha = adsProperties.getProperty("send.url") + this.usuario.getUserName()+"&token="+ token;
		
		System.out.println(urlSenha);
		
		UsuarioToken usuarioToken = new UsuarioToken();
		usuarioToken.setToken(token);
		usuarioToken.setDataGeracao(new Date());
		usuarioToken.setUtilizado(false);
		usuarioToken.setUserName(this.usuario.getUserName());
		usuarioTokenServico.save(usuarioToken);
				
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(destino);
				message.setFrom(adsProperties.getProperty("mail.sender"));
				
				
				Map<String, String> model = new HashMap<String, String>();
					
				model.put("urlSenha", urlSenha);
				message.setSubject(adsProperties.getProperty("send.subject.novasenha"));

				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "solicitar_nova_senha.vm", model);
				message.setText(text, true);
			}
		};
		this.mailSender.send(preparator);		
		RequestContext request = RequestContext.getCurrentInstance();
		request.execute("PF('enviado').show()");
	}
	
	/**
	 * Metodo utilizado para enviar email na finalização de criacao de um novo usuario
	 * @param usuario
	 * @throws Exception
	 */
	
	public void enviarEmailNovoUsuario(Usuario usuario) throws Exception {

		this.usuario = this.alfrescoServico.listarUsuarioNome(usuario.getUserName());

		System.out.println(usuario.getUserName());
		System.out.println(usuario.getEmail());

		destino = usuario.getEmail();		
		String token = UUID.randomUUID().toString();
		
		urlSenha = adsProperties.getProperty("send.url") + usuario.getUserName()+"&token="+ token;
		
		System.out.println(urlSenha);
		
		UsuarioToken usuarioToken = new UsuarioToken();
		usuarioToken.setToken(token);
		usuarioToken.setDataGeracao(new Date());
		usuarioToken.setUtilizado(false);
		usuarioToken.setUserName(usuario.getUserName());
		usuarioTokenServico.save(usuarioToken);
				
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(destino);
				message.setFrom(adsProperties.getProperty("mail.sender"));
						
				Map<String, String> model = new HashMap<String, String>();
					
				model.put("urlSenha", urlSenha);
				message.setSubject(adsProperties.getProperty("send.subject"));

				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "senha_novo_user.vm", model);
				message.setText(text, true);
		
			}

		};
		this.mailSender.send(preparator);

	}

	/**
	 * Metodo utilizado para enviar email no caso de alteracao de usuario executor de tarefas
	 * @param tarefa
	 * @throws Exception
	 */
	public void enviarEmailNovoExecutor(final TarefaInstancia tarefa) throws Exception {

		this.usuario = this.alfrescoServico.listarUsuarioNome(tarefa.getAssignee());

		final String emailDestino = usuario.getEmail();		
				
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(emailDestino);
				message.setFrom(adsProperties.getProperty("mail.sender"));
						
				Map<String, String> model = new HashMap<String, String>();
					
				model.put("tipoSolicitacao", tarefa.getProcessDefinitionId());
				model.put("numeroProtocolo", ((VariaveisProcesso)tarefa.getVariaveis()).getProtocolo());
				model.put("nomeAtividade", tarefa.getName());
				
				message.setSubject(adsProperties.getProperty("send.subject.novatarefa"));

				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "email_novo_executor.vm", model);
				message.setText(text, true);
		
			}
		};
		
		this.mailSender.send(preparator);
	}
	
	/**
	 * Metodo utilizado para enviar email na finalização de criacao de um novo usuario
	 * @param usuario
	 * @throws Exception
	 */
	
	public void sendmail() throws Exception {
				
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo("claudemirramosferreira@gmail.com");
				message.setFrom(adsProperties.getProperty("mail.sender"));
						
				Map<String, String> model = new HashMap<String, String>();
					
				model.put("urlSenha", urlSenha);
				message.setSubject(adsProperties.getProperty("send.subject"));

				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "tarefa_pendente.vm", model);
				message.setText(text, true);
		
			}

		};
		this.mailSender.send(preparator);

	}
	
	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Properties getAdsProperties() {
		return adsProperties;
	}

	public void setAdsProperties(Properties adsProperties) {
		this.adsProperties = adsProperties;
	}

	public AlfrescoServico getAlfrescoServico() {
		return alfrescoServico;
	}

	public void setAlfrescoServico(AlfrescoServico alfrescoServico) {
		this.alfrescoServico = alfrescoServico;
	}

	public UsuarioTokenServico getUsuarioTokenServico() {
		return usuarioTokenServico;
	}

	public void setUsuarioTokenServico(UsuarioTokenServico usuarioTokenServico) {
		this.usuarioTokenServico = usuarioTokenServico;
	}

	public String getNovoUsuario() {
		return novoUsuario;
	}

	public void setNovoUsuario(String novoUsuario) {
		this.novoUsuario = novoUsuario;
	}

}
