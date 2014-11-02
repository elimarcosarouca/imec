package br.com.ss.core.web.controlador;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.ss.academico.dominio.Empresa;
import br.com.ss.academico.enumerated.EstadoCivil;
import br.com.ss.core.seguranca.dominio.AbstractEntity;
import br.com.ss.core.seguranca.dominio.Usuario;
import br.com.ss.core.seguranca.servico.IService;
import br.com.ss.core.web.enumerated.Constants;
import br.com.ss.core.web.enumerated.NaoSim;
import br.com.ss.core.web.enumerated.Sexo;
import br.com.ss.core.web.ireport.RelatorioUtil;
import br.com.ss.core.web.utils.DateUtil;
import br.com.ss.core.web.utils.FacesUtils;
import br.com.ss.core.web.utils.ReflectionsUtil;

import com.lowagie.text.DocumentException;

@Named
public abstract class ControladorGenerico<T extends AbstractEntity> implements
		Serializable {

	private static final long serialVersionUID = -1229239475130268144L;

	/* ---------- Atributos ----------------------- */

	/** Entity usado no cadastro. */
	protected T entidade;

	/** Entity usado na pesquisa. */
	protected T pesquisa;

	/** Entity selecionada para exclusão. */
	protected T itemToRemove;

	/** Lista com o resultado da pesquisa. */
	protected List<T> listaPesquisa;

	@ManagedProperty(value = "#{relatorioUtil}")
	protected RelatorioUtil relatorioUtil;

	// FIXME deve ficar no contexto de app - criar classe
	protected List<SelectItem> sexoList;
	
	// FIXME deve ficar no contexto de app - criar classe
	protected List<SelectItem> estadoCivilList;

	// FIXME deve ficar no contexto de app - criar classe
	protected List<SelectItem> alergiaList;

	/**
	 * Alias para redirecionar para a tela de cadastro.
	 */
	public static final String CADASTRO = "cadastro";

	/**
	 * Alias para redirecionar para a tela de pesquisa.
	 */
	public static final String PESQUISA = "pesquisa";

	/**
	 * Alias para redirecionar para a tela de relatorio.
	 */
	public static final String RELATORIO = "relatorio";

	/**
	 * armazena os bytes do relatorio
	 */
	private StreamedContent inputStream;

	protected static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

	/** Resource path dos relatorio: /resources/jasper/ */
	protected static final String PATH_REPORT = "resources" + File.separator
			+ "jasper" + File.separator;

	/* ------ Parametros para o Relatório ----------- */
	/** Parametro para o relatorio. */
	protected static final String REPORT_TITLE = "report_title";
	protected static final String USUARIO = "usuario";
	protected static final String EMPRESA = "empresa";

	/* ---------- Metodos ----------------------- */

	@PostConstruct
	protected void setup() {
		initEntity();
		init();
		initCommons();
		pesquisar();
	}

	protected void initCommons() {

		sexoList = new ArrayList<SelectItem>();
		for (Sexo c : Sexo.values()) {
			sexoList.add(new SelectItem(c, c.getDescricao()));
		}

		alergiaList = new ArrayList<SelectItem>();
		for (NaoSim c : NaoSim.values()) {
			alergiaList.add(new SelectItem(c, c.getDescricao()));
		}
		
		estadoCivilList = new ArrayList<SelectItem>();
		for (EstadoCivil c : EstadoCivil.values()) {
			estadoCivilList.add(new SelectItem(c, c.getDescricao()));
		}

	}

	protected void init() {
		// Sobrescrever caso necessario
	}

	/**
	 * Força o recarregamento da pagina de pesquisa.
	 */
	public void reload() {
		init();
	}

	@SuppressWarnings("unchecked")
	protected void initEntity() {
		try {
			Class<T> clazz = resolverClass();
			pesquisa = (T) ReflectionsUtil.callConstructor(clazz);
			entidade = (T) ReflectionsUtil.callConstructor(clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private Class<T> resolverClass() {
		return (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/** Nome do relatorio utilizado na impressão. */
	protected abstract String getNomeRelatorioJasper();

	/** Título do relatório utilizado na impressão. */
	protected abstract String getTituloRelatorio();

	/** Retornar o serviço da entidade. */
	protected abstract IService<T, Long> getService();

	public void pesquisar() {
		this.listaPesquisa = getService().pesquisar(pesquisa);
	}

	/**
	 * Faz a persistencia da entidade gerenciada.
	 * 
	 * @param url
	 *            URL de retorno.
	 * @return String URL
	 */
	public String salvar(String url) {
		try {
			getService().salvar(entidade);
			setup();
			showMessage(Constants.MSG_SUCESSO, FacesMessage.SEVERITY_INFO);

			if (url != null) {
				return url;
			}

			return redirect(PESQUISA);

		} catch (Exception e) {
			e.printStackTrace();
			showMessage(Constants.MSG_ERRO, FacesMessage.SEVERITY_ERROR);
			return null;
		}
	}

	/**
	 * Faz a persistencia da entidade gerenciada.
	 */
	public String salvar() {
		return salvar(null);
	}

	public void excluir(T itemRemove) {
		itemToRemove = itemRemove;
		excluir();
	}

	public void excluir() {
		try {

			executarExcluir(itemToRemove);
			pesquisar();
			setItemToRemove(null);
			showMessage(Constants.MSG_SUCESSO, FacesMessage.SEVERITY_INFO);

		} catch (Exception e) {
			String msg = Constants.MSG_ERRO;
			Throwable throwableCause = e.getCause();
			if (throwableCause instanceof PersistenceException) {
				msg = Constants.MSG_ERRO_FK_CONSTRAINT;
			}
			e.printStackTrace();
			showMessage(msg, FacesMessage.SEVERITY_ERROR);
		}
	}

	public void executarExcluir(T itemRemove) {
		T removeEntity = getService().findByPrimaryKey(itemRemove.getId());
		getService().remover(removeEntity);
	}

	/**
	 * Metodo utilizado para ir para a tela de cadastra da entidade.
	 * 
	 * @return string.
	 */
	public String novo() {
		this.initEntity();
		return redirect(CADASTRO);
	}

	private String redirect(String page) {
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

	public String detalhe() {
		return detalhe(entidade);
	}

	/**
	 * Metodo utilizado para editar uma entidade. Sobrescrever este metodo caso
	 * necessário realizar outras operaçoes.
	 * 
	 * @return string.
	 */
	public String detalhe(T entidade) {
		this.entidade = entidade;
		return redirect(CADASTRO);
	}

	/**
	 * Metodo utilizado para cancelar uma edicao e retornar para a pg de
	 * inicial.
	 * 
	 * @return string.
	 */
	public String cancelar() {
		init();
		return redirect(PESQUISA);
	}

	/**
	 * Impressao de um item selecionado no grid de pesquisa.
	 * 
	 * @param entity
	 * @throws JRException
	 */
	public void imprimir(T entity, String nomeRelatorio) throws JRException {
		Map<String, Object> param = new HashMap<String, Object>();
		Empresa empresa = (Empresa) FacesUtils.getApplicationParam("empresa");

		// parametros usados no relatorio
		param.put(REPORT_TITLE, getTituloRelatorio());
		param.put(EMPRESA, empresa);
		param.put(USUARIO, getUsuarioLogado());

		List<T> listaPesquisa = new ArrayList<T>();

		listaPesquisa.add(entity);

		gerarRelatorioWeb(listaPesquisa, param, nomeRelatorio);
	}

	/**
	 * Usado para imprimir o grid da tela de pesqusia.
	 * 
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 * @throws IOException
	 * @throws JRException
	 */

	public void imprimir() throws FileNotFoundException, IOException,
			DocumentException, JRException {
		Map<String, Object> param = new HashMap<String, Object>();
		Empresa empresa = (Empresa) FacesUtils.getApplicationParam("empresa");

		// parametros usados no relatorio
		param.put(REPORT_TITLE, getTituloRelatorio());
		param.put(EMPRESA, empresa);
		param.put(USUARIO, getUsuarioLogado());
		
		gerarRelatorioWeb(this.listaPesquisa, param, getNomeRelatorioJasper());

	}

	public void gerarRelatorioWeb(List<T> lista,
			Map<String, Object> parametros, String nomeRelatorio)
			throws JRException {

		JRDataSource jrRS = new JRBeanCollectionDataSource(lista);

		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context
				.getExternalContext().getResponse();

		BufferedOutputStream output = null;
		BufferedInputStream input = null;

		String webPath = context.getExternalContext().getRealPath("/");
		String reportPath = webPath + PATH_REPORT + nomeRelatorio;

		try {

			input = new BufferedInputStream(new FileInputStream(reportPath),
					DEFAULT_BUFFER_SIZE);

			File fileReport = new File(reportPath);

			response.reset();
			response.setHeader("Content-Type", "application/pdf");
			response.setHeader("Content-Length",
					String.valueOf(fileReport.length()));
			response.setHeader("Content-Disposition", "inline; filename=\""
					+ fileReport.getName() + "\"");

			JasperRunManager.runReportToPdfStream(new FileInputStream(
					fileReport), response.getOutputStream(), parametros, jrRS);

			output = new BufferedOutputStream(response.getOutputStream(),
					DEFAULT_BUFFER_SIZE);

			// Write file contents to response.
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}

			// Finalize task.
			output.flush();
		} catch (FileNotFoundException e) {
			System.out.println("Erro : Relatorio não foi encontrado: " + reportPath);
			showMessage(Constants.MSG_ERRO, FacesMessage.SEVERITY_ERROR);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Gently close streams.
			close(output);
			close(input);
		}

		context.renderResponse();
		context.responseComplete();
	}

	public void imprimir(List<?> lista, Map<String, Object> params,
			String nomeRelatorio) {

		try {
			byte[] dadosPdf = relatorioUtil.gerarRelatorioWebBytes(lista,
					params, nomeRelatorio);
			InputStream is = new ByteArrayInputStream(dadosPdf);

			inputStream = new DefaultStreamedContent(is, "application/pdf");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/* -------- Metodos utilitarios -------------- */

	protected void showMessage(String msg, Severity severityInfo) {
		showMessage(msg, null, severityInfo);
	}

	protected void showMessage(String msg, String detail, Severity severityInfo) {
		FacesUtils.addMessage(msg, detail, severityInfo);
	}

	/**
	 * Retorna o usuário logado.
	 * 
	 * @return Usuario
	 */
	public Usuario getUsuarioLogado() {
		return ((Usuario) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal());
	}

	/**
	 * Retorna a instancia de HttpServletRequest.
	 */
	protected HttpServletRequest getRequest() {
		return FacesUtils.getRequest();
	}

	protected boolean isDataFuturo(Date date) {
		return DateUtil.isDataFuturo(date);
	}

	/* ---------- Others ------------- */

	/* ---------- Gets/Sets ------------- */

	public T getItemToRemove() {
		return itemToRemove;
	}

	public void setItemToRemove(T itemToRemove) {
		this.itemToRemove = itemToRemove;
	}

	public List<T> getResultList() {
		return listaPesquisa;
	}

	public T getEntidade() {
		return entidade;
	}

	public T getPesquisa() {
		return pesquisa;
	}

	public List<T> getListaPesquisa() {
		return listaPesquisa;
	}

	public List<SelectItem> getSexoList() {
		return sexoList;
	}

	public List<SelectItem> getAlergiaList() {
		return alergiaList;
	}

	public List<SelectItem> getEstadoCivilList() {
		return estadoCivilList;
	}

	public RelatorioUtil getRelatorioUtil() {
		return relatorioUtil;
	}

	public void setRelatorioUtil(RelatorioUtil relatorioUtil) {
		this.relatorioUtil = relatorioUtil;
	}

	public void setEntidade(T entidade) {
		this.entidade = entidade;
	}

	public StreamedContent getInputStream() {
		return inputStream;
	}

	public void setInputStream(StreamedContent inputStream) {
		this.inputStream = inputStream;
	}

	protected static void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}