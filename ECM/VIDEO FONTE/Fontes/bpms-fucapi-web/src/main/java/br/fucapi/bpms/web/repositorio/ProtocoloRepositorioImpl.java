package br.fucapi.bpms.web.repositorio;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.fucapi.bpms.web.dominio.Protocolo;

@Repository
@Transactional
public class ProtocoloRepositorioImpl  implements Serializable, ProtocoloRepositorio{

	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;

	
	public void save(Protocolo protocolo) {
		entityManager.persist(protocolo);
	}

	@SuppressWarnings("unchecked")
	public List<Protocolo> list() {
		return entityManager.createQuery("select t from Protocolo t")
				.getResultList();
	}

	// @Override
	public Protocolo getByPrimaryKey(Protocolo entity) {
		return entityManager.find(Protocolo.class, entity.getId());
	}

	// @Override
	public Protocolo getByPrimaryKey(Long id) {
		return entityManager.find(Protocolo.class, id);
	}

	// @Override
	public Protocolo merge(Protocolo entity) {
		return merge(entity, true);
	}

	// @Override
	public Protocolo merge(Protocolo entity, boolean flush) {
		Protocolo t = entityManager.merge(entity);
		flush(flush);
		return t;
	}

	// @Override
	public void remove(Protocolo entity) {
		entityManager.remove(entity);
		flush();
	}

	// @Override
	public void remove(Long id) {
		entityManager.remove(entityManager.find(Protocolo.class, id));
		flush();
	}

	// @Override
	public void persist(Protocolo entity) {
		persist(entity, true);
	}

	// @Override
	public void persist(Protocolo entity, boolean flush) {
		entityManager.persist(entity);
		flush(flush);
	}

	// @Override
	public void saveOrUpdate(Protocolo entity) {
		saveOrUpdate(entity, true);
	}

	// @Override
	public void saveOrUpdate(Protocolo entity, boolean flush) {
		if (!entity.getId().equals(0)) {
			persist(entity);
		} else {
			merge(entity);
		}
		flush(flush);
	}

	// @Override
	public void flush() {
		entityManager.flush();
	}

	public void flush(boolean flush) {
		if (flush) {
			flush();
		}
	}

	// @Override
	public List<Protocolo> listAll() {
		CriteriaQuery<Protocolo> criteria = entityManager.getCriteriaBuilder()
				.createQuery(Protocolo.class);
		return (List<Protocolo>) this.entityManager.createQuery(
				criteria.select(criteria.from(Protocolo.class)))
				.getResultList();
	}



	public boolean notEmpty(Object obj) {
		return obj != null;
	}

	public boolean notEmpty(String str) {
		return str != null && str.length() > 0;
	}

	@Transactional
	public Protocolo gerarProtocolo() {
		Calendar cal = Calendar.getInstance();
		int ano = cal.get(Calendar.YEAR);
		Protocolo protocolo = null;

		Query q = entityManager.createNativeQuery(
				"select * from siem_protocolo prot where prot.prot_ano = "
						+ ano, Protocolo.class);

		try {

			protocolo = (Protocolo) q.getSingleResult();

			if (protocolo == null) {
				protocolo = new Protocolo(1l, 1, 2013);
			} else {
				protocolo.setSequencial(protocolo.getSequencial() + 1);
			}

			System.out.println("protocolo = " + protocolo.getSequencial()
					+ " / " + protocolo.getAno());

			return merge(protocolo, true);

		} catch (EmptyResultDataAccessException eresult) {
			eresult.printStackTrace();
			System.out.println("protocolo não encontrado");

		} catch (NoResultException nore) {
			System.out.println("==========================");
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Warnning",
							"Erro, nenhum registro encontrado"));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Ocorreu um erro no sistema"));
		}

		return null;

	}

}