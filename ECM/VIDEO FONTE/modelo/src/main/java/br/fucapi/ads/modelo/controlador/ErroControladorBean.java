package br.fucapi.ads.modelo.controlador;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

import br.fucapi.ads.mail.MailService;

@ManagedBean
@ViewScoped
public class ErroControladorBean {

	private String pilhaExcecao;
	private Date data;
	private String usuario;
	private String email;
	private Long idException;
	private String comentario;
	private String ClassName;
	private final String msgErro = "Ocorreu um erro durante a operação no qual o sistema não pôde recuperar-se";
	private Properties properties;

	@ManagedProperty(value = "#{mailService}")
	private MailService mailService;

	@ManagedProperty(value = "#{paginaCentralControladorBean}")
	private PaginaCentralControladorBean paginaCentralControladorBean;

	@PostConstruct
	public void init() {
		properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream(
					"ads.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void enviarRelatorioErro() {
		String data = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
				.format(this.data);

		String servidor = ((HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest())
				.getServerName()
				+ " "
				+ ((HttpServletRequest) FacesContext.getCurrentInstance()
						.getExternalContext().getRequest()).getRemoteAddr();

		String texto = "Usuário: " + usuario + "\n\n" + "[" + data
				+ "] .......................................\n\n"
				+ "\t- Comentario: " + comentario + "\n"
				+ "\t- Exception ID: #" + idException + "\n"
				+ "\t- Class Name: " + ClassName + "\n" + "\t- Server: "
				+ servidor + "\n" + "\t- Message: " + msgErro + "\n"
				+ "\t- Exception Date Time: " + data + "\n"
				+ "\t- StackTrace: " + pilhaExcecao;

		/*mailService.send(properties.getProperty("mail.receiver"),
				properties.getProperty("mail.subject"), texto);*/

		paginaCentralControladorBean.setPaginaCentral("paginas/teste.xhtml");

		comentario = "";

		FacesMessage msg = new FacesMessage("Email enviado com sucesso!");
		FacesContext.getCurrentInstance().addMessage(null, msg);

		RequestContext.getCurrentInstance().update("paginaCentral");

	}

	public String getDataFormatada() {
		return new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy hh:mm:ss")
				.format(data);
	}

	public String getPilhaExcecao() {
		return pilhaExcecao;
	}

	public void setPilhaExcecao(String pilhaExcecao) {
		this.pilhaExcecao = pilhaExcecao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public PaginaCentralControladorBean getPaginaCentralControladorBean() {
		return paginaCentralControladorBean;
	}

	public void setPaginaCentralControladorBean(
			PaginaCentralControladorBean paginaCentralControladorBean) {
		this.paginaCentralControladorBean = paginaCentralControladorBean;
	}

	public Long getIdException() {
		return idException;
	}

	public void setIdException(Long idException) {
		this.idException = idException;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getClassName() {
		return ClassName;
	}

	public void setClassName(String className) {
		ClassName = className;
	}

	public String getMsgErro() {
		return msgErro;
	}
}
