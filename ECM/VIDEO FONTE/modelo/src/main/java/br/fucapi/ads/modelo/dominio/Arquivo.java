package br.fucapi.ads.modelo.dominio;

import java.io.File;
import java.io.Serializable;

public class Arquivo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String uuid;
	private File file;
	private String processoId;
	private String nomeArquivo;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getProcessoId() {
		return processoId;
	}

	public void setProcessoId(String processoId) {
		this.processoId = processoId;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
}