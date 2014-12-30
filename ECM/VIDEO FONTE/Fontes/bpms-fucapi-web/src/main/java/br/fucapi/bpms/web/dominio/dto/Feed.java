package br.fucapi.bpms.web.dominio.dto;

import java.util.List;

public class Feed {

	private String id;
	private String title;
	private List<Entry> entrys;
	
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
	public List<Entry> getEntrys() {
		return entrys;
	}
	public void setEntrys(List<Entry> entrys) {
		this.entrys = entrys;
	}
}
