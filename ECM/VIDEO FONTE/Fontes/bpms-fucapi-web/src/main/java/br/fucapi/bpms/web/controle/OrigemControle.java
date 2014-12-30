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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.UnexpectedRollbackException;

import br.fucapi.bpms.web.dominio.Origem;
import br.fucapi.bpms.web.exception.ValidationException;
import br.fucapi.bpms.web.repositorio.OrigemRepositorio;

@ManagedBean(name = "origemControle")
@SessionScoped
public class OrigemControle implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory
			.getLogger(OrigemControle.class);

	protected Origem entity;
	protected Origem search;

	private Origem origem = new Origem();
	private List<Origem> resultList;

	@ManagedProperty(value = "#{origemRepositorioImpl}")
	private OrigemRepositorio origemRepositorio;

	public OrigemRepositorio getOrigemRepositorio() {
		return origemRepositorio;
	}

	public void setOrigemRepositorio(OrigemRepositorio origemRepositorio) {
		this.origemRepositorio = origemRepositorio;
	}

	public String getMessage() {
		logger.debug("Returning message from origem home bean");
		return "Hello from Spring";
	}

	private void invalidate() {
		resultList = null;
	}

	public List<Origem> getResultList() {
		return resultList;
	}

	public void setResultList(List<Origem> resultList) {
		this.resultList = resultList;
	}

	public Origem getOrigem() {
		return origem;
	}

	public void setOrigem(Origem origem) {
		this.origem = origem;
	}

	public String init() {
		try {
			initEntity();
			this.search();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
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
		this.entity = new Origem();
		this.search = new Origem();

	}

	public void search() {
		this.resultList = origemRepositorio.search(this.search);;
	}

	public String save() throws SQLException {
		try {
			origemRepositorio.saveOrUpdate(entity);
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

	public void remove(Origem itemRemove) {
		origemRepositorio.remove(itemRemove);
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
	public String editar(Origem entity) {
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

	public Origem getEntity() {
		return entity;
	}

	public void setEntity(Origem entity) {
		this.entity = entity;
	}

	public Origem getSearch() {
		return search;
	}

	public void setSearch(Origem search) {
		this.search = search;
	}

}
