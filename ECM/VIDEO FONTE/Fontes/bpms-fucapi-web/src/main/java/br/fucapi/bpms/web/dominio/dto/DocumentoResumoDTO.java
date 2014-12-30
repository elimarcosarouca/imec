package br.fucapi.bpms.web.dominio.dto;

import java.util.Date;

public class DocumentoResumoDTO {

	private String nomeDocumento;
	private String uuidDocumento ;
	private Date lastModification;
	private String lastVersion;
	
	public String getNomeDocumento() {
		return nomeDocumento;
	}
	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}
	public String getUuidDocumento() {
		return uuidDocumento;
	}
	public void setUuidDocumento(String uuidDocumento) {
		this.uuidDocumento = uuidDocumento;
	}
	public Date getLastModification() {
		return lastModification;
	}
	public void setLastModification(Date lastModification) {
		this.lastModification = lastModification;
	}
	public String getLastVersion() {
		return lastVersion;
	}
	public void setLastVersion(String lastVersion) {
		this.lastVersion = lastVersion;
	}
}
