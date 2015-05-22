package br.fucapi.ads.modelo.controlador;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.alfresco.repo.webservice.administration.AdministrationFault;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;

import br.fucapi.ads.modelo.dominio.UsuarioGrupoLog;
import br.fucapi.ads.modelo.dominio.UsuarioLog;
import br.fucapi.ads.modelo.enumerated.Constants;
import br.fucapi.ads.modelo.servico.UsuarioGrupoLogServico;
import br.fucapi.ads.modelo.servico.UsuarioLogServico;
import br.fucapi.ads.modelo.utils.FacesUtils;
import br.fucapi.ads.modelo.utils.ItemMenu;
import br.fucapi.ads.modelo.utils.Menu;
import br.fucapi.bpms.alfresco.dominio.Capabilities;
import br.fucapi.bpms.alfresco.dominio.GrupoAlfresco;
import br.fucapi.bpms.alfresco.dominio.Usuario;
import br.fucapi.bpms.alfresco.servico.AlfrescoServico;

@ManagedBean
@SessionScoped
public class UsuarioControladorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{alfrescoServicoImpl}")
	private AlfrescoServico alfrescoServico;

	private Boolean isAdmin = false;
	private Boolean isBloqueado = false;
	private Boolean isEditarUsuario = false;

	@ManagedProperty(value = "#{emailControlador}")
	private EmailControlador emailControlador;

	@Value("${alfresco.grupo.adm}")
	private String alfrescoGrupoAdmin;

	@ManagedProperty(value = "#{adsProperties}")
	private Properties adsProperties;

	private Usuario usuario;

	private GrupoAlfresco grupoAlfresco = new GrupoAlfresco();

	private List<Usuario> usuarios;

	private String token;

	/**
	 * Alias para redirecionar para a tela de cadastro.
	 */
	public static final String CADASTRO = "cadastro";

	/**
	 * Alias para redirecionar para a tela de pesquisa.
	 */
	public static final String PESQUISA = "pesquisa";

	// private String confirmaSenha;

	// @ManagedProperty(value = "#{usuarioTokenServicoImpl}")
	// private UsuarioTokenServico usuarioTokenServico;

	@ManagedProperty(value = "#{usuarioLogServicoImpl}")
	private UsuarioLogServico usuarioLogServico;

	@ManagedProperty(value = "#{usuarioGrupoLogServicoImpl}")
	private UsuarioGrupoLogServico usuarioGrupoLogServico;

	@PostConstruct
	public void init() {
		this.usuario = new Usuario();

		this.isAdmin = false;
		this.isBloqueado = false;

		this.usuario.setSenha("teste");

		/*
		 * HttpServletRequest request =
		 * (HttpServletRequest)FacesContext.getCurrentInstance
		 * ().getExternalContext().getRequest(); HttpServletResponse response =
		 * (
		 * HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext
		 * ().getResponse(); token = request.getParameter("token");
		 * 
		 * if (this.usuarioTokenServico.listarTodos(token).getUtilizado()) {
		 * System.out.println("teste123");
		 * 
		 * try { request.setAttribute("token_utilizado", true);
		 * request.getRequestDispatcher("login.xhtml").forward(request,
		 * response); } catch (ServletException e) { e.printStackTrace(); }
		 * catch (IOException e) { e.printStackTrace(); }
		 * 
		 * }
		 */
	}

	protected String redirect(String page) {
		try {

			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			HttpServletRequest request = (HttpServletRequest) context
					.getRequest();

			String fullUrl = request.getRequestURL().toString();
			String path = fullUrl.substring(0,
					fullUrl.lastIndexOf(Constants.BARRA));
			String url = path + Constants.BARRA + page + Constants.EXTENSION
					+ Constants.REDIRECT;

			context.redirect(url);
			FacesContext.getCurrentInstance().responseComplete();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return page;
	}

	public String incluir() throws RemoteException {

		try {
			if (!isEditarUsuario) {
				this.incluirUsuario();
				this.init();
			} else {
				this.editarUsuario();
			}

			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO,
					"Atenção. Operacacao realizado com sucesso", ""));

		} catch (HttpClientErrorException e) {
			if ("409".equals(e.getStatusCode().toString())) {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"Atenção. Nome de usuário cadastrado anteriormente",
										""));

			} else {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"Atenção. Problema ao realizar a operacao",
										""));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Atenção. Operação realizada com sucesso", ""));
		}
		
		return redirect(PESQUISA);
	}
	
	public void isBloqueado(boolean isNovoUsuario) {
		String operacao;

		if (this.isBloqueado) {
			alfrescoServico.habilitarUsuario(this.usuario.getUserName(), true);
			operacao = "D";
		} else {
			alfrescoServico.habilitarUsuario(this.usuario.getUserName(), false);
			operacao = "B";
		}

		UsuarioLog usuarioLog = new UsuarioLog(this.usuario.getFirstName(),
				this.usuario.getLastName(), this.usuario.getUserName(),
				this.usuario.getEmail());

		usuarioLog.setData(new Date());
		usuarioLog.setOperacao(operacao);
		usuarioLog.setLoginAdm(((Usuario) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal()).getUserName());
	}

	public void isAdministrador(boolean isNovoUsuario) {
		String operacao = "";

		if (this.isAdmin) {
			alfrescoServico.incluirUsuarioGrupo(this.usuario.getUserName(),
					"ALFRESCO_ADMINISTRATORS");
			operacao = "I";
		} else if (!isNovoUsuario) {
			alfrescoServico.excluirUsuarioGrupo(this.usuario.getUserName(),
					"ALFRESCO_ADMINISTRATORS");
			operacao = "E";
		}

		UsuarioGrupoLog usuarioGrupoLog = new UsuarioGrupoLog(
				"ALFRESCO_ADMINISTRATORS", this.usuario.getUserName(),
				((Usuario) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal()).getUserName(),
				new Date(), operacao);

		usuarioGrupoLogServico.save(usuarioGrupoLog);
	}

	public void incluirUsuarioGrupo() {
		alfrescoServico.incluirUsuarioGrupo(this.usuario.getUserName(),
				this.grupoAlfresco.getDisplayName());

		UsuarioGrupoLog usuarioGrupoLog = new UsuarioGrupoLog(
				this.grupoAlfresco.getDisplayName(),
				this.usuario.getUserName(),
				((Usuario) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal()).getUserName(),
				new Date(), "I");

		usuarioGrupoLogServico.save(usuarioGrupoLog);
	}

	public void excluirUsuarioGrupo() {

		alfrescoServico.excluirUsuarioGrupo(this.usuario.getUserName(),
				this.grupoAlfresco.getDisplayName());

		UsuarioGrupoLog usuarioGrupoLog = new UsuarioGrupoLog(
				this.grupoAlfresco.getDisplayName(),
				this.usuario.getUserName(),
				((Usuario) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal()).getUserName(),
				new Date(), "D");

		usuarioGrupoLogServico.save(usuarioGrupoLog);
	}

	public void incluirUsuario() throws RemoteException {

		alfrescoServico.incluirUsuario(this.usuario.getFirstName(),
				this.usuario.getLastName(), this.usuario.getEmail(),
				this.usuario.getUserName());

		this.isAdministrador(true);
		this.isBloqueado(false);

		try {
			emailControlador.enviarEmailNovoUsuario(this.usuario);
		} catch (Exception e) {
			e.printStackTrace();
		}

		UsuarioLog usuarioLog = new UsuarioLog(this.usuario.getFirstName(),
				this.usuario.getLastName(), this.usuario.getUserName(),
				this.usuario.getEmail());

		usuarioLog.setData(new Date());
		usuarioLog.setOperacao("I");
		usuarioLog.setLoginAdm(((Usuario) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal()).getUserName());

		// INSERI O LOG DO REGISTRO
		usuarioLogServico.save(usuarioLog);

		this.usuario = new Usuario();
	}

	public void editarUsuario() {
		alfrescoServico.editarUsuario(this.usuario.getUserName(),
				this.usuario.getFirstName(), this.usuario.getLastName(),
				this.usuario.getEmail(), this.usuario.isEnabled());

		if (!this.usuario.getCapabilities().isAdmin())
			this.isAdministrador(false);

		this.isBloqueado(false);

		UsuarioLog usuarioLog = new UsuarioLog(this.usuario.getFirstName(),
				this.usuario.getLastName(), this.usuario.getUserName(),
				this.usuario.getEmail());

		usuarioLog.setData(new Date());
		usuarioLog.setOperacao("U");
		usuarioLog.setLoginAdm(((Usuario) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal()).getUserName());

		// INSERI O LOG DO REGISTRO
		usuarioLogServico.save(usuarioLog);
	}

	public String listarUsuarioNome() throws RemoteException {

		if (this.usuario.getUserName() == null) {
			this.usuario.setUserName("");
		}

		this.usuarios = this.alfrescoServico.listarUsuarioNomeLike(this.usuario
				.getUserName());

		return redirect(PESQUISA);
	}

	public String editar(Usuario usuario) {

		List<GrupoAlfresco> grupos = null;

		try {
			grupos = alfrescoServico.listarGruposPorUsuario(usuario
					.getUserName());
		} catch (AdministrationFault e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Problema ao realizar a operacao", ""));
			return null;
		} catch (RemoteException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Problema ao realizar a operacao", ""));
			return null;
		} catch (HttpClientErrorException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Problema ao realizar a operacao", ""));
			return null;
		}

		// verificar se o usuario é adminstrador
		// Solucao temporaria para resolver problema de json que define se o
		// usuario e administrador
		for (GrupoAlfresco grupoAlfresco : grupos) {
			if (grupoAlfresco.getDisplayName()
					.equals("ALFRESCO_ADMINISTRATORS")) {
				usuario.setCapabilities(new Capabilities());
				usuario.getCapabilities().setAdmin(true);
				this.isAdmin = usuario.getCapabilities().isAdmin();
				break;
			}
		}

		this.isEditarUsuario = true;
		this.usuario = usuario;

		this.isBloqueado = !usuario.isEnabled();

		return redirect(CADASTRO);

	}

	public void atualizarUsuarioBloqueado() {
		this.isBloqueado = false;

	}

	public void atualizarUsuarioAdmin() {
		this.isAdmin = false;
	}

	public String paginaIncluir() {

		this.isEditarUsuario = false;
		this.usuario = new Usuario();

		return redirect(CADASTRO);

	}

	public String cancelar() {
		this.usuario = new Usuario();
		return redirect(PESQUISA);

	}

	public void enableChk() {
		if (this.usuario.isEnabled()) {
			this.isBloqueado = true;
		}
	}

	/*
	 * public String modificarSenha() throws RemoteException {
	 * 
	 * UsuarioToken listaToken = usuarioTokenServico.listarTodos(token);
	 * 
	 * 
	 * //if (!listaToken.isEmpty() &&
	 * listaToken.get(0).getUserName().equals(usuario.getUserName()) &&
	 * !listaToken.get(0).getUtilizado()) { if (listaToken != null &&
	 * listaToken.getUserName().equals(usuario.getUserName()) &&
	 * !listaToken.getUtilizado()) {
	 * 
	 * Date dataToken = listaToken.getDataGeracao();
	 * 
	 * Calendar cal = Calendar.getInstance(); cal.setTime(dataToken);
	 * cal.add(Calendar.HOUR, 24); Date futureDate = cal.getTime(); Date
	 * dataAtual = new Date();
	 * 
	 * System.out.println(dataAtual); System.out.println(dataToken);
	 * 
	 * if (dataAtual.after(futureDate)) {
	 * 
	 * FacesMessage message = new FacesMessage( FacesMessage.SEVERITY_ERROR,
	 * "Esse link não é válido!", "");
	 * FacesContext.getCurrentInstance().addMessage("msg", message);
	 * 
	 * } else {
	 * 
	 * alfrescoServico.atualizarSenha(this.usuario.getUserName(),
	 * this.usuario.getSenha()); UsuarioToken usuarioToken = listaToken;
	 * usuarioToken.setUtilizado(true);
	 * usuarioToken.setDataUtilizacao(dataAtual);
	 * usuarioTokenServico.salvar(usuarioToken);
	 * 
	 * }
	 * 
	 * } else {
	 * 
	 * FacesMessage message = new FacesMessage( FacesMessage.SEVERITY_ERROR,
	 * "Esse link não é válido!", "");
	 * FacesContext.getCurrentInstance().addMessage("growl", message);
	 * 
	 * }
	 * 
	 * 
	 * SecurityContextHolder.clearContext(); FacesContext facesContext =
	 * FacesContext.getCurrentInstance();
	 * 
	 * HttpSession session = (HttpSession) facesContext.getExternalContext()
	 * .getSession(false); if (session != null) { session.invalidate(); } return
	 * "login.xhtml?faces-redirect=true";
	 * 
	 * }
	 */

	public Boolean getIsEditarUsuario() {
		return isEditarUsuario;
	}

	public void setIsEditarUsuario(Boolean isEditarUsuario) {
		this.isEditarUsuario = isEditarUsuario;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public AlfrescoServico getAlfrescoServico() {
		return alfrescoServico;
	}

	public void setAlfrescoServico(AlfrescoServico alfrescoServico) {
		this.alfrescoServico = alfrescoServico;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public GrupoAlfresco getGrupoAlfresco() {
		return grupoAlfresco;
	}

	public void setGrupoAlfresco(GrupoAlfresco grupoAlfresco) {
		this.grupoAlfresco = grupoAlfresco;
	}

	public String getAlfrescoGrupoAdmin() {
		return alfrescoGrupoAdmin;
	}

	public void setAlfrescoGrupoAdmin(String alfrescoGrupoAdmin) {
		this.alfrescoGrupoAdmin = alfrescoGrupoAdmin;
	}

	public Boolean getIsBloqueado() {
		return isBloqueado;
	}

	public void setIsBloqueado(Boolean isBloqueado) {
		this.isBloqueado = isBloqueado;
	}

	public EmailControlador getEmailControlador() {
		return emailControlador;
	}

	public void setEmailControlador(EmailControlador emailControlador) {
		this.emailControlador = emailControlador;
	}

	// public UsuarioTokenServico getUsuarioTokenServico() {
	// return usuarioTokenServico;
	// }
	//
	// public void setUsuarioTokenServico(UsuarioTokenServico
	// usuarioTokenServico) {
	// this.usuarioTokenServico = usuarioTokenServico;
	// }

	public Properties getAdsProperties() {
		return adsProperties;
	}

	public void setAdsProperties(Properties adsProperties) {
		this.adsProperties = adsProperties;
	}

	public UsuarioLogServico getUsuarioLogServico() {
		return usuarioLogServico;
	}

	public void setUsuarioLogServico(UsuarioLogServico usuarioLogServico) {
		this.usuarioLogServico = usuarioLogServico;
	}

	public UsuarioGrupoLogServico getUsuarioGrupoLogServico() {
		return usuarioGrupoLogServico;
	}

	public void setUsuarioGrupoLogServico(
			UsuarioGrupoLogServico usuarioGrupoLogServico) {
		this.usuarioGrupoLogServico = usuarioGrupoLogServico;
	}

	// public String getConfirmaSenha() {
	// return confirmaSenha;
	// }
	//
	// public void setConfirmaSenha(String confirmaSenha) {
	// this.confirmaSenha = confirmaSenha;
	// }

	/**
	 * Carrega os perfis do usuario logado.
	 * 
	 * @param usuario
	 */
	private void carregarPerfisUsuario(Usuario usuario) {
		String redirec = "?faces-redirect=true";
		// String idSistema = MessageUtils.getMessageResourceString(
		// MessageUtils.ID_SISTEMA );

		// List<Perfil> perfis = perfilServico.listaPerfilPorSistemaPorUsuario(
		// new Integer(idSistema), usuario.getId() );

		List<Menu> menuList = new ArrayList<Menu>();

		Menu menu = new Menu("Cadastro", null);

		ItemMenu item = new ItemMenu(null, "TIPO DE DOCUMENTO",
				"/paginas/tipodocumento/pesquisa.xhtml" + redirec);
		menu.getItemMenus().add(item);

		menuList.add(menu);

		// armazena menu na sessao do usuario
		FacesUtils.getRequest().getSession().setAttribute("menuList", menuList);
	}

}