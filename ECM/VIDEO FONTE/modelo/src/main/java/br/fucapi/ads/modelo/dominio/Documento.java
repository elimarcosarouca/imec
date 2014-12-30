package br.fucapi.ads.modelo.dominio;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.UploadedFile;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class Documento {

	private String uuid;
	private UploadedFile file;
	private String processoId;
	private String nomeArquivo;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
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

	public static List<Documento> fromJsonArrayToDocumento(String json) {
		return new JSONDeserializer<List<Documento>>().use(null,
				ArrayList.class).deserialize(json);
	}

	public String objectToJson() {
		return new JSONSerializer().serialize(this);
	}

	public static String listToJson(List<Documento> documentos) {
		return new JSONSerializer().exclude("class").exclude("file")
				.exclude("processoId").serialize(documentos);
	}
}
