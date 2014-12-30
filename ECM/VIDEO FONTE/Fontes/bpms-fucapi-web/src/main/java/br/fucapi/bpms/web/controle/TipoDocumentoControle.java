package br.fucapi.bpms.web.controle;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.UnexpectedRollbackException;

import br.fucapi.bpms.web.dominio.TipoDocumento;
import br.fucapi.bpms.web.exception.ValidationException;
import br.fucapi.bpms.web.repositorio.TipoDocumentoRepositorio;

@ManagedBean(name = "tipoDocumentoControle")
@SessionScoped
public class TipoDocumentoControle implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory
			.getLogger(TipoDocumentoControle.class);

	protected TipoDocumento entity;
	protected TipoDocumento search;

	private TipoDocumento tipoDocumento = new TipoDocumento();
	private List<TipoDocumento> resultList;

	@ManagedProperty(value = "#{tipoDocumentoRepositorioImpl}")
	private TipoDocumentoRepositorio tipoDocumentoRepositorio;

	public String init() {
		try {
			initEntity();
			this.search();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return resolveNavigation(false);
	}

	public String getMessage() {
		logger.debug("Returning message from tipoDocumento home bean");
		return "Hello from Spring";
	}

	private void invalidate() {
		resultList = null;
	}

	public List<TipoDocumento> getResultList() {
		return this.resultList;
	}

	public void setResultList(List<TipoDocumento> resultList) {
		this.resultList = resultList;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	@PreDestroy
	public void cleanUp() {
		this.resultList = null;
	}

	protected void initEntity() throws InstantiationException,
			IllegalAccessException {
		this.entity = new TipoDocumento();
		this.search = new TipoDocumento();

	}

	public void search() {
		this.resultList = tipoDocumentoRepositorio.search(this.search);
	}

	public String save() throws SQLException {
		try {
			tipoDocumentoRepositorio.save(entity);
			this.search();
			return resolveNavigation(false);
		} catch (UnexpectedRollbackException ure) {
			System.out.println(ure.getMessage());
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"JA EXISTE UM REGISTRO CADASTRADO COM O NOME "));
			ure.printStackTrace();

		} catch (ValidationException e) {
			System.out.println(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Warnning", e
							.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e
							.getMessage()));

		}

		return null;
	}

	public void remove() {
		// remove(itemToRemove);
		search();
		// setItemToRemove(null);
	}

	public void remove(TipoDocumento itemRemove) {
		tipoDocumentoRepositorio.remove(itemRemove);
	}

	/**
	 * Metodo utilizado para ir para a tela de cadastra da entidade.
	 * 
	 * @return string.
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public String cadastrar() throws InstantiationException,
			IllegalAccessException {
		this.initEntity();
		return resolveNavigation(true);
	}

	/**
	 * Metodo utilizado para editar uma entidade. Sobrescrever este metodo caso
	 * necessÃ¡rio realizar outras operaÃ§oes.
	 * 
	 * @return string.
	 */
	public String editar(TipoDocumento entity) {
		System.out.println("entrou no editar");
		this.entity = entity;
		return resolveNavigation(true);
	}

	/**
	 * Metodo utilizado para editar uma entidade.
	 * 
	 * @return string.
	 */
	public String cancel() {
		this.search();
		return resolveNavigation(false);
	}

	/* ---------- Others ------------- */
	protected String resolveNavigation(boolean crud) {
		String url = "/pages/"
				+ entity.getClass().getSimpleName().toLowerCase() + "/";
		url += crud ? "create.jsf" : "search.jsf";
		return url;
	}

	public TipoDocumento getEntity() {
		return entity;
	}

	public void setEntity(TipoDocumento entity) {
		this.entity = entity;
	}

	public TipoDocumento getSearch() {
		return search;
	}

	public void setSearch(TipoDocumento search) {
		this.search = search;
	}

	public TipoDocumentoRepositorio getTipoDocumentoRepositorio() {
		return tipoDocumentoRepositorio;
	}

	public void setTipoDocumentoRepositorio(
			TipoDocumentoRepositorio tipoDocumentoRepositorio) {
		this.tipoDocumentoRepositorio = tipoDocumentoRepositorio;
	}

}