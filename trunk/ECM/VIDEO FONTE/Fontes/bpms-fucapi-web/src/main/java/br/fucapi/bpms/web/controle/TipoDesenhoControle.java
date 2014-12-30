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

import br.fucapi.bpms.web.dominio.TipoDesenho;
import br.fucapi.bpms.web.exception.ValidationException;
import br.fucapi.bpms.web.repositorio.TipoDesenhoRepositorio;

@ManagedBean
@SessionScoped
public class TipoDesenhoControle implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory
			.getLogger(TipoDesenhoControle.class);

	protected TipoDesenho entity;
	protected TipoDesenho search;

	private TipoDesenho tipoDesenho = new TipoDesenho();
	private List<TipoDesenho> resultList;

	@ManagedProperty(value = "#{tipoDesenhoRepositorioImpl}")
	private TipoDesenhoRepositorio tipoDesenhoRepositorio;

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

	public List<TipoDesenho> getResultList() {
		return this.resultList;
	}

	public void setResultList(List<TipoDesenho> resultList) {
		this.resultList = resultList;
	}

	public TipoDesenho getTipoDesenho() {
		return tipoDesenho;
	}

	public void setTipoDesenho(TipoDesenho tipoDesenho) {
		this.tipoDesenho = tipoDesenho;
	}

	@PreDestroy
	public void cleanUp() {
		this.resultList = null;
	}

	protected void initEntity() throws InstantiationException,
			IllegalAccessException {
		this.entity = new TipoDesenho();
		this.search = new TipoDesenho();

	}

	public void search() {
		this.resultList = tipoDesenhoRepositorio.search(this.search);
	}

	public String save() throws SQLException {
		try {
			tipoDesenhoRepositorio.save(entity);
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

	public void remove(TipoDesenho itemRemove) {
		tipoDesenhoRepositorio.remove(itemRemove);
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
	public String editar(TipoDesenho entity) {
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

	public TipoDesenho getEntity() {
		return entity;
	}

	public void setEntity(TipoDesenho entity) {
		this.entity = entity;
	}

	public TipoDesenho getSearch() {
		return search;
	}

	public void setSearch(TipoDesenho search) {
		this.search = search;
	}

	public TipoDesenhoRepositorio getTipoDesenhoRepositorio() {
		return tipoDesenhoRepositorio;
	}

	public void setTipoDesenhoRepositorio(
			TipoDesenhoRepositorio tipoDesenhoRepositorio) {
		this.tipoDesenhoRepositorio = tipoDesenhoRepositorio;
	}

}