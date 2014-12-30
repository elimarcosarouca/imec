package br.fucapi.bpms.web.controle;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.UnexpectedRollbackException;

import br.fucapi.bpms.web.dominio.FamiliaProduto;
import br.fucapi.bpms.web.exception.ValidationException;
import br.fucapi.bpms.web.repositorio.FamiliaProdutoRepositorio;

@ManagedBean(name = "familiaProdutoControle")
@SessionScoped
public class FamiliaProdutoControle implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory
			.getLogger(FamiliaProdutoControle.class);

	protected FamiliaProduto entity;
	protected FamiliaProduto search;

	private FamiliaProduto familiaProduto = new FamiliaProduto();
	private List<FamiliaProduto> resultList;

	@ManagedProperty(value = "#{familiaProdutoRepositorioImpl}")
	private FamiliaProdutoRepositorio familiaProdutoRepositorio;

	public FamiliaProdutoRepositorio getFamiliaProdutoRepositorio() {
		return familiaProdutoRepositorio;
	}

	public void setFamiliaProdutoRepositorio(
			FamiliaProdutoRepositorio familiaProdutoRepositorio) {
		this.familiaProdutoRepositorio = familiaProdutoRepositorio;
	}

	public String getMessage() {
		logger.debug("Returning message from familiaProduto home bean");
		return "Hello from Spring";
	}

	private void invalidate() {
		resultList = null;
	}

	public List<FamiliaProduto> getResultList() {
		return resultList;
	}

	public void setResultList(List<FamiliaProduto> resultList) {
		this.resultList = resultList;
	}

	public FamiliaProduto getFamiliaProduto() {
		return familiaProduto;
	}

	public void setFamiliaProduto(FamiliaProduto familiaProduto) {
		this.familiaProduto = familiaProduto;
	}

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

	@PreDestroy
	public void cleanUp() {
		this.resultList = null;
	}

	protected void initEntity() throws InstantiationException,
			IllegalAccessException {
		this.entity = new FamiliaProduto();
		this.search = new FamiliaProduto();

	}

	public void search() {
		this.resultList = familiaProdutoRepositorio.search(this.search);
	}

	public String save() throws SQLException {
		try {
			familiaProdutoRepositorio.saveOrUpdate(entity);
			search();
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
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e
							.getMessage()));

		}

		return null;
	}

	public void remove(FamiliaProduto itemRemove) {
		familiaProdutoRepositorio.remove(itemRemove);
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
	public String editar(FamiliaProduto entity) {
		this.entity = entity;
		return resolveNavigation(true);
	}

	/**
	 * Metodo utilizado para editar uma entidade.
	 * 
	 * @return string.
	 */
	public String cancel() {
		init();
		return resolveNavigation(false);
	}

	/* ---------- Others ------------- */
	protected String resolveNavigation(boolean crud) {
		String url = "/pages/"
				+ entity.getClass().getSimpleName().toLowerCase() + "/";
		url += crud ? "create.jsf" : "search.jsf";
		return url;
	}

	public FamiliaProduto getEntity() {
		return entity;
	}

	public void setEntity(FamiliaProduto entity) {
		this.entity = entity;
	}

	public FamiliaProduto getSearch() {
		return search;
	}

	public void setSearch(FamiliaProduto search) {
		this.search = search;
	}

}
