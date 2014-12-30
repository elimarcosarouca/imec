package br.fucapi.bpms.web.dominio.dto;

import java.util.List;

public class Properties {

	private List<PropertyId> propertiesId;
	private List<PropertyString> propertiesString;
	private List<PropertyDateTime> propertiesDateTime;
	

	public List<PropertyDateTime> getPropertiesDateTime() {
		return propertiesDateTime;
	}

	public void setPropertiesDateTime(List<PropertyDateTime> propertiesDateTime) {
		this.propertiesDateTime = propertiesDateTime;
	}

	public List<PropertyString> getPropertiesString() {
		return propertiesString;
	}

	public void setPropertiesString(List<PropertyString> propertiesString) {
		this.propertiesString = propertiesString;
	}

	public List<PropertyId> getPropertiesId() {
		return propertiesId;
	}

	public void setPropertiesId(List<PropertyId> propertiesId) {
		this.propertiesId = propertiesId;
	}

}
