package br.fucapi.bpms.web.dominio.dto;

import java.util.Date;
import java.util.List;

public class Entry {

	private String id;
	private String title;
	private Date updated;
	private Object object;

	// private List<Propriedades> object;

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	// public List<Propriedades> getObject() {
	// return object;
	// }
	// public void setObject(List<Propriedades> object) {
	// this.object = object;
	// }
}
